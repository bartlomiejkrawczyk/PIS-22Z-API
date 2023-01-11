package com.example.api.auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Roles {
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    USER("USER");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
