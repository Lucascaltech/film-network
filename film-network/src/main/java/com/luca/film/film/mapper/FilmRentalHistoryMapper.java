package com.luca.film.film.mapper;

import com.luca.film.film.Film;
import com.luca.film.film.FilmRentalHistory;
import com.luca.film.film.dto.FilmRentalHistoryRequest;
import com.luca.film.film.dto.FilmRentalHistoryResponse;
import com.luca.film.film.dto.RentedFilmResponse;
import com.luca.film.user.User;
import org.springframework.stereotype.Service;

/**
 * Mapper for converting between FilmRentalHistory entities and DTOs.
 */
@Service
public class FilmRentalHistoryMapper {

    /**
     * Converts a FilmRentalHistoryRequest DTO along with associated User and Film
     * into a FilmRentalHistory entity.
     *
     * @param request the DTO containing rental history details
     * @param user    the User who rented the film
     * @param film    the Film being rented
     * @return a FilmRentalHistory entity
     */
    public FilmRentalHistory toFilmRentalHistory(FilmRentalHistoryRequest request, User user, Film film) {
        return FilmRentalHistory.builder()
                .rentalDate(request.rentalDate())
                .returnDate(request.returnDate())
                .returned(request.returned())
                .returnedApproved(request.returnedApproved())
                .rentalPrice(request.rentalPrice())
                .user(user)
                .film(film)
                .build();
    }

    /**
     * Converts a FilmRentalHistory entity to a FilmRentalHistoryResponse DTO.
     *
     * @param history the entity to convert
     * @return the FilmRentalHistoryResponse DTO
     */
    public FilmRentalHistoryResponse toFilmRentalHistoryResponse(FilmRentalHistory history) {
        return FilmRentalHistoryResponse.builder()
                .id(history.getId())
                .rentalDate(history.getRentalDate())
                .returnDate(history.getReturnDate())
                .returned(history.isReturned())
                .returnedApproved(history.isReturnedApproved())
                .rentalPrice(history.getRentalPrice())
                .userId(history.getUser() != null ? history.getUser().getId() : null)
                .filmId(history.getFilm() != null ? history.getFilm().getId() : null)
                .build();
    }

    /**
     * Converts a FilmRentalHistory entity to a RentedFilmResponse DTO.
     *
     * @param history the FilmRentalHistory entity to convert
     * @return the RentedFilmResponse DTO containing selected fields from Film and FilmRentalHistory
     */
    public RentedFilmResponse toRentedFilmResponse(FilmRentalHistory history) {
        Film film = history.getFilm();
        return RentedFilmResponse.builder()
                .id(history.getId())
                .filmTitle(film != null ? film.getTitle() : null)
                .director(film != null ? film.getDirector() : null)
                // If imdbId is stored as a String in Film, change the type of imdbId in the DTO accordingly.
                .imdbId(film != null ? film.getImdbId(): null)
                .rentalDate(history.getRentalDate())
                .returnApproved(history.isReturnedApproved())
                .returned(history.isReturned())
                .rentalPrice(history.getRentalPrice())
                .build();
    }
}
