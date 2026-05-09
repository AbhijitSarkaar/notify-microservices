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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.util.WebUtils;

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

    @Value("${jwtCookie}")
    String jwtCookie;

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

    @Override
    public ResponseEntity<?> getUserDetails(HttpServletRequest httpServletRequest) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, jwtCookie);
        String token = cookie.getValue();

        System.out.println(token);

        System.out.println(":::getUserDetails:::");

        if (token != null && jwtUtils.validate(token)) {
            String username = jwtUtils.generateUsernameFromJwt(token);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Invalid token"));

            ResponseCookie cookie1 = ResponseCookie.from("X-USER-ID", user.getUserId().toString())
                    .path("/api")
                    .maxAge(12 * 60 * 60)
                    .build();

            ResponseCookie cookie2 = ResponseCookie.from("X-USER-USERNAME", user.getUsername())
                    .path("/api")
                    .maxAge(12 * 60 * 60)
                    .build();

            ResponseCookie cookie3 = ResponseCookie.from("X-USER-EMAIL", user.getEmail())
                    .path("/api")
                    .maxAge(12 * 60 * 60)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie1.toString())
                    .header(HttpHeaders.SET_COOKIE, cookie2.toString())
                    .header(HttpHeaders.SET_COOKIE, cookie3.toString())
                    .body(null);

        }

        return ResponseEntity.badRequest().body(null);
    }

}
