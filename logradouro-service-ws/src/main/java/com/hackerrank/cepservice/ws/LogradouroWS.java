package com.hackerrank.cepservice.ws;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hackerrank.cepservice.core.service.LogradouroService;
import com.hackerrank.cepservice.core.service.ServiceException;
import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.ws.client.LogradouroWSClient;
import com.hackerrank.cepservice.ws.client.Result;
import com.hackerrank.cepservice.ws.client.Result.Status;

@Component
public class LogradouroWS implements LogradouroWSClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogradouroWS.class);

    @Autowired
    private LogradouroService logradouroService;

    @Override
    public Result<Logradouro> pesquisarPorCep(String cep) {

        Result<Logradouro> result = new Result<Logradouro>();

        try {

            result.setDate(new Date());

            LOGGER.info("cep informado [{}]", cep);

            result.setObject(logradouroService.pesquisarPorCep(cep));
            result.setStatus(Status.OK);

            LOGGER.info("logradouro [{}] para o cep infomrado [{}]", result.getObject(), cep);

        } catch (ServiceException serviceException) {

            LOGGER.info("erro ao pesquisar logradouro para o cep informado [{}]: [{}]", cep, result, serviceException);

            result.setErrorMessage(serviceException.getMessage());
            result.setStatus(Status.FAIL);

        }

        return result;
    }

    public void setLogradouroService(LogradouroService logradouroService) {
        this.logradouroService = logradouroService;
    }
}
