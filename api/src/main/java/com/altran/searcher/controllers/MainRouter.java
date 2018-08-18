package com.altran.searcher.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by guzmle on 17/8/18.
 */
@Configuration
public class MainRouter {

    @Bean
    public RouterFunction<ServerResponse> route(MainHandler mainHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), mainHandler::hello)
                .andRoute(RequestPredicates.GET("/package").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), mainHandler::findAll);
    }
}
