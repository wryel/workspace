package com.hackerrank.cepservice.core;

public class ApplicationRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 7950231738139693564L;

    public ApplicationRuntimeException() {
        super();
    }

    public ApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationRuntimeException(String message) {
        super(message);
    }

    public ApplicationRuntimeException(Throwable cause) {
        super(cause);
    }
}