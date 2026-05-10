package com.microservices.auth_service.service.impl;

import com.microservices.auth_service.client.UserClient;
import com.microservices.auth_service.external.UserDTO;
import com.microservices.auth_service.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserClient userClient;

    @Override
    public ResponseEntity<?> verify(HttpServletRequest httpServletRequest) {

        try {
            String cookie = httpServletRequest.getHeader("Cookie");
            if(cookie == null) throw new RuntimeException("Cookie is null");

            UserDTO userDto = userClient.verify(cookie);

//            ResponseCookie userIdCookie = ResponseCookie.from("X-USER-ID", userDto.getUserId().toString())
//                    .path("/api")
//                    .maxAge(12 * 60 * 60)
//                    .httpOnly(false)
//                    .build();
//
//            ResponseCookie usernameCookie = ResponseCookie.from("X-USER-USERNAME", userDto.getUsername())
//                    .path("/api")
//                    .maxAge(12 * 60 * 60)
//                    .httpOnly(false)
//                    .build();
//
//            ResponseCookie emailCookie = ResponseCookie.from("X-USER-EMAIL", userDto.getEmail())
//                    .path("/api")
//                    .maxAge(12 * 60 * 60)
//                    .httpOnly(false)
//                    .build();

            String roles = userDto
                    .getRoles()
                    .stream()
                    .reduce(
                            "",
                            (prefix, role) -> prefix.isEmpty() ? role : (prefix + ":" + role)
                    );

//            ResponseCookie rolesCookie = ResponseCookie.from("X-USER-ROLE", roles)
//                    .path("/api")
//                    .maxAge(12 * 60 * 60)
//                    .httpOnly(false)
//                    .build();
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.SET_COOKIE, userIdCookie.toString())
//                    .header(HttpHeaders.SET_COOKIE, usernameCookie.toString())
//                    .header(HttpHeaders.SET_COOKIE, emailCookie.toString())
//                    .header(HttpHeaders.SET_COOKIE, roles.toString())
//                    .body(null);

            return ResponseEntity.ok()
                    .header("X-USER-ID", userDto.getUserId().toString())
                    .header("X-USER-USERNAME", userDto.getUsername())
                    .header("X-USER-EMAIL", userDto.getEmail())
                    .header("X-USER-ROLE", roles)
                    .body(null);

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
