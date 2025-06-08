package com.example.store.exception;

public class InsufficientInventoryException extends RuntimeException{
    public InsufficientInventoryException(String message){
        super(message);
    }
}
