package com.health.platform.dto;

public class AuthResponse {

    private String token;
    private Long userId;
    private String name;

    public AuthResponse(String token, Long userId, String name) {
        this.token = token;
        this.userId = userId;
        this.name = name;
    }

    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
}
