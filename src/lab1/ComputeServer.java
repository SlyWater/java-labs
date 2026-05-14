package lab1;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
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
    private static final int BUFFER_SIZE = 1024;
    private static final int CLIENT_TIMEOUT_SECONDS = 30;

    private final DatagramSocket socket;
    private final CopyOnWriteArrayList<ClientInfo> clients = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String, CompletableFuture<Double>> pendingResults = new ConcurrentHashMap<>();
    private final Thread listenerThread;
    private volatile boolean running = true;

    public ComputeServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
        listenerThread = new Thread(this::listen, "udp-compute-server");
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    public int getPort() {
        return socket.getLocalPort();
    }

    public int getClientCount() {
        return clients.size();
    }

    public double calculate(double lowLimit, double highLimit, double step) throws Exception {
        if (clients.isEmpty()) {
            throw new IllegalStateException("No compute clients registered on UDP port " + getPort());
        }

        String taskGroupId = UUID.randomUUID().toString();
        List<ClientInfo> taskClients = new ArrayList<>(clients);
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
                send(taskClients.get(i), "TASK;" + taskId + ";" + partLow + ";" + partHigh + ";" + step);
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
        }
    }

    private void listen() {
        byte[] buffer = new byte[BUFFER_SIZE];
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                String message = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
                handleMessage(packet.getAddress(), packet.getPort(), message);
            } catch (SocketException e) {
                if (running) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(InetAddress address, int port, String message) {
        String[] parts = message.split(";", 3);
        if ("REGISTER".equals(parts[0])) {
            registerClient(address, port);
            return;
        }

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

    private void registerClient(InetAddress address, int port) {
        ClientInfo client = new ClientInfo(address, port);
        if (!clients.contains(client)) {
            clients.add(client);
        }
    }

    private void send(ClientInfo client, String message) throws IOException {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, client.address, client.port);
        socket.send(packet);
    }

    @Override
    public void close() {
        running = false;
        socket.close();
    }

    private static class ClientInfo {
        private final InetAddress address;
        private final int port;

        private ClientInfo(InetAddress address, int port) {
            this.address = address;
            this.port = port;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ClientInfo)) {
                return false;
            }
            ClientInfo other = (ClientInfo) obj;
            return port == other.port && address.equals(other.address);
        }

        @Override
        public int hashCode() {
            return 31 * address.hashCode() + port;
        }
    }
}
