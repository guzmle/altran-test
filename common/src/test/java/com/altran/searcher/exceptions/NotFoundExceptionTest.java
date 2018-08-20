package com.altran.searcher.exceptions;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class NotFoundExceptionTest {


    @Test
    public void testCreation() {
        NotFoundException exception = new NotFoundException("Parametro 1", "Parametro 2");
        assertNotNull(exception);
    }

    @Test
    public void testStatus() {
        NotFoundException exception = new NotFoundException("Parametro 1", "Parametro 2");
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
