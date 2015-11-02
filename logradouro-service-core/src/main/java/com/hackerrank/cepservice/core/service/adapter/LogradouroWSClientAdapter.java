package com.hackerrank.cepservice.core.service.adapter;

import com.hackerrank.cepservice.model.Logradouro;

public interface LogradouroWSClientAdapter {

    Logradouro pesquisarPorCep(String cep) throws LogradouroWSClientAdapterException;
}
