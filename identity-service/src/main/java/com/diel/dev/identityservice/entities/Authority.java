package com.diel.dev.identityservice.entities;

import com.diel.dev.identityservice.exceptions.BusinessException;

public enum Authority {
    ADMIN,
    USER;

    public static Authority toEnum(String authority) {
        return switch (authority) {
            case "ADMIN" -> ADMIN;
            case "USER" -> USER;
            default -> throw new BusinessException("Invalid authority!");
        };
    }
}
