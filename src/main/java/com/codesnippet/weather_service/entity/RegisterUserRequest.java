package com.codesnippet.weather_service.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

// DTO class
public class RegisterUserRequest {

    private String username;
    private String password;
    private Roles role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
