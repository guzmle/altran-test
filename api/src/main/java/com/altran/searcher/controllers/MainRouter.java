package com.altran.searcher.controllers;

import com.altran.searcher.utilities.FilterParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Locale;
import java.util.Optional;

/**
 * Created by guzmle on 17/8/18.
 */
@Configuration
public class MainRouter {

    @Value("${config.defaultLang}")
    private String defaultLang;

    @Bean
    public RouterFunction<ServerResponse> route(MainHandler mainHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), mainHandler::hello)
                .andRoute(RequestPredicates.GET("/package").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), request -> {
                    FilterParams params = new FilterParams();
                    params.setLang(defaultLang);
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
                    return mainHandler.findAll(params);
                });
    }
}
