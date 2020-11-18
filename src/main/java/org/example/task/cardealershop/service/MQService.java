package org.example.task.cardealershop.service;

import org.example.task.cardealershop.entity.Part;

public interface MQService {
    boolean sendMessageToMQ(String message);

    String receiveMessage();

    String sendPartToMQ(Part part);

    Part getPartFromMQ();
}
