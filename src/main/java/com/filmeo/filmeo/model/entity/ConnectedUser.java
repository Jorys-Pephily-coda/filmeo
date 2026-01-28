package com.filmeo.filmeo.model.entity;

import java.util.Collection;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// 💬 ⚠ ce n'est pas une entité
public class ConnectedUser implements UserDetails {

    private static final Logger logger = LoggerFactory.getLogger(
        ConnectedUser.class
    );
    private final User user;

    public ConnectedUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // IMPORTANT: Spring Security expects authorities to be prefixed with "ROLE_"
        // If database stores roles as "ADMIN", we need to convert to "ROLE_ADMIN"
        // This is required for:
        // 1. @PreAuthorize/@PostAuthorize annotations with hasRole()
        // 2. SecurityConfig.hasRole() method calls (which auto-prefix "ROLE_")
        // 3. Thymeleaf sec:authorize="hasRole('ROLE_ADMIN')" directives
        var authorities = user
            .getRoles()
            .stream()
            .map(role -> {
                String roleWithPrefix = role.startsWith("ROLE_")
                    ? role
                    : "ROLE_" + role;
                return new SimpleGrantedAuthority(roleWithPrefix);
            })
            .collect(Collectors.toList());

        logger.debug(
            "User '{}' loaded with authorities: {}",
            user.getUsername(),
            authorities
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // ? intérêt
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true /* user.isEnabled() */;
    }
}
