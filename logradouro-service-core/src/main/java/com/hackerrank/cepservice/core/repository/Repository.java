package com.hackerrank.cepservice.core.repository;

import java.io.Serializable;
import java.util.List;

import com.hackerrank.cepservice.core.dao.DAO;
import com.hackerrank.cepservice.model.filter.AbstractFilter;

/**
 * @see DAO
 */
public interface Repository<E extends Serializable, I extends Serializable, F extends AbstractFilter> {

    /**
     * @see DAO#obter(Serializable)
     */
    E obter(I id);

    /**
     * @see DAO#remover
     */
    void remover(E entity);

    /**
     * @see DAO#atualizar(Serializable)
     */
    void atualizar(E entity);

    /**
     * @see DAO#inserir(Serializable)
     */
    I inserir(E entity);

    /**
     * @see DAO#listar()
     */
    List<E> listar();

    /**
     * @see DAO#listar(AbstractFilter)
     */
    List<E> listar(F filter);
}
