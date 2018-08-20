package com.altran.searcher.handles;

import com.altran.searcher.exceptions.CustomServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

/**
 * Clase que permite configurar la respuesta de error del sistema
 */
@Component
public class CustomErrorAtributtes extends DefaultErrorAttributes {

    private static final Logger logger = LogManager.getLogger(CustomErrorAtributtes.class);

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        addErrorDetails(errorAttributes, request);
        return errorAttributes;
    }

    /**
     * Metodo que arma el detalle de la excepcion
     * @param errorAttributes atributos actuales de el error
     * @param request datos de la peticion
     */
    private void addErrorDetails(final Map<String, Object> errorAttributes, final ServerRequest request) {
        Throwable ex = getError(request);
        errorAttributes.put("exception", ex.getClass().getSimpleName());
        errorAttributes.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorAttributes.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        if (ex instanceof CustomServerException) {
            errorAttributes.put("error", ((CustomServerException) ex).getStatus().getReasonPhrase());
            errorAttributes.put("status", ((CustomServerException) ex).getStatus().value());
        }

        logger.error("Ha ocurrido un error", ex);
        errorAttributes.put("message", ex.getMessage());
    }
}