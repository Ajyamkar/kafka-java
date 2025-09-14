package com.kafka.java.client;

import com.kafka.java.KafkaBroker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateClientSocket extends Thread {
    private final ServerSocket serverSocket;

    public CreateClientSocket(ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Current Thread: " + Thread.currentThread().getName() + " started: " + Thread.currentThread().getState() + " and is listening for request..");
            // Wait for connection from client.
            Socket clientSocket = this.serverSocket.accept();

            System.out.println("Current Thread: " + Thread.currentThread().getName() + " started: " + Thread.currentThread().getState() + " and is responding to request..");
            KafkaBroker kafkaBroker = new KafkaBroker(clientSocket, currentThread());
            kafkaBroker.respondToKafkaClientRequest();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
