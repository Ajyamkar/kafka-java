package com.kafka.java;

import com.kafka.java.models.ApiVersionsRequestV4;

public class LogApiVersionRequestV4Data {

    public void log(ApiVersionsRequestV4 apiVersionsRequestV4) {
        System.out.println("NEW REQUEST.....");

        System.out.println("messageSize: " + apiVersionsRequestV4.getMessageSize());
        System.out.println("requestApiKey: " + apiVersionsRequestV4.getApiKey());
        System.out.println("requestApiVersion: " + apiVersionsRequestV4.getApiVersion());
        System.out.println("correlationId: " + apiVersionsRequestV4.getCorrelationId());
        System.out.println("clientId: " + apiVersionsRequestV4.getClientId());
        System.out.println("tagbuffer: " + apiVersionsRequestV4.getTagBuffer());
        System.out.println("requestBodyClientId: " + apiVersionsRequestV4.getRequestBodyClientId());
        System.out.println("requestBodyClientVersion: " + apiVersionsRequestV4.getRequestBodyClientVersion());
        System.out.println("requestBodyTagBuffer: " + apiVersionsRequestV4.getRequestBodyTagBuffer());

        System.out.println();
        System.out.println();

    }
}
