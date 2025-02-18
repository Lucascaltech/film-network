package com.luca.film.handler;

import com.luca.film.film.exceptions.OperationNotPermittedException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

/**
 * Global exception handler to catch and handle different types of exceptions in the application.
 */
@RestControllerAdvice
public class GlobalExceptionalHandler {

    /**
     * Handles account lock exceptions.
     *
     * @param e the {@link LockedException} thrown when an account is locked.
     * @return a response entity containing the error code and message.
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .errorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getErrorCode())
                        .errorMessage(BusinessErrorCodes.ACCOUNT_LOCKED.getMessage())
                        .build());
    }

    /**
     * Handles account disabled exceptions.
     *
     * @param e the {@link DisabledException} thrown when an account is disabled.
     * @return a response entity containing the error code and message.
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .errorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getErrorCode())
                        .errorMessage(BusinessErrorCodes.ACCOUNT_DISABLED.getMessage())
                        .build());
    }

    /**
     * Handles bad credentials exceptions.
     *
     * @param e the {@link BadCredentialsException} thrown when authentication fails due to incorrect credentials.
     * @return a response entity containing the error code and message.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .errorCode(BusinessErrorCodes.BAD_CREDENTIALS.getErrorCode())
                        .errorMessage(BusinessErrorCodes.BAD_CREDENTIALS.getMessage())
                        .build());
    }

    /**
     * Handles messaging exceptions.
     *
     * @param e the {@link MessagingException} thrown when an email-related error occurs.
     * @return a response entity containing the error message.
     */
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .errorMessage(e.getMessage())
                        .build());
    }

    /**
     * Handles validation errors for method arguments.
     *
     * @param e the {@link MethodArgumentNotValidException} thrown when request validation fails.
     * @return a response entity containing the validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        Set<String> validationErrors = new HashSet<>();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            validationErrors.add(error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .validationErrors(validationErrors)
                        .build());
    }

    /**
     * Handles exceptions related to unauthorized operations.
     *
     * @param e the {@link OperationNotPermittedException} thrown when an operation is not allowed.
     * @return a response entity containing the error code and message.
     */
    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleOperationNotPermittedException(OperationNotPermittedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionResponse.builder()
                        .errorCode(BusinessErrorCodes.OPERATION_NOT_PERMITTED.getErrorCode())
                        .errorMessage(e.getMessage())
                        .build());
    }

    /**
     * Handles all other exceptions that are not explicitly caught.
     *
     * @param e the exception that was thrown.
     * @return a generic error response indicating an internal server error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        e.printStackTrace(); // Logging the exception for debugging
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .errorMessage("An error occurred, please try again later.")
                        .build());
    }
}
