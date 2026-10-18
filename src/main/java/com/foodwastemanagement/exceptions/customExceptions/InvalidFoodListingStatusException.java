package com.foodwastemanagement.exceptions.customExceptions;

public class InvalidFoodListingStatusException extends RuntimeException{
    public InvalidFoodListingStatusException(String message) {
        super(message);
    }
}
