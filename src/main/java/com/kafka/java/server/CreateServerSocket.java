package com.kafka.java.server;

import java.io.IOException;
import java.net.ServerSocket;

public class CreateServerSocket {
    private java.net.ServerSocket serverSocket;

    public CreateServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        // Since the tester restarts your program quite often, setting SO_REUSEADDR
        // ensures that we don't run into 'Address already in use' errors
        serverSocket.setReuseAddress(true);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
