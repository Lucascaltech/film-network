package com.luca.film.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

/**
 * A response object used for handling exceptions and returning structured error details to the client.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

    /**
     * The unique error code associated with the exception.
     */
    private Integer errorCode;

    /**
     * A brief description of the exception that occurred.
     */
    private String exceptionDescription;

    /**
     * A user-friendly error message providing details about the issue.
     */
    private String errorMessage;

    /**
     * A set of validation errors, typically used for form validation failures.
     */
    private Set<String> validationErrors;

    /**
     * A map of specific errors where the key represents the field or attribute and the value describes the error.
     */
    private Map<String, String> errors;
}
