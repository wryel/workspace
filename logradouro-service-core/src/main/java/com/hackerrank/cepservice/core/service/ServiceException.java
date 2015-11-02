package com.hackerrank.cepservice.core.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 8414571246880301035L;

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}