package com.foodwastemanagement.exceptions;

import com.foodwastemanagement.dto.response.BaseErrorResponse;
import com.foodwastemanagement.exceptions.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserExistsByUsernameException.class)
    public ResponseEntity<BaseErrorResponse> handleUserExistsByUsernameException(
            UserExistsByUsernameException exception){
        BaseErrorResponse errorResponse = new BaseErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotExistsByUsernameException.class)
    public ResponseEntity<BaseErrorResponse> handleUserDoesNotExistsByUsernameException(
            UserDoesNotExistsByUsernameException exception){
        BaseErrorResponse errorResponse = new BaseErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<BaseErrorResponse> handleInvalidPasswordException(
            InvalidPasswordException exception){
        BaseErrorResponse errorResponse = new BaseErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<BaseErrorResponse> handleUserNotLoggedInException(
            UserNotLoggedInException exception){
        BaseErrorResponse errorResponse = new BaseErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyLoggedInException.class)
    public ResponseEntity<BaseErrorResponse> handleUserAlreadyLoggedInException(
            UserAlreadyLoggedInException exception){
        BaseErrorResponse errorResponse = new BaseErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
