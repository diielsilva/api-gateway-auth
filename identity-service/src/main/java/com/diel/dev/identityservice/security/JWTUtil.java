package com.diel.dev.identityservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.diel.dev.identityservice.entities.Authority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {
    private static final String SECRET_KEY = "12345Ab";
    private static final long EXPIRATION_TIME = 60000L;

    public String generateJWT(String username, Authority authority) {
        return JWT.create().withSubject(username).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("roles", List.of(authority.name()))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public String getUserFromJWT(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token).getSubject();
    }
}
