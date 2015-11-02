package com.hackerrank.cepservice.ws.client;

import javax.ws.rs.client.ClientBuilder;

import org.apache.commons.lang3.builder.Builder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

class LogradouroWSClientBuilder implements Builder<LogradouroWSClient> {
    
    private static final ClientBuilder CLIENT_BUILDER = ClientBuilder.newBuilder();
    
    static {
        CLIENT_BUILDER.register(JacksonJsonProvider.class);
    }
    
    private String baseUrl;

    public static LogradouroWSClientBuilder newInstance() {
        return new LogradouroWSClientBuilder();
    }

    @Override
    public LogradouroWSClient build() {
        return ((ResteasyWebTarget) CLIENT_BUILDER.build().target(baseUrl)).proxy(LogradouroWSClient.class);      
    }

    public LogradouroWSClientBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
}