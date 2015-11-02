package com.hackerrank.cepservice.web.view;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.hackerrank.cepservice.model.Logradouro;

public class LogradouroListagemView implements Serializable {

    private static final long serialVersionUID = 6873673781267280470L;

    private List<Logradouro> logradouros;

    public List<Logradouro> getLogradouros() {
        if (logradouros == null) {
            logradouros = Lists.newArrayList();
        }
        return logradouros;
    }

    public void setLogradouros(List<Logradouro> logradouros) {
        this.logradouros = logradouros;
    }
}
