package com.hackerrank.cepservice.ws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.hackerrank.cepservice.core.service.LogradouroService;
import com.hackerrank.cepservice.core.service.ServiceException;
import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.ws.client.Result;
import com.hackerrank.cepservice.ws.client.Result.Status;

@RunWith(PowerMockRunner.class)
public class LogradouroWSTest {

    @InjectMocks
    private LogradouroWS logradouroWS = new LogradouroWS();

    @Mock
    private LogradouroService logradouroService;

    @Test
    public void deveRetornarLogradouroQueFoiRetornadoPeloServico() throws ServiceException {

        Logradouro logradouro = logradouroValido();
        
        when(logradouroService.pesquisarPorCep(logradouro.getCep()))
            .thenReturn(logradouro);
        
        logradouroWS.pesquisarPorCep(logradouro.getCep());
        
        Result<Logradouro> result = logradouroWS.pesquisarPorCep(logradouro.getCep());
        
        assertEquals("Status diferente do esperado", Status.OK, result.getStatus());
        assertEquals("Logradouro diferente do esperado", logradouro, result.getObject());
    }
    
    @Test
    public void deveRetornarMensagemDeErroQuandoStatusForFail() throws ServiceException {
        
        String mensagemEsperada = "Uma mensagem de erro qualquer";
        
        when(logradouroService.pesquisarPorCep(anyString()))
            .thenThrow(new ServiceException(mensagemEsperada));
        
        Result<Logradouro> result = logradouroWS.pesquisarPorCep(RandomStringUtils.randomNumeric(Logradouro.TAMANHO_CEP));
        
        assertEquals("Status diferente do esperado", Status.FAIL, result.getStatus());
        assertEquals("Mensagem diferente do esperado", mensagemEsperada, result.getErrorMessage());
    }
    
    private Logradouro logradouroValido() {
        Logradouro logradouro = new Logradouro();
        logradouro.setBairro(RandomStringUtils.randomAlphabetic(10));
        logradouro.setCep(RandomStringUtils.randomNumeric(8));
        logradouro.setCidade(RandomStringUtils.randomAlphabetic(10));
        logradouro.setEstado(RandomStringUtils.randomAlphabetic(2));
        logradouro.setNumero(RandomUtils.nextInt(1, 999));
        logradouro.setRua(RandomStringUtils.randomAlphabetic(10));
        return logradouro;
    }
}
