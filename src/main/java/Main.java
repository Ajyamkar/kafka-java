import com.kafka.java.client.CreateClientSocket;
import com.kafka.java.server.CreateServerSocket;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.err.println("Logs from your program will appear here!");

        int BrokerPort = 9092;

        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            CreateServerSocket serverSocket = new CreateServerSocket(BrokerPort);
            while (true) {
                System.out.println("listening...");
                Socket clientSocket = serverSocket.getServerSocket().accept();

                executor.submit(() -> {
                    // TODO: Remove the client instance and directly call kafka broker
                    CreateClientSocket clientSocketInstance = null;
                    try {
                        clientSocketInstance = new CreateClientSocket(clientSocket);
                        clientSocketInstance.execute();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
