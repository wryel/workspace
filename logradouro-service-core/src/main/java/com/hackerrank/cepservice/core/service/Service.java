package com.hackerrank.cepservice.core.service;

import java.io.Serializable;
import java.util.List;

import com.hackerrank.cepservice.core.repository.Repository;
import com.hackerrank.cepservice.model.filter.AbstractFilter;

/**
 * Esta camada é responsável por comportar regras de negócio.
 * É necessário sobrescrever os métodos comuns de inserção/atualização por exemplo, para comportar suas regras de negócio.
 * 
 * @see Repository
 */
public interface Service<E extends Serializable, I extends Serializable, F extends AbstractFilter> {

    /**
     * @see Repository#obter(Serializable)
     */
    E obter(I id);

    /**
     * @see Repository#inserir(Serializable)
     */
    I inserir(E entity) throws ServiceException;

    /**
     * @see Repository#remover(Serializable)
     */
    void remover(E entity) throws ServiceException;

    /**
     * @see Repository#atualizar(Serializable)
     */
    void atualizar(E entity) throws ServiceException;

    /**
     * @see Repository#listar()
     */
    List<E> listar();

    /**
     * @see Repository#listar(AbstractFilter)
     */
    List<E> listar(F filter);
}
