package com.hackerrank.cepservice.core.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;

import com.hackerrank.cepservice.model.filter.AbstractFilter;

/**
 * Trechos de código que podem se repetir em mais de um lugar
 */
class DAOHelper {

    private static final String WHERE = " WHERE ";

    private static final String AND = " AND ";

    private static final Mirror MIRROR_INSTANCE = new Mirror();

    private DAOHelper() {

    }
    
    public static void appendWhereIfNeeded(StringBuilder sql, List<String> wheres) {
        if (!wheres.isEmpty()) {
            sql.append(WHERE);
            sql.append(StringUtils.join(wheres, AND));
        }
    }

    /**
     * Busca em um objeto pela anotação {@link Id} ou {@link EmbeddedId} e retorna o valor do campo
     * @param object entidade para ser descoberta o id
     * @return
     */
    public static Object getId(Object object) {
        List<Field> fields = MIRROR_INSTANCE.on(object.getClass()).reflectAll().fields().matching(new GetIdMatcher());
        return fields.isEmpty() ? null : MIRROR_INSTANCE.on(object).get().field(fields.iterator().next());
    }

    public static void safeCopyProperties(Object destination, Object from) {
        try {
            BeanUtilsBean.getInstance().copyProperties(destination, from);
        } catch (IllegalAccessException illegalAccessException) {
            throw new DAOException(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            throw new DAOException(invocationTargetException);
        }
    }

    public static <T extends AbstractFilter> T newFilterInstance(Class<T> filterClass) {
        try {
            return filterClass.newInstance();
        } catch (InstantiationException instantiationException) {
            throw new DAOException(instantiationException);
        } catch (IllegalAccessException illegalAccessException) {
            throw new DAOException(illegalAccessException);
        }
    }

    private static class GetIdMatcher implements Matcher<Field> {

        @Override
        public boolean accepts(Field field) {
            return field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class);
        }
    }
}
