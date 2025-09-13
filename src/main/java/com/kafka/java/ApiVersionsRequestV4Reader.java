package com.kafka.java;

import com.kafka.java.models.ApiVersionsRequestV4;

import java.io.DataInputStream;
import java.io.IOException;

public class ApiVersionsRequestV4Reader {
    private final ApiVersionsRequestV4 apiVersionRequestV4;

    public ApiVersionsRequestV4Reader() {
        apiVersionRequestV4 = new ApiVersionsRequestV4();
    }

//    public ApiVersionsRequestV4Reader(ApiVersionsRequestV4 apiVersionRequestV4, DataInputStream dataInputStream) {
//        this.apiVersionRequestV4 = apiVersionRequestV4;
//        this.dataInputStream = dataInputStream;
//    }

    public ApiVersionsRequestV4 getApiVersionRequestV4() {
        return apiVersionRequestV4;
    }

    /**
     * Reads from request inputStream
     */
    public void readApiVersionsRequestV4(DataInputStream dataInputStream) throws IOException {
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
}
