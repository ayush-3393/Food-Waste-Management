package com.foodwastemanagement.exceptions.customExceptions;

public class UserDoesNotExistsByUsernameException extends RuntimeException{
    public UserDoesNotExistsByUsernameException(String message) {
        super(message);
    }
}
