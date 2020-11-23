package org.example.task.cardealershop.service;

public interface MQService {
    boolean sendMessageToMQ(String message);

    String receiveMessage();

    <T> String sendEntityToMQ(T entity);

    <T> T getEntityFromMQ(Class<T> entityClass);
}
