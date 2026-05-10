package com.microservices.order_service.messaging;

import com.microservices.order_service.payload.MessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(MessageDTO messageDto) {
        rabbitTemplate.convertAndSend("notificationQueue", messageDto);
    }
}
