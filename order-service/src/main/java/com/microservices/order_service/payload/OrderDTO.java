package com.microservices.order_service.payload;

import lombok.Data;

@Data
public class OrderDTO {
    private Long orderId;
    private Long userId;
}
