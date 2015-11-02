package com.hackerrank.cepservice.model.filter;

import java.io.Serializable;

/**
 * Filtro base para todas as outras classes de filtro
 */
public class AbstractFilter implements Serializable {

    private static final long serialVersionUID = -7140515458542473799L;

    private Integer firstResult;

    private Integer maxResults;

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
}