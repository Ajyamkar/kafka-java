package com.kafka.java.models;

public class ApiVersionsResponseV4 {
    private int messageSize;
    private int correlationId;
    private int errorCode;
    private ApiVersion[] apiVersions;
    private int throttleTime;
    private int tagBuffer;

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ApiVersion[] getApiVersions() {
        return apiVersions;
    }

    public void setApiVersions(ApiVersion[] apiVersions) {
        this.apiVersions = apiVersions;
    }

    public int getThrottleTime() {
        return throttleTime;
    }

    public void setThrottleTime(int throttleTime) {
        this.throttleTime = throttleTime;
    }

    public int getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(int tagBuffer) {
        this.tagBuffer = tagBuffer;
    }
}
