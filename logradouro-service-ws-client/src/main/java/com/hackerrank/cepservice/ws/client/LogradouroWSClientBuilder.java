package com.hackerrank.cepservice.ws.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.commons.lang3.builder.Builder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

class LogradouroWSClientBuilder implements Builder<LogradouroWSClient> {

    private WebTarget webTarget;

    public static LogradouroWSClientBuilder newInstance() {
        return new LogradouroWSClientBuilder();
    }

    @Override
    public LogradouroWSClient build() {
        return ((ResteasyWebTarget) webTarget).proxy(LogradouroWSClient.class);
    }

    public LogradouroWSClientBuilder setBaseUrl(String url) {
        webTarget = ClientBuilder.newBuilder().build().register(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class).target(url);
        return this;
    }
}
