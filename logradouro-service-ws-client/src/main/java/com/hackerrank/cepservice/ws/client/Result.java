package com.hackerrank.cepservice.ws.client;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Wrapper genérico para retorno de métodos REST
 */
public class Result<R extends Serializable> implements Serializable {

    private static final long serialVersionUID = 8126501703489126863L;

    private R object;

    private Status status;

    private Date date;

    private String errorMessage;

    public R getObject() {
        return object;
    }

    public void setObject(R object) {
        this.object = object;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public enum Status {

        OK, FAIL

    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
