package org.example.task.cardealershop.service;

import org.example.task.cardealershop.exception.NoMessagesInQueueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MQServiceImpl implements MQService{

    private Logger logger = LoggerFactory.getLogger(PartServiceImpl.class);
    private final static String QUEUE_NAME = "MyQueue";
    private AmqpTemplate amqpTemplate;

    @Autowired
    public MQServiceImpl(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public boolean sendMessageToMQ(String message) {
        amqpTemplate.convertAndSend(QUEUE_NAME, message);
        logger.info("Message sent to MQ: " + message);
        return true;
    }

    @Override
    public String receiveMessage() {
        Message message = amqpTemplate.receive(QUEUE_NAME);
        if (message == null) throw new NoMessagesInQueueException(QUEUE_NAME);
        String messageFromQueue = new String(message.getBody(), StandardCharsets.UTF_8);
        logger.info("Message from MQ received: " + messageFromQueue);
        return messageFromQueue;
    }
}
