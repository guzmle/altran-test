package com.altran.searcher.controllers;

import com.altran.searcher.utilities.AuditControl;
import com.altran.searcher.utilities.FilterParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Locale;
import java.util.Optional;

/**
 * Clase que permite la configuracion de los endpoint del API
 */
@Configuration
public class MainRouter {

    @Value("${config.maxItemsCache}")
    private String maxItemsCache;

    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    @Value("${config.defaultLang}")
    private String defaultLang;

    /**
     * Metodo que configura los endpoints del sistema
     *
     * @param mainHandler clase que se encarga de operar la peticion
     * @return Objeto para la gestion de la invocacion
     */
    @Bean
    public RouterFunction<ServerResponse> route(MainHandler mainHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), mainHandler::hello)
                .andRoute(RequestPredicates.GET("/package").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        request -> mainHandler.findAll(formatFilters(request), formatAudit(request)));
    }

    /**
     * Metodo que toma el objeto de la peticion del api y obtiene la informacion
     * requerida para el filtrar la lista
     *
     * @param request datos con la informacion de la peticion
     * @return objeto con la informacion para filtrar la lista
     */
    private FilterParams formatFilters(ServerRequest request) {

        FilterParams params = new FilterParams();
        params.setLang(defaultLang);
        params.setMaxItemsCache(Integer.parseInt(maxItemsCache));

        Optional<String> limit = request.queryParam(LIMIT);
        limit.ifPresent(s -> params.setLimit(Integer.parseInt(s)));

        Optional<String> offset = request.queryParam(OFFSET);
        offset.ifPresent(s -> params.setOffset(Integer.parseInt(s)));

        if (!request.headers().acceptLanguage().isEmpty()) {
            Optional<String> languageHeader = request.headers().acceptLanguage().stream()
                    .filter(line -> !line.getRange().contains("-"))
                    .map(Locale.LanguageRange::getRange)
                    .findFirst();

            languageHeader.ifPresent(params::setLang);
        }

        return params;
    }

    /**
     * Metodo que arma el objeto necesario para la auditoria de la peticion
     *
     * @param request datos con la informacion de la peticion
     * @return objeto con la informacion de la peticion
     */
    private AuditControl formatAudit(ServerRequest request) {
        AuditControl auditControl = new AuditControl();
        try {
            Method exchangeMethod = request.getClass().getDeclaredMethod("exchange");
            exchangeMethod.setAccessible(true);
            ServerWebExchange exchange = (ServerWebExchange) exchangeMethod.invoke(request);
            exchangeMethod.setAccessible(false);
            InetSocketAddress addr = exchange.getRequest().getRemoteAddress();
            auditControl.setIp(addr != null ? addr.getHostString() : "");
            auditControl.setUserAgent(request.headers().asHttpHeaders().getFirst("User-Agent"));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            auditControl = null;
        }
        return auditControl;
    }
}
