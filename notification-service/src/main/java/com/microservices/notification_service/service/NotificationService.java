package com.microservices.notification_service.service;

import com.microservices.notification_service.payload.MessageDTO;

public interface NotificationService {
    void send(MessageDTO messageDto);
}
