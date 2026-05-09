package com.microservice.users_service.service.impl;

import com.microservice.users_service.enums.AppRole;
import com.microservice.users_service.exception.response.CustomResponse;
import com.microservice.users_service.model.Role;
import com.microservice.users_service.model.User;
import com.microservice.users_service.payload.LogInRequestDTO;
import com.microservice.users_service.payload.UserRequestDTO;
import com.microservice.users_service.repository.RoleRepository;
import com.microservice.users_service.repository.UserRepository;
import com.microservice.users_service.security.service.UserDetailsImpl;
import com.microservice.users_service.service.UserService;
import com.microservice.users_service.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public CustomResponse register(UserRequestDTO userRequestDto) {

        User user = new User(
                userRequestDto.getUsername(),
                userRequestDto.getEmail(),
                passwordEncoder.encode(userRequestDto.getPassword())
        );

        List<String> roles = userRequestDto.getRoles();

        if(roles == null) {
            Role userRole = roleRepository.findRoleByRoleName(AppRole.ROLE_USER);
            user.setRole(userRole);
        } else {
            roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        user.setRole(roleRepository.findRoleByRoleName(AppRole.ROLE_ADMIN));
                        break;
                    case "user":
                        user.setRole(roleRepository.findRoleByRoleName(AppRole.ROLE_USER));
                        break;
                }
            });
        }

        userRepository.save(user);

        return new CustomResponse("Registration successful");
    }

    @Override
    public ResponseEntity<?> login(LogInRequestDTO logInRequestDto) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInRequestDto.getUsername(),
                            logInRequestDto.getPassword()
                    )
            );
        } catch(AuthenticationException e) {
            return new ResponseEntity<>(new CustomResponse("Bad credentials"), HttpStatus.BAD_REQUEST);
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie cookie = jwtUtils.generateCookieFromJwt(userDetails.getUsername());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(
                        new CustomResponse("Logged in successfully")
                );
    }

    @Override
    public ResponseCookie logout() {
        return jwtUtils.cleanCookie();
    }

}
