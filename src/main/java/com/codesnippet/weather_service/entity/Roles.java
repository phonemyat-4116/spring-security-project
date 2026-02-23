package com.codesnippet.weather_service.entity;

import java.util.Set;

public enum Roles {
    ADMIN(Set.of(Permission.WEATHER_READ, Permission.WEATHER_WRITE, Permission.WEATHER_DELETE)),
    USER(Set.of(Permission.WEATHER_READ)),;

    private final Set<Permission> permissions;

    Roles(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
