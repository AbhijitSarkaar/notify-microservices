package com.microservice.users_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwtCookie}")
    private String jwtCookie;

    @Value("${jwtExpirationMs}")
    private Integer jwtExpirationMs;

    @Value("${jwtSecret}")
    private String jwtSecret;

    Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64URL.decode(jwtSecret)
        );
    }

    public String generateJwtFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date( new Date().getTime() + jwtExpirationMs ))
                .signWith(key())
                .compact();
    }

    public ResponseCookie generateCookieFromJwt(String username) {
        return ResponseCookie.from(jwtCookie, generateJwtFromUsername(username))
                .maxAge(24 * 60 * 60)
                .httpOnly(false)
                .build();
    }

    public ResponseCookie cleanCookie() {
        return ResponseCookie.from(jwtCookie, null)
                .build();
    }
}
