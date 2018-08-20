package com.altran.searcher.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Clase que representa los datos de una excepcion cuando no existen datos
 */
public class InternalErrorException extends CustomServerException {

    public InternalErrorException(String message, Throwable cause, Object... parameters) {
        super(message, cause, parameters);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
