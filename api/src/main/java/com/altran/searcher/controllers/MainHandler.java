package com.altran.searcher.controllers;

import com.altran.searcher.business.PackageService;
import com.altran.searcher.business.domain.ResultDTO;
import com.altran.searcher.utilities.FilterParams;
import com.altran.searcher.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Filter;

@Component
public class MainHandler {

    private final PackageService service;

    private final CacheManager cacheManager;

    @Autowired
    public MainHandler(PackageService service, CacheManager cacheManager) {
        this.service = service;
        this.cacheManager = cacheManager;
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("It is Working!"));
    }

    public Mono<ServerResponse> findAll(FilterParams params) {

        Response<ResultDTO> response = new Response<>();
        ResultDTO result = service.getPackages(params);
        response.setResult(result);
        response.setSuccess(true);

        // TODO: Implementar exception
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(BodyInserters.fromObject(response));
    }

    // A cron expression to define every day at midnight
    @Scheduled(fixedDelay = 60000)
    public void cacheEvictionScheduler() {

        Cache cache = cacheManager.getCache("packages");
        if (cache != null) {
            Object nativeCache = cache.getNativeCache();
            Object[] langs = ((ConcurrentHashMap) nativeCache).keySet().toArray();
            cache.clear();

            for (Object lang : langs) {
                FilterParams filter = new FilterParams();
                filter.setLang((String) lang);
                service.getPackages(filter);
            }

            System.out.println("Cache");
        }

    }
}


