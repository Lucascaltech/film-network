package com.luca.film.feedback.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for creating or updating a film feedback record.
 */
public record FilmFeedbackRequest(
        @NotNull(message = "Film id is required")
        Integer filmId,

        @NotNull(message = "User id is required")
        Integer userId,

        @NotNull(message = "Rating is required")
        @Min(value = 0, message = "Rating must be at least 0")
        @Max(value = 5, message = "Rating must be at most 5")
        Double rating,

        @NotNull(message = "Review is required")
        @NotEmpty(message = "Review cannot be empty")
        String review
) { }
