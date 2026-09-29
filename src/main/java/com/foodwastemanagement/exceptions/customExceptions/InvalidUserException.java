package com.foodwastemanagement.exceptions.customExceptions;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message) {
        super(message);
    }
}
