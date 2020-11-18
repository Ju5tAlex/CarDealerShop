package org.example.task.cardealershop.service;

public interface MQService {
    boolean sendMessageToMQ(String message);

    String receiveMessage();
}
