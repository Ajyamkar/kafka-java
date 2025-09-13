package com.kafka.java.client;

import com.kafka.java.models.ApiVersion;
import com.kafka.java.models.ApiVersionsRequestV4;
import com.kafka.java.models.ApiVersionsResponseV4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocket {
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;
    private ByteArrayOutputStream responseBuffer;
    private final DataOutputStream dataOutputStream;
    private final ApiVersionsRequestV4 apiVersionRequestV4;
    private final ApiVersionsResponseV4 apiVersionsResponseV4;

    public ClientSocket(ServerSocket serverSocket) throws IOException {
        this.clientSocket = serverSocket.accept();
        this.in = clientSocket.getInputStream();
        this.out = clientSocket.getOutputStream();
        this.dataOutputStream = new DataOutputStream(out);
        this.apiVersionRequestV4 = new ApiVersionsRequestV4();
        this.apiVersionsResponseV4 = new ApiVersionsResponseV4();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ByteArrayOutputStream getResponseBuffer() {
        return responseBuffer;
    }

    public void setResponseBuffer(ByteArrayOutputStream responseBuffer) {
        this.responseBuffer = responseBuffer;
    }

    /**
     * Process ApiVersions by reading request and responding to the response in v4.
     * Responds with ERROR 35 if the API Version is unsupported
     */
    public void processApiVersions() {
        try (DataInputStream dataInputStream = new DataInputStream(in)) {
            while (true) {
                try {
                    readApiVersionsRequestV4(dataInputStream);
                    logApiVersionRequestV4Data();
                    sendApiVersionsResponseV4();

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

    /**
     * Reads from request inputStream
     */
    private void readApiVersionsRequestV4(DataInputStream dataInputStream) throws IOException {
        this.apiVersionRequestV4.setMessageSize(dataInputStream.readInt());
        this.apiVersionRequestV4.setApiKey(dataInputStream.readShort());
        this.apiVersionRequestV4.setApiVersion(dataInputStream.readShort());
        this.apiVersionRequestV4.setCorrelationId(dataInputStream.readInt());
        this.apiVersionRequestV4.setClientId(new String(dataInputStream.readNBytes(11)));
        this.apiVersionRequestV4.setTagBuffer(dataInputStream.read());
        this.apiVersionRequestV4.setRequestBodyClientId(new String(dataInputStream.readNBytes(10)));
        this.apiVersionRequestV4.setRequestBodyClientVersion(new String(dataInputStream.readNBytes(4)));
        this.apiVersionRequestV4.setRequestBodyTagBuffer(dataInputStream.read());
    }

    private void logApiVersionRequestV4Data() {
        System.out.println("NEW REQUEST.....");

        System.out.println("messageSize: " + apiVersionRequestV4.getMessageSize());
        System.out.println("requestApiKey: " + apiVersionRequestV4.getApiKey());
        System.out.println("requestApiVersion: " + apiVersionRequestV4.getApiVersion());
        System.out.println("correlationId: " + apiVersionRequestV4.getCorrelationId());
        System.out.println("clientId: " + apiVersionRequestV4.getClientId());
        System.out.println("tagbuffer: " + apiVersionRequestV4.getTagBuffer());
        System.out.println("requestBodyClientId: " + apiVersionRequestV4.getRequestBodyClientId());
        System.out.println("requestBodyClientVersion: " + apiVersionRequestV4.getRequestBodyClientVersion());
        System.out.println("requestBodyTagBuffer: " + apiVersionRequestV4.getRequestBodyTagBuffer());

        System.out.println();
        System.out.println();
    }

    /**
     * Returns response to client by writing to output stream.
     *
     * @throws IOException
     */
    private void sendApiVersionsResponseV4() throws IOException {
        this.constructApiVersionResponseV4();
        this.calculateResponseMessageSize();

        this.dataOutputStream.writeInt(this.apiVersionsResponseV4.getMessageSize());
        this.dataOutputStream.write(this.getResponseBuffer().toByteArray());
        this.dataOutputStream.flush();
    }

    /**
     * Constructs API Versions Response V4 all fields except messageSize.
     */
    private void constructApiVersionResponseV4() {
        this.apiVersionsResponseV4.setCorrelationId(this.apiVersionRequestV4.getCorrelationId());

        if (this.apiVersionRequestV4.getApiVersion() >= 0 && this.apiVersionRequestV4.getApiVersion() <= 4) {
            this.apiVersionsResponseV4.setErrorCode(0);
        } else {
            this.apiVersionsResponseV4.setErrorCode(35);
        }

        int apiKey = 17;
        ApiVersion[] apiVersions = new ApiVersion[3];
        int count = 0;
        while (count < 3) {
            ApiVersion apiVersion = new ApiVersion();
            apiVersion.setApiKey(apiKey);
            apiVersion.setMinSupportedApiVersion(0);
            apiVersion.setMaxSupportedApiVersion(4);
            apiVersion.setTagBuffer(0);

            apiVersions[count] = apiVersion;
            count++;
            apiKey++;
        }

        this.apiVersionsResponseV4.setApiVersions(apiVersions);
        this.apiVersionsResponseV4.setThrottleTime(0);
        this.apiVersionsResponseV4.setTagBuffer(0);
    }

    /**
     * Response messageSize is calculated based on the total number of bytes of response fields that will be sent.
     * To calculate total response bytes, we are using {@DataOutputStream} to wrap around {@ByteArrayOutputStream}.
     *
     * @throws IOException
     */
    private void calculateResponseMessageSize() throws IOException {
        ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
        DataOutputStream responseBody = new DataOutputStream(responseBuffer);

        // Response Header v0
        responseBody.writeInt(this.apiVersionsResponseV4.getCorrelationId()); // correlationId

        // Response body v4
        responseBody.writeShort(this.apiVersionsResponseV4.getErrorCode()); // errorCode

        // API Version Array
        // Array length + 1 (I am returning three versions with min version 0, max 4)
        responseBody.write(this.apiVersionsResponseV4.getApiVersions().length + 1);

        for (int i = 0; i < this.apiVersionsResponseV4.getApiVersions().length; i++) {
            ApiVersion apiVersion = this.apiVersionsResponseV4.getApiVersions()[i];

            responseBody.writeShort(apiVersion.getApiKey()); // API key
            responseBody.writeShort(apiVersion.getMinSupportedApiVersion()); // min version
            responseBody.writeShort(apiVersion.getMaxSupportedApiVersion()); // max version
            responseBody.write(apiVersion.getTagBuffer()); // tag buffer
        }

        // throttle time
        responseBody.writeInt(this.apiVersionsResponseV4.getThrottleTime());

        // tag buffer
        responseBody.write(this.apiVersionsResponseV4.getTagBuffer());

        this.apiVersionsResponseV4.setMessageSize(responseBuffer.size());
        this.setResponseBuffer(responseBuffer);
    }
}
