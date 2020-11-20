package org.example.task.cardealershop.exception;

public class AlreadyHasEntityException extends RuntimeException{
    public AlreadyHasEntityException(String entityName, int id, int carId) {
        super(String.format("Car with id %d already has %s with id %d", carId, entityName, id));
    }
}
