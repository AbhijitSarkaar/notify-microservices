package com.microservices.order_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {
    @GetMapping("/order")
    public ResponseEntity<String> getOrders(HttpServletRequest httpServletRequest) {

        System.out.println(httpServletRequest.getHeader("X-USER-ID"));
        System.out.println(httpServletRequest.getHeader("X-USER-USERNAME"));
        System.out.println(httpServletRequest.getHeader("X-USER-EMAIL"));
        System.out.println(httpServletRequest.getHeader("X-USER-ROLE"));

        return new ResponseEntity<>("orders", HttpStatus.OK);
    }
}
