package com.kafka.java.client;

import com.kafka.java.KafkaBroker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CreateClientSocket {
    private final Socket clientSocket;

    public CreateClientSocket(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    public void execute() {
        try {
            System.out.println("Current Thread: " + Thread.currentThread().getName() + " started: " + Thread.currentThread().getState() + " and is listening for request..");
            // Wait for connection from client.

            System.out.println("Current Thread: " + Thread.currentThread().getName() + " started: " + Thread.currentThread().getState() + " and is responding to request..");
            KafkaBroker kafkaBroker = new KafkaBroker(clientSocket, Thread.currentThread());
            kafkaBroker.respondToKafkaClientRequest();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
