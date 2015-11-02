package com.hackerrank.cepservice.core.service.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.ProcessingException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.io.Resources;
import com.hackerrank.cepservice.core.ApplicationRuntimeException;
import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.ws.client.LogradouroWSClientFactory;
import com.hackerrank.cepservice.ws.client.Result;

@Service
public class LogradouroWSClientAdapterImpl implements LogradouroWSClientAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogradouroWSClientAdapterImpl.class);
  
    private static final String LOGRADOUROS_ENDPOINT;
    
    static {
     
        InputStream inputStream = null;
        
        try {

            final Properties properties = new Properties();
            
            inputStream = Resources.asByteSource(Resources.getResource("logradouro-service-core.properties")).openBufferedStream();

            properties.load(inputStream);            
            
            LOGRADOUROS_ENDPOINT = properties.getProperty("logradouros.endpoint");
            
        } catch (final IOException ioException) {

            final String message = "Erro ao carregar endpoint para o serviço de logradouros: " + ioException.getMessage();
            
            LOGGER.error(message, ioException);
            
            throw new ApplicationRuntimeException(message, ioException);
            
        } finally {
            
            IOUtils.closeQuietly(inputStream);
            
        }        
    }
    
    @Override
    public Logradouro pesquisarPorCep(String cep) throws LogradouroWSClientAdapterException {

        try {

            Result<Logradouro> result = LogradouroWSClientFactory.newLogradouroWSClient(LOGRADOUROS_ENDPOINT).pesquisarPorCep(cep);

            if (result.getStatus() == Result.Status.FAIL) {
                throw new LogradouroWSClientAdapterException(result.getErrorMessage());
            }

            return result.getObject();

        } catch (ProcessingException processingException) {

            LOGGER.error("Ocorreu um erro com o servi\u00e7o de logradouro: [{}]", processingException.getMessage(), processingException);

            throw new LogradouroWSClientAdapterException("Ocorreu um erro ao tentar validar seu cep, tente novamente mais tarde", processingException);

        }
    }
}
