package org.example.task.cardealershop.exception;

public class DuplicatedListEntityException extends RuntimeException{
    public DuplicatedListEntityException(String entityName, int id) {
        super(entityName + " with id " + id + " already exists in list");
    }
}
