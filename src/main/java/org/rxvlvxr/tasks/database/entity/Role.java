package org.rxvlvxr.tasks.database.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.function.Predicate;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }

    public static boolean hasAuthority(Role role) {
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        return authorities.stream()
                .anyMatch(Predicate.isEqual(role));
    }
}
