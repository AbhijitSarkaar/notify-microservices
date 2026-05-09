package com.microservice.users_service.exception;

import com.microservice.users_service.exception.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomResponse> customRuntimeExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(
                new CustomResponse(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(item -> {
            map.put(item.getField(), item.getDefaultMessage());
        });

        return new ResponseEntity<>(
                map,
                HttpStatus.BAD_REQUEST
        );
    }

}
