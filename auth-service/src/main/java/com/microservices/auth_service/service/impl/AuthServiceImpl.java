package com.microservices.auth_service.service.impl;

import com.microservices.auth_service.client.UserClient;
import com.microservices.auth_service.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserClient userClient;

    @Override
    public ResponseEntity<?> verify(HttpServletRequest httpServletRequest) {
        return userClient.verify(httpServletRequest.getHeader("Cookie"));
    }

}
