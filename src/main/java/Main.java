import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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
            clientSocket = serverSocket.accept();

            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(in);
            System.out.println("dataInputStream.readInt()"+dataInputStream.readInt());
            System.out.println("dataInputStream.readShort()"+dataInputStream.readShort());
            System.out.println("dataInputStream.readShort()"+dataInputStream.readShort());
            int correlationId= dataInputStream.readInt();

            DataOutputStream dataOutputStream = new DataOutputStream(out);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            System.out.println("correlationId:"+correlationId);

            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(correlationId);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
