package com.microservices.notification_service.messaging;

import com.microservices.notification_service.payload.MessageDTO;
import com.microservices.notification_service.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    NotificationService notificationService;

    @RabbitListener(queues = "notificationQueue")
    public void consumeMessage(MessageDTO messageDto) {
        notificationService.send(messageDto);
    }

}
