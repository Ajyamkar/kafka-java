import com.kafka.java.client.ClientSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.err.println("Logs from your program will appear here!");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 9092;
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            ClientSocket clientSocketInstance = new ClientSocket(serverSocket);
            clientSocket = clientSocketInstance.getClientSocket();
            clientSocketInstance.processApiVersions();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                System.out.println("clientSocket.isClosed() =" + clientSocket.isClosed());
                System.out.println("clientSocket.isConnected()" + clientSocket.isConnected());
                if (clientSocket != null && clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
