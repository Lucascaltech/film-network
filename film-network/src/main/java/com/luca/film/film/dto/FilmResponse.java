package com.luca.film.film.dto;

import java.time.LocalDateTime;
import java.time.Year;

import com.luca.film.film.enums.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for sending Film details in responses.
 */
@Getter
@Setter
@Builder
public class FilmResponse {
    private Integer id;
    private String title;
    private String director;
    private String imdbId;
    private String synopsis;
    private byte[] filmPoster;
    private String year;
    private boolean archive;
    private Double rating;
    private Genre genre;
    private String addedBy;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
