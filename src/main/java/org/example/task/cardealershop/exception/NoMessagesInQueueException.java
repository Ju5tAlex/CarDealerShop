package org.example.task.cardealershop.exception;

public class NoMessagesInQueueException extends RuntimeException{
    public NoMessagesInQueueException(String queueName) {
        super("There is no messages in queue '" + queueName + "' to receive");
    }
}
