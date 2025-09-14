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
            CreateClientSocket clientSocket = new CreateClientSocket(serverSocket.getServerSocket());
            clientSocket.setName("CreateClientSocketThread-T1");
            System.out.println("Current Thread: "+clientSocket.getName()+" state: "+clientSocket.getState());
            clientSocket.start();

            // Thread 2
            CreateClientSocket clientSocket2 = new CreateClientSocket(serverSocket.getServerSocket());
            clientSocket2.setName("CreateClientSocketThread-T2");
            System.out.println("Current Thread: "+clientSocket2.getName()+" state: "+clientSocket2.getState());
            clientSocket2.start();

            // Thread 3
            CreateClientSocket clientSocket3 = new CreateClientSocket(serverSocket.getServerSocket());
            clientSocket3.setName("CreateClientSocketThread-T3");
            System.out.println("Current Thread: "+clientSocket3.getName()+" state: "+clientSocket3.getState());
            clientSocket3.start();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
