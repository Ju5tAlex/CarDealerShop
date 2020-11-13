package org.example.task.cardealershop.controller.advice;

import org.example.task.cardealershop.exception.DuplicatedListEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DuplicatedListEntityAdvice {
    @ResponseBody
    @ExceptionHandler(DuplicatedListEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String DuplicatedEntityHandler(DuplicatedListEntityException ex) {
        return ex.getMessage();
    }
}
