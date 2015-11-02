package com.hackerrank.cepservice.web.view;

import java.io.Serializable;

import com.hackerrank.cepservice.model.Logradouro;

public class LogradouroEntradaView implements Serializable {

    private static final long serialVersionUID = -2336277461736870826L;

    private Logradouro logradouro;

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }
}
