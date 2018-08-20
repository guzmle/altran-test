package com.altran.searcher.controllers;

import com.altran.searcher.business.PackageService;
import com.altran.searcher.utilities.AuditControl;
import com.altran.searcher.utilities.FilterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Clase que permite controlar las acciones de los eventos http del api
 */
@Component
public class MainHandler {

    private final PackageService service;

    private final CacheManager cacheManager;

    @Autowired
    public MainHandler(final PackageService service, final CacheManager cacheManager) {
        this.service = service;
        this.cacheManager = cacheManager;
    }

    /**
     * Metodo que verifica que el servicio se encuentre activo
     * @param request datos de la peticion
     * @return Mensaje que se encuentra activo
     */
    @SuppressWarnings("unused")
    Mono<ServerResponse> hello(final ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("It is Working!"));
    }

    /**
     * Metodo que consulta la lista de paquetes segun los parametros
     * @param params parametros de consulta
     * @param auditControl objeto para la auditoria
     * @return lista de paquete segun los parametros de consulta
     */
    @SuppressWarnings("unused")
    Mono<ServerResponse> findAll(final FilterParams params, final AuditControl auditControl) {
        return service.getPackages(params)
                .flatMap(result -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(result));
    }

    /**
     * Metodo que se ejecuta en un tiempo determinado con la finalidad de actualizar la informacion
     * alojada en el cache
     */
    @Scheduled(fixedDelay = 60000)
    public void cacheEvictionScheduler() {
        Cache cache = cacheManager.getCache("packages");
        if (cache != null) {
            Object nativeCache = cache.getNativeCache();
            Object[] langs = ((ConcurrentHashMap) nativeCache).keySet().toArray();
            cache.clear();
            Arrays.stream(langs).forEach(lang -> service.getPackages(new FilterParams((String) lang)));
        }

    }
}


