package org.example.task.cardealershop.exception;

public class EntityByIdNotFoundException extends RuntimeException{
    public EntityByIdNotFoundException(String entityName, int id) {
        super(entityName + " with id " + id + " not found");
    }
}
