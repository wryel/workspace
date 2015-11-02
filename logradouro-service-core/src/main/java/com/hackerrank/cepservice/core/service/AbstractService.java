package com.hackerrank.cepservice.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hackerrank.cepservice.core.repository.Repository;
import com.hackerrank.cepservice.model.filter.AbstractFilter;

public abstract class AbstractService<E extends Serializable, I extends Serializable, F extends AbstractFilter, R extends Repository<E, I, F>> implements Service<E, I, F> {

    private R repository;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public E obter(I id) {
        return repository.obter(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public I inserir(E entity) throws ServiceException {
        return repository.inserir(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remover(E entity) throws ServiceException {
        repository.remover(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void atualizar(E entity) throws ServiceException {
        repository.atualizar(entity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<E> listar() {
        return repository.listar();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<E> listar(F filter) {
        return repository.listar(filter);
    }

    @Autowired
    public void setRepository(R repository) {
        this.repository = repository;
    }

    public R getRepository() {
        return repository;
    }
}