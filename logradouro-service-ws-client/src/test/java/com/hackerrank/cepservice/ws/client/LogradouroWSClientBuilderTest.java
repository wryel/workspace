package com.hackerrank.cepservice.ws.client;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ClientBuilder.class)
public class LogradouroWSClientBuilderTest {

    @Mock
    private ClientBuilder clientBuilder;

    @Mock
    private Client client;

    @Mock
    private ResteasyWebTarget resteasyWebTarget;

    @Before
    public void setUp() {

        mockStatic(ClientBuilder.class);

        when(ClientBuilder.newBuilder()).thenReturn(clientBuilder);

        when(clientBuilder.build()).thenReturn(client);

        when(client.register((Class<?>) com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class)).thenReturn(client);

        when(client.target(anyString())).thenReturn(resteasyWebTarget);
    }

    /**
     * Testa apenas comportamento interno.
     */
    @Test
    public void deveCertificarSeQueSetouUrlBaseCorretamente() {

        String baseUrl = "http://teste.com";

        LogradouroWSClientBuilder.newInstance().setBaseUrl(baseUrl).build();

        verify(client).target(baseUrl);
    }
}
