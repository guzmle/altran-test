package com.altran.searcher.exceptions;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class InternaErrorExceptionTest {


    @Test
    public void testCreation() {
        InternalErrorException exception = new InternalErrorException("Message", new Exception(), "Parametro");
        assertNotNull(exception);
    }

    @Test
    public void testStatus() {
        InternalErrorException exception = new InternalErrorException("Message", new Exception(), "Parametro");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }
}
