package ru.yandex.practicum.filmorate.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.common.collect.Sets;

import java.util.stream.Collectors;
import java.util.Set;

import static ru.yandex.practicum.filmorate.security.UserPermission.*;

/**
 * @author Oleg Khilko
 */


public enum UserRole {
    STUDENT(Sets.newHashSet()), ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_READ, COURSE_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
