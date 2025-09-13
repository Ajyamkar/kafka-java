package com.kafka.java;

import com.kafka.java.models.ApiVersion;
import com.kafka.java.models.ApiVersionsRequestV4;
import com.kafka.java.models.ApiVersionsResponseV4;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ApiVersionsResponseWriter {
    private ByteArrayOutputStream responseBuffer;
    private final DataOutputStream dataOutputStream;
    private final ApiVersionsResponseV4 apiVersionsResponseV4;

    public ApiVersionsResponseWriter(OutputStream out){
        this.dataOutputStream = new DataOutputStream(out);
        this.apiVersionsResponseV4 = new ApiVersionsResponseV4();
    }

    public ByteArrayOutputStream getResponseBuffer() {
        return responseBuffer;
    }

    public void setResponseBuffer(ByteArrayOutputStream responseBuffer) {
        this.responseBuffer = responseBuffer;
    }

    /**
     * Returns response to client by writing to output stream.
     * @throws IOException
     */
    public void sendApiVersionsResponseV4(ApiVersionsRequestV4 apiVersionsRequestV4) throws IOException {
        this.constructApiVersionResponseV4(apiVersionsRequestV4);
        this.calculateResponseMessageSize();

        this.dataOutputStream.writeInt(this.apiVersionsResponseV4.getMessageSize());
        this.dataOutputStream.write(this.getResponseBuffer().toByteArray());
        this.dataOutputStream.flush();
    }

    /**
     * Constructs API Versions Response V4 all fields except messageSize.
     */
    private void constructApiVersionResponseV4(ApiVersionsRequestV4 apiVersionsRequestV4) {
        this.apiVersionsResponseV4.setCorrelationId(apiVersionsRequestV4.getCorrelationId());

        if (apiVersionsRequestV4.getApiVersion() >= 0 && apiVersionsRequestV4.getApiVersion() <= 4) {
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
