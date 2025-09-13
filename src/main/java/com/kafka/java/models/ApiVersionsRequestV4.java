package com.kafka.java.models;

public class ApiVersionsRequestV4 {
    private int messageSize;
    private int apiKey;
    private int apiVersion;
    private int correlationId;
    private String clientId;
    private int tagBuffer;
    private String requestBodyClientId;
    private String requestBodyClientVersion;
    private int requestBodyTagBuffer;

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public void setApiKey(int apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setTagBuffer(int tagBuffer) {
        this.tagBuffer = tagBuffer;
    }

    public void setRequestBodyClientId(String requestBodyClientId) {
        this.requestBodyClientId = requestBodyClientId;
    }

    public void setRequestBodyClientVersion(String requestBodyClientVersion) {
        this.requestBodyClientVersion = requestBodyClientVersion;
    }

    public void setRequestBodyTagBuffer(int requestBodyTagBuffer) {
        this.requestBodyTagBuffer = requestBodyTagBuffer;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public int getApiKey() {
        return apiKey;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public String getClientId() {
        return clientId;
    }

    public int getTagBuffer() {
        return tagBuffer;
    }

    public String getRequestBodyClientId() {
        return requestBodyClientId;
    }

    public String getRequestBodyClientVersion() {
        return requestBodyClientVersion;
    }

    public int getRequestBodyTagBuffer() {
        return requestBodyTagBuffer;
    }

    public int getApiVersion() {
        return apiVersion;
    }
}
