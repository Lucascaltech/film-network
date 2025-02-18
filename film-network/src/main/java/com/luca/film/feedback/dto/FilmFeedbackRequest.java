package com.luca.film.feedback.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO (Data Transfer Object) for submitting feedback on a film.
 * This DTO is used for both creating and updating feedback records.
 * It ensures that the input data meets validation constraints before processing.
 */
public record FilmFeedbackRequest(

        @NotNull(message = "Film id is required")
        Integer filmId,

        @NotNull(message = "Rating is required")
        @Min(value = 0, message = "Rating must be at least 0")
        @Max(value = 5, message = "Rating must be at most 5")
        Double rating,

        @NotNull(message = "Review is required")
        @NotEmpty(message = "Review cannot be empty")
        String review
) { }
