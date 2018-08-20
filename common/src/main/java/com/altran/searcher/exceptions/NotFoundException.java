package com.altran.searcher.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Clase que representa los datos de una excepcion cuando no existen datos
 */
public class NotFoundException extends CustomServerException {

    private static final String MESSAGE = "Packages records not found";

    public NotFoundException(Object... params) {
        super(MESSAGE, params);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
