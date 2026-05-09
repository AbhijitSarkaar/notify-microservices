package com.microservice.users_service.service;

import com.microservice.users_service.exception.response.CustomResponse;
import com.microservice.users_service.model.User;
import com.microservice.users_service.payload.LogInRequestDTO;
import com.microservice.users_service.payload.UserDTO;
import com.microservice.users_service.payload.UserRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

public interface UserService {
    CustomResponse register(UserRequestDTO userRequestDto);

    ResponseEntity<?> login(@Valid LogInRequestDTO logInRequestDto);

    ResponseCookie logout();

    UserDTO getUserDetails(HttpServletRequest httpServletRequest);
}

