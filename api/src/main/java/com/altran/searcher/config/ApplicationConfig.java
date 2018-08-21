package com.altran.searcher.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * Clase que configura la mayoria de los beans que se requieren en el sistema
 */
@Configuration
@PropertySource("classpath:config.properties")
public class ApplicationConfig {


    private static final String ALLOWED_HEADERS = "Accept-Language, x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String MAX_AGE = "3600";
    public static final String CACHE_NAME = "packages";
    public static final long TIME_REFRESH_CACHE = 60000;

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return builder;
    }


    @Bean
    public CacheManager cacheManager() {
        List<ConcurrentMapCache> caches = Collections.singletonList(new ConcurrentMapCache(CACHE_NAME));
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            ServerHttpResponse response = ctx.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
            headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
            headers.add("Access-Control-Max-Age", MAX_AGE);
            headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
            return chain.filter(ctx);
        };
    }
}
