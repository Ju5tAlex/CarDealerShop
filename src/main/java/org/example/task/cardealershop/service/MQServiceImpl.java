package org.example.task.cardealershop.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
public class MQServiceImpl implements MQService{

    private Logger logger = LoggerFactory.getLogger(PartServiceImpl.class);
    private final static String QUEUE_NAME = "PartQueue";
    private ConnectionFactory factory;

    public MQServiceImpl() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    @Override
    public boolean sendMessageToMQ(String message) {
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
        logger.info("Message sent ot MQ: " + message);
        return true;
    }

    @Override
    public String receiveMessage() {
        String message = null;
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            GetResponse response = channel.basicGet(QUEUE_NAME, false);
            if (response == null) {
                message = "No messages in queue to receive";
            } else {
                message = new String(response.getBody(), StandardCharsets.UTF_8);
                long deliveryTag = response.getEnvelope().getDeliveryTag();
                channel.basicAck(deliveryTag, false);
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
        logger.info("Message from MQ received: " + message);
        return message;
    }
}
