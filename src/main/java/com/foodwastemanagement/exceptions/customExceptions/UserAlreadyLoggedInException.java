package com.foodwastemanagement.exceptions.customExceptions;

public class UserAlreadyLoggedInException extends RuntimeException{
    public UserAlreadyLoggedInException(String message) {
        super(message);
    }
}
