package ru.yandex.practicum.filmorate.security;

/**
 * @author Oleg Khilko
 */


public enum UserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    FILM_READ("film:read"),
    FILM_WRITE("film:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
