package org.example.task.cardealershop.controller.advice;

import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundHttpStatusAdvice {
    private Logger logger = LoggerFactory.getLogger(BadRequestHttpStatusAdvice.class);

    @ResponseBody
    @ExceptionHandler(EntityByIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundHandler(RuntimeException ex) {
        logger.error(String.format("Exception %s occurred with message \"%s\"", ex.getClass().getName(), ex.getMessage()));
        return ex.getMessage();
    }
}
