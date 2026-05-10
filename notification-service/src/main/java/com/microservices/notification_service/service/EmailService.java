package com.microservices.notification_service.service;

import com.microservices.notification_service.payload.MessageDTO;

public interface EmailService {
    public void send(MessageDTO messageDto);
}
