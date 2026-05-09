package com.microservice.users_service.controller;

import com.microservice.users_service.exception.response.CustomResponse;
import com.microservice.users_service.model.User;
import com.microservice.users_service.payload.LogInRequestDTO;
import com.microservice.users_service.payload.UserDTO;
import com.microservice.users_service.payload.UserRequestDTO;
import com.microservice.users_service.service.UserService;
import com.microservice.users_service.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<CustomResponse> register(@Valid @RequestBody UserRequestDTO userRequestDto) {
        return new ResponseEntity<>(
                userService.register(userRequestDto),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LogInRequestDTO logInRequestDto) {
        return userService.login(logInRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<CustomResponse> logout() {
        ResponseCookie cookie = userService.logout();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new CustomResponse("Logged out successfully"));
    }

    @GetMapping("/verify")
    public ResponseEntity<UserDTO> verify(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                userService.getUserDetails(httpServletRequest),
                HttpStatus.OK
        );
    }
}
