package com.diel.dev.apigateway.filter;

import com.diel.dev.apigateway.routes.RoutesValidator;
import com.diel.dev.apigateway.util.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JWTAuthFilter implements GlobalFilter {
    private final JWTUtil jwtUtil;

    public JWTAuthFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (RoutesValidator.IS_SECURED.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Missing request params");
            }
            String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            try {
                String claims = jwtUtil.getRolesFromToken(token);

                if (RoutesValidator.IS_ADMIN.test(exchange.getRequest()) && !claims.equals("ADMIN")) {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid permission");
                }
            } catch (Exception exception) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(403), "You don't have permission to access this resource or your token is invalid or expired");
            }
        }
        return chain.filter(exchange);
    }
}
