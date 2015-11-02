package com.hackerrank.cepservice.core.dao;

import java.io.Serializable;
import java.util.List;

import com.hackerrank.cepservice.model.filter.AbstractFilter;

/**
 *
 * @param <E> Entidade serializável
 * @param <I> Identificador serializável
 * @param <F> Filtro serializável
 */
public interface DAO<E extends Serializable, I extends Serializable, F extends AbstractFilter> {

    /**
     * Obtem uma entidade a partir do seu identificador
     * @param id - identificador do objeto
     * @return Registro encontrado ou null caso não seja possível encontralo
     */
    E obter(I id);

    /**
     * Remove uma entidade a partir do seu identificador
     * @param entity - objeto a ser removido
     */
    void remover(E entity);

    /**
     * Atualiza uma entidade a partir do seu identificador
     * @param entity - entidade a ser atualizado
     */
    void atualizar(E entity);

    /**
     * Insere uma nova entidade e retorna seu identificador
     * @param entity - entidade a ser inserido
     * @return identificador da entidade
     */
    I inserir(E entity);

    /**
     * Recupera todas as entidades
     * @return lista com as entidades
     */
    List<E> listar();

    /**
     * Recupera todas as entidades que casam com o filtro informado
     * @return lista com as entidades
     */
    List<E> listar(F filter);
}
