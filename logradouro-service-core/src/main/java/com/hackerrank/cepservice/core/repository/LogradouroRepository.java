package com.hackerrank.cepservice.core.repository;

import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.model.filter.LogradouroFilter;

public interface LogradouroRepository extends Repository<Logradouro, Long, LogradouroFilter> {

    /**
     * Obtem logradouro pelo campo cep
     * @param cep
     * @return Logradouro
     */
    Logradouro obterPorCep(String cep);
}
