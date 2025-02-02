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
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    /**
     * Handle the locked exception
     * @param e the locked exception
     * @return the response entity with the exception response
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse
                        .builder().errorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getErrorCode())
                        .errorMessage(BusinessErrorCodes.ACCOUNT_LOCKED.getMessage())
                        .errorMessage(e.getMessage())
                        .build());
    }

    /**
     * Handle the disabled exception
     * @param e the disabled exception
     * @return the response entity with the exception response
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse
                        .builder().errorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getErrorCode())
                        .errorMessage(BusinessErrorCodes.ACCOUNT_DISABLED.getMessage())
                        .errorMessage(e.getMessage())
                        .build());
    }

    /**
     * Handle the bad credentials exception
     * @param e the bad credentials exception
     * @return the response entity with the exception response
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse
                        .builder().errorCode(BusinessErrorCodes.BAD_CREDENTIALS.getErrorCode())
                        .errorMessage(BusinessErrorCodes.BAD_CREDENTIALS.getMessage())
                        .errorMessage(e.getMessage())
                        .build());
    }

    /**
     * Handle the messaging exception
     * @param e the messaging exception
     * @return the response entity with the exception response
     */
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse
                        .builder()
                        .errorMessage(e.getMessage())
                        .build());
    }

    /**
     * Handle the method argument not valid exception
     * @param e the method argument not valid exception
     * @return the response entity with the exception response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        Set<String> validationErrors = new HashSet<>();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            String defaultMessage = error.getDefaultMessage();
            validationErrors.add(defaultMessage);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse
                        .builder().validationErrors(validationErrors)
                        .build());
    }

     /**
     * Handle the OperationNotPermittedException.
     *
     * @param e the OperationNotPermittedException thrown by the application
     * @return a ResponseEntity with FORBIDDEN status and error details
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
     * Handle any other exception
     * @param e the exception
     * @return the response entity with the exception response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse
                        .builder()
                        .errorMessage("An error occurred, Please try again later")
                        .errorMessage(e.getMessage())
                        .build());
    }
}
