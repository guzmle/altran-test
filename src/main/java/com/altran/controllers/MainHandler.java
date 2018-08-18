package com.altran.controllers;

import com.altran.services.domain.ResultDTO;
import com.altran.utilities.FilterParams;
import com.altran.utilities.Response;
import com.altran.services.PackageService;
import com.altran.services.domain.PackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class MainHandler {

    @Autowired
    private PackageService service;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("It is Working!"));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        FilterParams params = new FilterParams();
        if (request.queryParam("limit").isPresent()) {
            params.setLimit(Integer.parseInt(request.queryParam("limit").get()));
        }

        if (request.queryParam("offset").isPresent()) {
            params.setOffset(Integer.parseInt(request.queryParam("offset").get()));
        }

        if (!request.headers().acceptLanguage().isEmpty()) {
            Optional<String> languageHeader = request.headers().acceptLanguage().stream()
                    .filter(line -> !line.getRange().contains("-"))
                    .map(Locale.LanguageRange::getRange)
                    .findFirst();
            languageHeader.ifPresent(params::setLang);
        }
        Response<ResultDTO> response = new Response<>();
        ResultDTO result = service.getPackages(params);
        response.setResult(result);
        response.setSuccess(true);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(BodyInserters.fromObject(response));
    }

    // A cron expression to define every day at midnight
    @Scheduled(fixedDelay = 60000)
    public void cacheEvictionScheduler() {
        service.updatePackageCached();
    }
}


