package com.luca.film.film.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Request DTO for creating or updating a film rental history record.
 */
public record FilmRentalHistoryRequest(
        @NotNull(message = "User id is required")
        Integer userId,
        @NotNull(message = "Film id is required")
        Integer filmId,
        @NotNull(message = "Rental date is required")
        LocalDateTime rentalDate,
        LocalDateTime returnDate,
        boolean returned,
        boolean returnedApproved,
        double rentalPrice
) { }
