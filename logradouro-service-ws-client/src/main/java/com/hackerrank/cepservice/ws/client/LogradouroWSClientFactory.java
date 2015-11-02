package com.hackerrank.cepservice.ws.client;

public final class LogradouroWSClientFactory {

    private LogradouroWSClientFactory() {
        
    }
    
    public static LogradouroWSClient newLogradouroWSClient(String baseUrl) {
        return new LogradouroWSClientBuilder().setBaseUrl(baseUrl).build();
    }
}
