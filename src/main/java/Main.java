import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

            DataOutputStream dataOutputStream = new DataOutputStream(out);


            try (DataInputStream dataInputStream = new DataInputStream(in)) {
                while (true) {
                    try {
                        System.out.println("NEW REQUEST.....");

                        int messageSize = dataInputStream.readInt();
                        int apiKey = dataInputStream.readShort();
                        int apiVersion = dataInputStream.readShort();
                        int correlationId = dataInputStream.readInt();
                        byte[] clientId = dataInputStream.readNBytes(11);
                        int tagbuffer = dataInputStream.read();
                        String requestBodyClientId = new String( dataInputStream.readNBytes(10));
                        String requestBodyClientVersion = new String(dataInputStream.readNBytes(4));
                        int requestBodyTagBuffer = dataInputStream.read();

                        System.out.println("messageSize: " + messageSize);
                        System.out.println("requestApiKey: " + apiKey);
                        System.out.println("requestApiVersion: " + apiVersion);
                        System.out.println("correlationId: " + correlationId);

                        System.out.println("clientId: " + new String(clientId));
                        System.out.println("tagbuffer: " + tagbuffer);
                        System.out.println("requestBodyClientId: " + requestBodyClientId);
                        System.out.println("requestBodyClientVersion: " + requestBodyClientVersion);
                        System.out.println("requestBodyTagBuffer: "+requestBodyTagBuffer);

                        ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
                        getResponseBody(responseBuffer, apiVersion, correlationId);

                        dataOutputStream.writeInt(responseBuffer.size()); // message size
                        dataOutputStream.write(responseBuffer.toByteArray()); // responseBody
                        dataOutputStream.flush();

                        System.out.println();
                        System.out.println();
                    } catch (EOFException e) {
                        System.out.println("EOF : " + e.getMessage());
                        System.out.println("End of file reached.");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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

    private static void getResponseBody(ByteArrayOutputStream responseBuffer, int apiVersion, int correlationId) throws IOException {
        DataOutputStream responseBody = new DataOutputStream(responseBuffer);

        int errorCode = 35;
        if (apiVersion >= 0 && apiVersion <= 4) {
            errorCode = 0;
        }

        // Response Header v0
        responseBody.writeInt(correlationId); // correlationId

        // Response body v4
        responseBody.writeShort(errorCode); // errorCode

        // API Version Array
        // Array length + 1 (I am returning three versions with min version 0, max 4)
        responseBody.write(4);

        // Element 1
        responseBody.writeShort(17); // API key
        responseBody.writeShort(0); // min version
        responseBody.writeShort(4); // max version
        responseBody.write(0); // tag buffer

        // Element 2
        responseBody.writeShort(18); // API key
        responseBody.writeShort(0); // min version
        responseBody.writeShort(4); // max version
        responseBody.write(0); // tag buffer

        // Element 3
        responseBody.writeShort(19); // API key
        responseBody.writeShort(0); // min version
        responseBody.writeShort(4); // max version
        responseBody.write(0); // tag buffer

        // throttle time
        responseBody.writeInt(0);

        // tag buffer
        responseBody.write(0);
    }
}
