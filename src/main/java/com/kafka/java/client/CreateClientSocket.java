package com.kafka.java.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateClientSocket {
    private final Socket clientSocket;

    public CreateClientSocket(ServerSocket serverSocket) throws IOException {
        this.clientSocket = serverSocket.accept();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
