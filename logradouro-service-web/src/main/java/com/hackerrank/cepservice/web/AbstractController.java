package com.hackerrank.cepservice.web;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.jsf.FacesContextUtils;

import com.hackerrank.cepservice.core.service.Service;

/**
 * Controller base para todos os outros controllers
 */
public abstract class AbstractController implements Serializable {

    private static final long serialVersionUID = -1376379133393526083L;

    protected static final String VIEW_LISTAR = "listar";

    protected static final String VIEW_ENTRADA = "entrada";

    protected static final String ACTION_ADICIONAR = "adicionar";

    protected static final String ACTION_EDITAR = "editar";

    private String view;

    private String action;

    /**
     * Acesso ao contexto do Spring 
     */
    protected <S extends Service<?, ?, ?>> S getService(Class<S> klass) {
        return FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(klass);
    }

    public String noAction() {
        return StringUtils.EMPTY;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}