package com.jaya.security;

import com.jaya.users.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class JWTAuthentication implements Authentication {
    String jwt;
   UserEntity userEntity;

    public JWTAuthentication(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getCredentials() {
        return jwt;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public UserEntity getPrincipal() {
        return userEntity;
    }

    @Override
    public boolean isAuthenticated() {
        return (userEntity != null);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }
    @Override
    public String getName() {
        return null;
    }

    public boolean implies(Subject subject){
        return Authentication.super.implies(subject);
    }

    @Override
    public String toString() {
        return "JWTAuthentication{" +
                "jwt='" + jwt + '\'' +
                ", userEntity=" + userEntity +
                '}';
    }
}
