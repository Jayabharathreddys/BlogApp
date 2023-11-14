package com.jaya.security;

import com.jaya.users.UsersService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationManager implements AuthenticationManager {
    private final JWTService jwtService;
    private final UsersService usersService;

    public JWTAuthenticationManager(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof JWTAuthentication jwtAuthentication){
            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId(jwt);

            jwtAuthentication.userEntity = usersService.getUser(userId);
            jwtAuthentication.setAuthenticated(true);

            return jwtAuthentication;
        }
        throw new IllegalAccessError("Cannot authenticate non-JWT authentication.");
    }
}
