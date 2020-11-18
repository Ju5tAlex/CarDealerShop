package org.example.task.cardealershop.controller.advice;

import org.example.task.cardealershop.exception.DuplicatedListEntityException;
import org.example.task.cardealershop.exception.NoMessagesInQueueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadRequestHttpStatusAdvice {
    private Logger logger = LoggerFactory.getLogger(BadRequestHttpStatusAdvice.class);

    @ResponseBody
    @ExceptionHandler(value = {DuplicatedListEntityException.class, NoMessagesInQueueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String BadRequestHandler(RuntimeException ex) {
        logger.error(String.format("Exception %s occurred with message \"%s\"", ex.getClass().getName(), ex.getMessage()));
        return ex.getMessage();
    }
}
