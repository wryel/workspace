package com.hackerrank.cepservice.core.dao;

import com.hackerrank.cepservice.core.ApplicationRuntimeException;

public class DAOException extends ApplicationRuntimeException {

    private static final long serialVersionUID = 3925597961741265772L;

    public DAOException() {
        super();
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}