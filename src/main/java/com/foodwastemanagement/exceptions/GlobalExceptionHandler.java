package com.foodwastemanagement.exceptions;

import com.foodwastemanagement.dto.response.BaseErrorResponse;
import com.foodwastemanagement.exceptions.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception) {

        BaseErrorResponse errorResponse = new BaseErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        errorResponse.setMessage(message);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseErrorResponse> handleInvalidRequestBody(
            HttpMessageNotReadableException exception) {

        BaseErrorResponse errorResponse = new BaseErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Invalid request body");

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }
}
