package com.foodwastemanagement.exceptions.customExceptions;

public class UserExistsByUsernameException extends RuntimeException{
    public UserExistsByUsernameException(String message) {
        super(message);
    }
}
