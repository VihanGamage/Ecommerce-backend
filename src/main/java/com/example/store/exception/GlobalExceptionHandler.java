package com.example.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientInventoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInsufficientInventory(InsufficientInventoryException e){
        return e.getMessage();
    }
}
