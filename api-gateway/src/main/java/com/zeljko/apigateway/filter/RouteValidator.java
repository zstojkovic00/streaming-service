package com.zeljko.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

        public static final List<String> openApiEndpoints = List.of(
                "/auth",
                "/eureka",
                "/api/v1/video"
        );

        public Predicate<ServerHttpRequest> isSecured =
                request -> openApiEndpoints
                        .stream()
                        .noneMatch(uri -> request.getURI().getPath().startsWith(uri));


}
