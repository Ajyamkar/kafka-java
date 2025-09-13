package com.kafka.java.models;

public class ApiVersion {
    private int apiKey;
    private int minSupportedApiVersion;
    private int maxSupportedApiVersion;
    private int tagBuffer;

    public int getApiKey() {
        return apiKey;
    }

    public void setApiKey(int apiKey) {
        this.apiKey = apiKey;
    }

    public int getMaxSupportedApiVersion() {
        return maxSupportedApiVersion;
    }

    public void setMaxSupportedApiVersion(int maxSupportedApiVersion) {
        this.maxSupportedApiVersion = maxSupportedApiVersion;
    }

    public int getMinSupportedApiVersion() {
        return minSupportedApiVersion;
    }

    public void setMinSupportedApiVersion(int minSupportedApiVersion) {
        this.minSupportedApiVersion = minSupportedApiVersion;
    }

    public int getTagBuffer() {
        return tagBuffer;
    }

    public void setTagBuffer(int tagBuffer) {
        this.tagBuffer = tagBuffer;
    }
}
