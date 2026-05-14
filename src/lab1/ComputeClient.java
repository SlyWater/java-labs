package lab1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ComputeClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : ComputeServer.DEFAULT_PORT;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Compute client connected to " + host + ":" + port);
            System.out.println("Local client port: " + socket.getLocalPort());

            String message;
            while ((message = in.readLine()) != null) {
                handleTask(out, message);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void handleTask(PrintWriter out, String message) {
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
            out.println("RESULT;" + taskId + ";" + recIntegral.getResult());
        } catch (Exception e) {
            out.println("ERROR;" + taskId + ";" + e.getMessage().replace(";", ","));
        }
    }
}
