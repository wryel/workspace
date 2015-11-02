package com.hackerrank.cepservice.core.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hackerrank.cepservice.core.repository.LogradouroRepository;
import com.hackerrank.cepservice.core.service.adapter.LogradouroWSClientAdapter;
import com.hackerrank.cepservice.core.service.adapter.LogradouroWSClientAdapterException;
import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.model.filter.LogradouroFilter;

@Service
public class LogradouroServiceImpl extends AbstractService<Logradouro, Long, LogradouroFilter, LogradouroRepository> implements LogradouroService {
 
    @Autowired
    private LogradouroWSClientAdapter logradouroWSClientAdapter;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long inserir(Logradouro logradouro) throws ServiceException {

        validarCamposObrigatorios(logradouro);

        Logradouro logradouroDoServico = pesquisarPorCepPassandoPeloWS(logradouro.getCep());

        if (logradouroDoServico != null && logradouroDoServico.getCep().equals(logradouro.getCep())) {
            throw new ServiceException("J\u00e1 existe um logradouro cadastrado com o cep " + logradouro.getCep());
        }

        return super.inserir(logradouro);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void atualizar(Logradouro logradouro) throws ServiceException {

        validarCamposObrigatorios(logradouro);

        Logradouro logradouroDoServico = pesquisarPorCepPassandoPeloWS(logradouro.getCep());

        if (logradouroDoServico != null && logradouroDoServico.getCep().equals(logradouro.getCep()) && !logradouroDoServico.getId().equals(logradouro.getId())) {
            throw new ServiceException("J\u00e1 existe outro logradouro cadastrado com o cep " + logradouro.getCep());
        }

        super.atualizar(logradouro);
    }

    Logradouro pesquisarPorCepPassandoPeloWS(String cep) throws ServiceException {
        try {
            return logradouroWSClientAdapter.pesquisarPorCep(cep);
        } catch (LogradouroWSClientAdapterException logradouroWSClientAdapterException) {
            throw new ServiceException(logradouroWSClientAdapterException.getMessage(), logradouroWSClientAdapterException);
        }
    }

    @Override
    public Logradouro pesquisarPorCep(String cepInicial) throws ServiceException {

        validarTamanhoDoCep(cepInicial);

        Logradouro logradouro = null;

        for (int x = 0; x < Logradouro.TAMANHO_CEP && logradouro == null; x++) {
            logradouro = getRepository().obterPorCep(StringUtils.rightPad(StringUtils.substring(cepInicial, 0, Logradouro.TAMANHO_CEP - x), Logradouro.TAMANHO_CEP, NumberUtils.INTEGER_ZERO.toString()));
        }

        return logradouro;
    }

    private void validarCamposObrigatorios(Logradouro logradouro) throws ServiceException {
        // remove complexidade ciclomática, uma opção seria isolar validações em uma classe
        validarRua(logradouro);
        validarNumero(logradouro);
        validarCep(logradouro);
        validarCidade(logradouro);
        validarEstado(logradouro);
    }

    private void validarEstado(Logradouro logradouro) throws ServiceException {
        if (StringUtils.isBlank(logradouro.getEstado())) {
            throw new ServiceException("Estado \u00e9 requerido");
        }
    }

    private void validarCidade(Logradouro logradouro) throws ServiceException {
        if (StringUtils.isBlank(logradouro.getCidade())) {
            throw new ServiceException("Cidade \u00e9 requerido");
        }
    }

    private void validarNumero(Logradouro logradouro) throws ServiceException {
        if (logradouro.getNumero() == null) {
            throw new ServiceException("N\u00famero \u00e9 requerido");
        }
    }

    private void validarRua(Logradouro logradouro) throws ServiceException {
        if (StringUtils.isBlank(logradouro.getRua())) {
            throw new ServiceException("Rua \u00e9 requerido");
        }
    }

    private void validarCep(Logradouro logradouro) throws ServiceException {
        if (StringUtils.isBlank(logradouro.getCep())) {
            throw new ServiceException("Cep \u00e9 requerido");
        }
    }
    
    private void validarTamanhoDoCep(String cep) throws ServiceException {
        if (!StringUtils.isNumeric(cep) || cep.length() != Logradouro.TAMANHO_CEP) {
            throw new ServiceException("Cep inv\u00e1lido");
        }
    }

    public void setLogradouroWSClientAdapter(LogradouroWSClientAdapter logradouroWSClientAdapter) {
        this.logradouroWSClientAdapter = logradouroWSClientAdapter;
    }
}