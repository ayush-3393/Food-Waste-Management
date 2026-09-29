package com.foodwastemanagement.exceptions.customExceptions;

public class UserNotLoggedInException extends RuntimeException{
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
