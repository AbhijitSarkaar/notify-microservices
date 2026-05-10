package com.microservices.notification_service.service.impl;

import com.microservices.notification_service.payload.MessageDTO;
import com.microservices.notification_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String username;

    @Override
    public void send(MessageDTO messageDto) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(username);
            mailMessage.setTo(messageDto.getUserEmail());
            mailMessage.setSubject(
                    "Order placed with Order Id #" + messageDto.getOrderId()
            );
            mailMessage.setText(
                    "Hi " + messageDto.getUsername() + ". Order is placed"
            );
            mailSender.send(mailMessage);
        } catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
