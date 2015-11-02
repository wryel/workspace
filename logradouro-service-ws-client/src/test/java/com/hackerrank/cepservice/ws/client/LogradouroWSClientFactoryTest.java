package com.hackerrank.cepservice.ws.client;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LogradouroWSClientFactoryTest {

    @Test
    public void deveCriarInstanciaDeLogradouroWSClient() {
        assertNotNull("intancia de client para o serviço de logradouro não criada", LogradouroWSClientFactory.newLogradouroWSClient("http://www.teste.com"));
    }
}
