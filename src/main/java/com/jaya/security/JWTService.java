package com.jaya.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    private static final String JWT_KEY = "jslkdfjlasdfp80980s8d0s0jjsdlfjsdf99090df";
    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String createJwt(Long userId){
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                //.withExpiresAt(new Date().)
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString){

        var jwtDecode = JWT.decode(jwtString);
        return Long.valueOf(jwtDecode.getSubject());
    }

}
