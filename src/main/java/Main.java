import com.kafka.java.KafkaBroker;
import com.kafka.java.client.CreateClientSocket;
import com.kafka.java.server.CreateServerSocket;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.err.println("Logs from your program will appear here!");

        int BrokerPort = 9092;
        try {
            CreateServerSocket serverSocket = new CreateServerSocket(BrokerPort);
            // Wait for connection from client.
            CreateClientSocket clientSocket = new CreateClientSocket(serverSocket.getServerSocket());

            KafkaBroker kafkaBroker = new KafkaBroker(clientSocket.getClientSocket());
            kafkaBroker.respondToKafkaClientRequest();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
