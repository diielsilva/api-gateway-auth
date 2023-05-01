package com.diel.dev.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JWTUtil {
    private static final String SECRET_KEY = "12345Ab";

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token).getSubject();
    }

    public String getRolesFromToken(String token) {
        Map<String, Claim> claims = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token).getClaims();
        Claim roles = claims.get("roles");
        String[] array = roles.asArray(String.class);
        return array[0];
    }
}
