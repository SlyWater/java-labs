package lab1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ComputeClient {
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : ComputeServer.DEFAULT_PORT;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(host);
            send(socket, serverAddress, port, "REGISTER");
            System.out.println("Compute client registered on " + host + ":" + port);

            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
                handleTask(socket, packet.getAddress(), packet.getPort(), message);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void handleTask(DatagramSocket socket, InetAddress address, int port, String message) throws Exception {
        String[] parts = message.split(";", 5);
        if (parts.length != 5 || !"TASK".equals(parts[0])) {
            return;
        }

        String taskId = parts[1];
        try {
            double lowLimit = Double.parseDouble(parts[2]);
            double highLimit = Double.parseDouble(parts[3]);
            double step = Double.parseDouble(parts[4]);

            RecIntegral recIntegral = RecIntegral.createCalculationPart(lowLimit, highLimit, step);
            recIntegral.calculateMultiThread();
            send(socket, address, port, "RESULT;" + taskId + ";" + recIntegral.getResult());
        } catch (Exception e) {
            send(socket, address, port, "ERROR;" + taskId + ";" + e.getMessage().replace(";", ","));
        }
    }

    private static void send(DatagramSocket socket, InetAddress address, int port, String message) throws Exception {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
        socket.send(packet);
    }
}
