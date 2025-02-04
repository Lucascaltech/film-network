package com.luca.film.film.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Year;


public record FilmRequest(
        Integer id,

        @NotNull(message = "Title is required")
        @NotEmpty(message = "Title cannot be empty")
        String title,

            @NotNull(message = "Director is required")
            @NotEmpty(message = "Director cannot be empty")
            String director,

            @NotNull(message = "IMDB id is required")
            @NotEmpty(message = "IMDB id cannot be empty")
            String imdbId,

            @NotNull(message = "Synopsis is required")
            @NotEmpty(message = "Synopsis cannot be empty")
            String synopsis,

            // Optional: filmPoster may be null if not provided.
            String filmPoster,

            @NotNull(message = "Year is required")
            String year,

            // Indicates whether the film is archived.
            boolean archive,

            // Optional: if rating is provided. Otherwise, it could be null.
            Double rating,

            @NotNull(message = "Genre is required")
            @NotEmpty(message = "Genre cannot be empty")
            String genre
) {}



