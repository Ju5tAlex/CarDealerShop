package org.example.task.cardealershop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.exception.NoMessagesInQueueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class MQServiceImpl implements MQService{

    private Logger logger = LoggerFactory.getLogger(PartServiceImpl.class);
    private final static String QUEUE_NAME = "MyQueue";
    private AmqpTemplate amqpTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public MQServiceImpl(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
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

    @Override
    public String sendPartToMQ(Part part) {
        String message;
        try {
            amqpTemplate.convertAndSend(QUEUE_NAME, objectMapper.writeValueAsBytes(part));
            message = String.format("Part with id=%d sent to MQ", part.getId());
            logger.info(message);
        } catch (JsonProcessingException e) {
            logger.error(String.format("Exception %s occurred with message \"%s\"", e.getClass().getName(), e.getMessage()));
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public Part getPartFromMQ() {
        Part part;
        try {
            Message message = amqpTemplate.receive(QUEUE_NAME);
            if (message == null) throw new NoMessagesInQueueException(QUEUE_NAME);
            part = objectMapper.readValue(message.getBody(), Part.class);
            String log = String.format("Part with id=%d received from MQ", part.getId());
            logger.info(log);
        } catch (IOException e) {
            logger.error(String.format("Exception %s occurred with message \"%s\"", e.getClass().getName(), e.getMessage()));
            throw new RuntimeException(e);
        }
        return part;
    }

    @Override
    public <T> String sendEntityToMQ(T entity) {
        String message;
        try {
            amqpTemplate.convertAndSend(QUEUE_NAME, objectMapper.writeValueAsBytes(entity));
            message = String.format("%s sent to MQ", entity.getClass().getSimpleName());
            logger.info(message);
        } catch (JsonProcessingException e) {
            logger.error(String.format("Exception %s occurred with message \"%s\"", e.getClass().getName(), e.getMessage()));
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public <T> T getEntityFromMQ(Class<T> entityClass) {
        T entity;
        try {
            Message message = amqpTemplate.receive(QUEUE_NAME);
            if (message == null) throw new NoMessagesInQueueException(QUEUE_NAME);
            entity = objectMapper.readValue(message.getBody(), entityClass);
            String log = String.format("%s received from MQ", entityClass.getSimpleName());
            logger.info(log);
        } catch (IOException e) {
            logger.error(String.format("Exception %s occurred with message \"%s\"", e.getClass().getName(), e.getMessage()));
            throw new RuntimeException(e);
        }
        return entity;
    }
}
