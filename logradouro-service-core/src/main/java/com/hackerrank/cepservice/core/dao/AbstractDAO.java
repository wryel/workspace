package com.hackerrank.cepservice.core.dao;

import static com.hackerrank.cepservice.core.dao.DAOHelper.getId;
import static com.hackerrank.cepservice.core.dao.DAOHelper.newFilterInstance;
import static com.hackerrank.cepservice.core.dao.DAOHelper.safeCopyProperties;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.hackerrank.cepservice.model.filter.AbstractFilter;

public abstract class AbstractDAO<E extends Serializable, I extends Serializable, F extends AbstractFilter> implements DAO<E, I, F> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<E> entityClass;

    private Class<F> modelFilterClass;

    public AbstractDAO(Class<E> entityClass, Class<F> modelFilterClass) {
        this.entityClass = entityClass;
        this.modelFilterClass = modelFilterClass;
    }

    @Override
    public E obter(I id) {
        return entityManager.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public I inserir(E entity) {
        try {
            entityManager.persist(entity);
            return (I) getId(entity);
        } finally {
            entityManager.flush();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void remover(E entity) {
        E entityToRemove = entity;
        if (!entityManager.contains(entityToRemove)) {
            entityToRemove = obter((I) getId(entityToRemove));
        }
        entityManager.remove(entityToRemove);
        entityManager.flush();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void atualizar(E entity) {
        E managed = obter((I) getId(entity));
        safeCopyProperties(managed, entity);
        entityManager.merge(managed);
        entityManager.flush();
    }

    @Override
    public List<E> listar() {
        return listar(newFilterInstance(modelFilterClass));
    }

    @Override
    public List<E> listar(F filter) {

        TypedQuery<E> typedQuery = entityManager.createQuery(createQuery(filter), entityClass);

        applyQueryParameters(typedQuery, filter);

        return typedQuery.getResultList();
    }

    protected abstract String createQuery(F filter);

    protected void applyQueryParameters(TypedQuery<E> typedQuery, F filter) {

        if (filter.getFirstResult() != null) {
            typedQuery.setFirstResult(filter.getFirstResult());
        }

        if (filter.getMaxResults() != null) {
            typedQuery.setMaxResults(filter.getMaxResults());
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}