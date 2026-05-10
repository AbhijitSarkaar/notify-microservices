package com.microservices.order_service.payload;

import lombok.Data;

@Data
public class MessageDTO {
    String username;
    String userEmail;
    String orderId;
}
