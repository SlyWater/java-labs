package lab1;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ComputeServer implements Closeable {
    public static final int DEFAULT_PORT = 5000;
    private static final int CLIENT_TIMEOUT_SECONDS = 30;

    private final ServerSocket serverSocket;
    private final CopyOnWriteArrayList<ClientConnection> clients = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String, CompletableFuture<Double>> pendingResults = new ConcurrentHashMap<>();
    private final Thread acceptThread;
    private volatile boolean running = true;

    public ComputeServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        acceptThread = new Thread(this::acceptClients, "tcp-compute-server");
        acceptThread.setDaemon(true);
        acceptThread.start();
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public int getClientCount() {
        removeClosedClients();
        return clients.size();
    }

    public double calculate(double lowLimit, double highLimit, double step) throws Exception {
        removeClosedClients();
        if (clients.isEmpty()) {
            throw new IllegalStateException("No compute clients connected on TCP port " + getPort());
        }

        String taskGroupId = UUID.randomUUID().toString();
        List<ClientConnection> taskClients = new ArrayList<>(clients);
        List<CompletableFuture<Double>> futures = new ArrayList<>();

        double length = (highLimit - lowLimit) / taskClients.size();
        try {
            for (int i = 0; i < taskClients.size(); i++) {
                double partLow = lowLimit + i * length;
                double partHigh = (i == taskClients.size() - 1) ? highLimit : lowLimit + (i + 1) * length;
                String taskId = taskGroupId + "-" + i;
                CompletableFuture<Double> future = new CompletableFuture<>();
                pendingResults.put(taskId, future);
                futures.add(future);
                taskClients.get(i).send("TASK;" + taskId + ";" + partLow + ";" + partHigh + ";" + step);
            }

            double result = 0.0;
            for (CompletableFuture<Double> future : futures) {
                result += future.get(CLIENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            }
            return result;
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw new Exception(cause);
        } catch (TimeoutException e) {
            throw new SocketTimeoutException("Timeout while waiting for compute client result");
        } finally {
            for (String taskId : pendingResults.keySet()) {
                if (taskId.startsWith(taskGroupId + "-")) {
                    pendingResults.remove(taskId);
                }
            }
            removeClosedClients();
        }
    }

    private void acceptClients() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                ClientConnection client = new ClientConnection(socket);
                clients.add(client);
                client.startReader();
            } catch (SocketException e) {
                if (running) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                if (running) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleMessage(String message) {
        String[] parts = message.split(";", 3);
        if ("RESULT".equals(parts[0]) && parts.length == 3) {
            CompletableFuture<Double> future = pendingResults.remove(parts[1]);
            if (future != null) {
                future.complete(Double.parseDouble(parts[2]));
            }
            return;
        }

        if ("ERROR".equals(parts[0]) && parts.length == 3) {
            CompletableFuture<Double> future = pendingResults.remove(parts[1]);
            if (future != null) {
                future.completeExceptionally(new IllegalStateException(parts[2]));
            }
        }
    }

    private void removeClosedClients() {
        clients.removeIf(ClientConnection::isClosed);
    }

    @Override
    public void close() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ClientConnection client : clients) {
            client.close();
        }
    }

    private class ClientConnection implements Closeable {
        private final Socket socket;
        private final BufferedReader in;
        private final PrintWriter out;

        private ClientConnection(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        private void startReader() {
            Thread thread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        handleMessage(message);
                    }
                } catch (IOException e) {
                    if (running && !isClosed()) {
                        e.printStackTrace();
                    }
                } finally {
                    close();
                    clients.remove(this);
                }
            }, "tcp-compute-client-reader");
            thread.setDaemon(true);
            thread.start();
        }

        private synchronized void send(String message) throws IOException {
            out.println(message);
            if (out.checkError()) {
                close();
                throw new IOException("Failed to send task to compute client");
            }
        }

        private boolean isClosed() {
            return socket.isClosed() || !socket.isConnected();
        }

        @Override
        public void close() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
