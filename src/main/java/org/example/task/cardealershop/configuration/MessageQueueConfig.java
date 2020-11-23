package org.example.task.cardealershop.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfig {

    @Bean
    Queue queue() {
        return new Queue("MyQueue", false);
    }
}
