package org.example.task.cardealershop.controller.advice;

import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EntityNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EntityByIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String carNotFoundHandler(EntityByIdNotFoundException ex) {
        return ex.getMessage();
    }
}
