package com.netflop.be.movie.common;

import com.netflop.be.movie.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user != null ? List.of(new SimpleGrantedAuthority(user.getType())) : Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user != null ? user.getType() : null;
    }

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
        return user != null && Objects.equals(user.getStatus(), "Active");
    }

    public String getUserId() {
        return user != null ? user.getId() : null;
    }

    public String getUserType() {
        return user != null ? user.getType() : null;
    }
}
