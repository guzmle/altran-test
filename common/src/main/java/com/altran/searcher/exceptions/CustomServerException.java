package com.altran.searcher.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Clase que representa los datos de una excepcion no controlada en el servicio generada
 */
public abstract class CustomServerException extends RuntimeException {

    private final transient Object[] parameters;

    public CustomServerException(String message, Object... parameters) {
        super(message);
        this.parameters = parameters;
    }

    public CustomServerException(String message, Throwable cause, Object... parameters) {
        super(message, cause);
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public abstract HttpStatus getStatus();
}