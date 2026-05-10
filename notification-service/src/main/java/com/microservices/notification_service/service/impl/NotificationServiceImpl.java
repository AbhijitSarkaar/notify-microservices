package com.microservices.notification_service.service.impl;

import com.microservices.notification_service.payload.MessageDTO;
import com.microservices.notification_service.service.EmailService;
import com.microservices.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    EmailService emailService;

    @Override
    public void send(MessageDTO messageDto) {
        System.out.println(messageDto.getOrderId());
        System.out.println(messageDto.getUserEmail());
        System.out.println(messageDto.getUsername());

        emailService.send(messageDto);

    }
}
