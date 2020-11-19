package org.example.task.cardealershop.controller.advice;

import org.example.task.cardealershop.exception.DuplicatedListEntityException;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.example.task.cardealershop.exception.NoMessagesInQueueException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandlerAdvice {
    private Logger logger = LoggerFactory.getLogger(CustomExceptionHandlerAdvice.class);

    @ExceptionHandler(value = {DuplicatedListEntityException.class, NoMessagesInQueueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequestHandler(Exception ex) {
        logger.error(String.format("Exception %s occurred with message \"%s\"", ex.getClass().getName(), ex.getMessage()));
        return ex.getMessage();
    }

    @ExceptionHandler(EntityByIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundHandler(Exception ex) {
        logger.error(String.format("Exception %s occurred with message \"%s\"", ex.getClass().getName(), ex.getMessage()));
        return ex.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String ConstraintViolationHandler(Exception ex) {
        Throwable cause = ex.getCause().getCause();
        logger.error(String.format("Exception %s occurred with message \"%s\"", cause.getClass().getName(), cause.getMessage()));
        return cause.getMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String HttpMessageNotReadableHandler(Exception ex) {
        logger.error(String.format("Exception %s occurred with message \"%s\"", ex.getClass().getName(), ex.getMessage()));
        return ex.getCause().getMessage();
    }
}
