package com.luca.film.handler;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCodes {
    NO_CODE(0, "No error code", HttpStatus.NOT_IMPLEMENTED),

    // Password-related errors (starting at 1001)
    INCORRECT_CURRENT_PASSWORD(1001, "Incorrect current password", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_SAME_AS_OLD(1002, "New password cannot be the same as the old password", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_DOES_NOT_MATCH(1003, "New password does not match", HttpStatus.BAD_REQUEST),

    // Account-related errors (starting at 2001)
    ACCOUNT_LOCKED(2001, "Account locked", HttpStatus.FORBIDDEN),
    ACCOUNT_DISABLED(2002, "Account disabled", HttpStatus.FORBIDDEN),

    // Authentication errors (starting at 3001)
    BAD_CREDENTIALS(3001, "Bad credentials", HttpStatus.UNAUTHORIZED),

    // Operation errors (starting at 4001)
    OPERATION_NOT_PERMITTED(4001, "Operation not permitted", HttpStatus.FORBIDDEN);

    @Getter
    private final int errorCode;
    @Getter
    private final String message;
    @Getter
    private final HttpStatus httpStatus;

    @Contract(pure = true)
    BusinessErrorCodes(int errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
