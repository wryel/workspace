package com.hackerrank.cepservice.core.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Iterables;
import com.hackerrank.cepservice.core.dao.LogradouroDAO;
import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.model.filter.LogradouroFilter;

@Repository
public class LogradouroRepositoryImpl extends AbstractRepository<Logradouro, Long, LogradouroFilter, LogradouroDAO> implements LogradouroRepository {

    @Override
    public Logradouro obterPorCep(String cep) {
        LogradouroFilter logradouroFilter = new LogradouroFilter();
        logradouroFilter.setCepEquals(cep);
        return Iterables.getFirst(listar(logradouroFilter), null);
    }

    @Override
    @Autowired
    public void setDao(LogradouroDAO logradouroDAO) {
        super.setDao(logradouroDAO);
    }
}
