package com.jaya.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTServieTests {

    JWTService jwtService = new JWTService();
    @Test
    void canCreateJwtFromUserID(){
        var jwt = jwtService.createJwt(1001L);

        Assertions.assertNotNull(jwt);
    }
}
