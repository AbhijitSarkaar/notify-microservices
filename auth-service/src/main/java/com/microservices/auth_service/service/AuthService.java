package com.microservices.auth_service.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> verify(HttpServletRequest httpServletRequest);
}

