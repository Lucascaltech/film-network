package com.luca.film.film.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO for sending film rental history details to the client.
 */
@Getter
@Setter
@Builder
public class FilmRentalHistoryResponse {
    private Integer id;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private boolean returned;
    private boolean returnedApproved;
    private double rentalPrice;
    private Integer userId;
    private Integer filmId;
}
