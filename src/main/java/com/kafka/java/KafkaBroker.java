package com.kafka.java;

import java.io.*;
import java.net.Socket;

public class KafkaBroker {
    private final InputStream in;
    private final Socket clientSocket;
    private final ApiVersionsRequestV4Reader apiVersionsRequestV4Reader;
    private final LogApiVersionRequestV4Data logApiVersionRequestV4Data;
    private final ApiVersionsResponseWriter apiVersionsResponseWriter;

    public KafkaBroker(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = clientSocket.getInputStream();

        this.apiVersionsRequestV4Reader = new ApiVersionsRequestV4Reader();
        this.apiVersionsResponseWriter = new ApiVersionsResponseWriter(clientSocket.getOutputStream());
        this.logApiVersionRequestV4Data = new LogApiVersionRequestV4Data();
    }

    public void respondToKafkaClientRequest() {
        try {
            processApiVersions();
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

    /**
     * Process ApiVersions by reading request and responding to the response in v4.
     * Responds with ERROR 35 if the API Version is unsupported
     */
    public void processApiVersions() {
        try (DataInputStream dataInputStream = new DataInputStream(in)) {
            while (true) {
                try {
                    apiVersionsRequestV4Reader.readApiVersionsRequestV4(dataInputStream);

                    logApiVersionRequestV4Data.log(this.apiVersionsRequestV4Reader.getApiVersionRequestV4());

                    apiVersionsResponseWriter.sendApiVersionsResponseV4(apiVersionsRequestV4Reader.getApiVersionRequestV4());

                } catch (EOFException e) {
                    System.out.println("EOF : " + e.getMessage());
                    System.out.println("End of file reached.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
