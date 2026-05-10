package com.microservices.order_service.service;

import com.microservices.order_service.payload.OrderDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface OrderService {
    OrderDTO createOrder(HttpServletRequest httpServletRequest);
}

