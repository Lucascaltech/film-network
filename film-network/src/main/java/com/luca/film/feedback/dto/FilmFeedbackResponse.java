package com.luca.film.feedback.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO for sending film feedback details to the client.
 */
@Getter
@Setter
@Builder
public class FilmFeedbackResponse {
    private Integer id;
    private Double rating;
    private String review;
    private Integer filmId;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
