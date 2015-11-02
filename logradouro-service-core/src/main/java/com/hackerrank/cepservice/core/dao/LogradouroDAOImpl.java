package com.hackerrank.cepservice.core.dao;

import static com.hackerrank.cepservice.core.dao.DAOHelper.appendWhereIfNeeded;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.model.filter.LogradouroFilter;

@Component
public class LogradouroDAOImpl extends AbstractDAO<Logradouro, Long, LogradouroFilter> implements LogradouroDAO {

    private static final String SELECT = "SELECT l FROM Logradouro l";

    public LogradouroDAOImpl() {
        super(Logradouro.class, LogradouroFilter.class);
    }

    @Override
    protected String createQuery(LogradouroFilter logradouroFilter) {

        StringBuilder sql = new StringBuilder(SELECT);

        List<String> wheres = Lists.newArrayList();

        if (StringUtils.isNotBlank(logradouroFilter.getCepEquals())) {
            wheres.add("l.cep = :" + LogradouroFilter.CEP_EQUALS);
        }

        appendWhereIfNeeded(sql, wheres);

        return sql.toString();
    }

    @Override
    protected void applyQueryParameters(TypedQuery<Logradouro> typedQuery, LogradouroFilter logradouroFilter) {

        super.applyQueryParameters(typedQuery, logradouroFilter);

        if (StringUtils.isNotBlank(logradouroFilter.getCepEquals())) {
            typedQuery.setParameter(LogradouroFilter.CEP_EQUALS, logradouroFilter.getCepEquals());
        }
    }
}
