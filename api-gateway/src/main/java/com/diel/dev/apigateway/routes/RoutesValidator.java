package com.diel.dev.apigateway.routes;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

public abstract class RoutesValidator {
    public static final List<String> FREE_ENDPOINTS = List.of("/users/signup", "/users/auth");
    public static final List<String> ADMIN_ENDPOINTS = List.of("/tasks/secured");

    public static final Predicate<ServerHttpRequest> IS_SECURED = request -> FREE_ENDPOINTS.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public static final Predicate<ServerHttpRequest> IS_ADMIN = request -> ADMIN_ENDPOINTS.stream()
            .anyMatch(uri -> request.getURI().getPath().contains(uri));
}
