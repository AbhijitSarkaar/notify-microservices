package com.microservices.order_service.service.impl;

import com.microservices.order_service.model.Order;
import com.microservices.order_service.payload.OrderDTO;
import com.microservices.order_service.repository.OrderRepository;
import com.microservices.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDTO createOrder(HttpServletRequest httpServletRequest) {

        String userId = httpServletRequest.getHeader("X-USER-ID");
        String userEmail = httpServletRequest.getHeader("X-USER-EMAIL");

//        System.out.println(httpServletRequest.getHeader("X-USER-USERNAME"));
//        System.out.println(httpServletRequest.getHeader("X-USER-ROLE"));

        Order order = new Order();
        order.setUserId(Long.valueOf(userId));

        order = orderRepository.save(order);

        OrderDTO orderDto = new OrderDTO();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setUserId(order.getUserId());

        return orderDto;
    }
}
