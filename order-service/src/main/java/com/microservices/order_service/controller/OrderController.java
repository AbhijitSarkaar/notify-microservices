package com.microservices.order_service.controller;

import com.microservices.order_service.payload.OrderDTO;
import com.microservices.order_service.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(orderService.createOrder(httpServletRequest), HttpStatus.OK);
    }
}
