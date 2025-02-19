package com.luca.film.film.mapper;

import com.luca.film.file.FileUtils;
import com.luca.film.film.Film;
import com.luca.film.film.dto.FilmRequest;
import com.luca.film.film.dto.FilmResponse;
import com.luca.film.film.enums.Genre;
import org.springframework.stereotype.Service;

@Service
public class FilmMapper {

    public Film toFilm(FilmRequest request) {
        return Film.builder()
                .id(request.id())
                .title(request.title())
                .director(request.director())
                .imdbId(request.imdbId())
                .synopsis(request.synopsis())
                .filmPoster(request.filmPoster())
                .year(request.year())
                .archive(request.archive())
                .rating(request.rating())
                .genre(convertToGenre(request.genre()))
                .build();
    }

    /**
     * Converts the input string into a Genre enum value.
     *
     * @param genreStr the genre as a string
     * @return the matching Genre enum
     * @throws IllegalArgumentException if the genre string does not match any enum constant
     */
    private Genre convertToGenre(String genreStr) {
        try {
            return Genre.valueOf(genreStr.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid genre value: " + genreStr, ex);
        }
    }

    /**
     * Maps a Film entity to a FilmResponse DTO.
     *
     * @param film the Film entity to be converted
     * @return the FilmResponse DTO containing film details
     */
    public FilmResponse toFilmResponse(Film film) {
        return FilmResponse.builder()
                .id(film.getId())
                .title(film.getTitle())
                .director(film.getDirector())
                .imdbId(film.getImdbId())
                .synopsis(film.getSynopsis())
                .filmPoster(FileUtils.readFileFromLocation(film.getFilmPoster()))
                .year(film.getYear())
                .addedBy(film.getCreatedBy())
                .archive(film.isArchive())
                .rating(film.getRating() <=3.5 ? Math.floor(film.getRating()) : Math.ceil(film.getRating()))
                .genre(film.getGenre())
                .createdAt(film.getCreatedAt())
                .lastModifiedAt(film.getLastModifiedAt())
                .build();
    }

    // Additional mapping methods (if needed) can be added here.
}
