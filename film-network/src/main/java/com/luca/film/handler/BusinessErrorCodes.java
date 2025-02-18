package com.luca.film.handler;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;

/**
 * Enum representing business error codes used throughout the application.
 * Each error code is associated with a specific message and an HTTP status.
 */
@Getter
public enum BusinessErrorCodes {
    /**
     * Default error code indicating no specific error.
     */
    NO_CODE(0, "No error code", HttpStatus.NOT_IMPLEMENTED),

    // Password-related errors (starting at 1001)
    /**
     * Error when the provided current password is incorrect.
     */
    INCORRECT_CURRENT_PASSWORD(1001, "Incorrect current password", HttpStatus.BAD_REQUEST),

    /**
     * Error when the new password is the same as the old password.
     */
    NEW_PASSWORD_SAME_AS_OLD(1002, "New password cannot be the same as the old password", HttpStatus.BAD_REQUEST),

    /**
     * Error when the new password confirmation does not match.
     */
    NEW_PASSWORD_DOES_NOT_MATCH(1003, "New password does not match", HttpStatus.BAD_REQUEST),

    // Account-related errors (starting at 2001)
    /**
     * Error when the user's account is locked.
     */
    ACCOUNT_LOCKED(2001, "Account locked", HttpStatus.FORBIDDEN),

    /**
     * Error when the user's account is disabled.
     */
    ACCOUNT_DISABLED(2002, "Account disabled", HttpStatus.FORBIDDEN),

    // Authentication errors (starting at 3001)
    /**
     * Error when the provided authentication credentials are incorrect.
     */
    BAD_CREDENTIALS(3001, "Bad credentials", HttpStatus.UNAUTHORIZED),

    // Operation errors (starting at 4001)
    /**
     * Error when an operation is not permitted due to access restrictions.
     */
    OPERATION_NOT_PERMITTED(4001, "Operation not permitted", HttpStatus.FORBIDDEN);

    private final int errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    /**
     * Constructor for BusinessErrorCodes.
     *
     * @param errorCode  the unique identifier for the error
     * @param message    the descriptive message of the error
     * @param httpStatus the associated HTTP status code
     */
    @Contract(pure = true)
    BusinessErrorCodes(int errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
