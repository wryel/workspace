package com.hackerrank.cepservice.core.repository;

import java.io.Serializable;
import java.util.List;

import com.hackerrank.cepservice.core.dao.DAO;
import com.hackerrank.cepservice.model.filter.AbstractFilter;

public abstract class AbstractRepository<E extends Serializable, I extends Serializable, F extends AbstractFilter, D extends DAO<E, I, F>> implements Repository<E, I, F> {

    private D dao;

    @Override
    public E obter(I id) {
        return dao.obter(id);
    }

    @Override
    public void remover(E entity) {
        dao.remover(entity);
    }

    @Override
    public void atualizar(E entity) {
        dao.atualizar(entity);
    }

    @Override
    public I inserir(E entity) {
        return dao.inserir(entity);
    }

    @Override
    public List<E> listar() {
        return dao.listar();
    }

    @Override
    public List<E> listar(F filter) {
        return dao.listar(filter);
    }

    public void setDao(D dao) {
        this.dao = dao;
    }
}