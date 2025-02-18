package com.luca.film.film;

import com.luca.film.film.dto.FilmRentalHistoryRequest;
import com.luca.film.film.dto.FilmRentalHistoryResponse;
import com.luca.film.film.mapper.FilmRentalHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer responsible for managing film rental history records.
 * <p>
 * This service provides methods to create, retrieve, update, and delete rental history records.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class FilmRentalHistoryService {

    private final FilmRentalHistoryRepository filmRentalHistoryRepository;
    private final FilmRentalHistoryMapper filmRentalHistoryMapper;
    private final FilmRepository filmRepository;

    /**
     * Creates a new film rental history record.
     *
     * @param request       The DTO containing rental history details.
     * @param authentication The authentication object representing the current user.
     * @return The created {@link FilmRentalHistoryResponse} DTO.
     * @throws RuntimeException If the specified film does not exist.
     */
    @Transactional
    public FilmRentalHistoryResponse createRentalHistory(FilmRentalHistoryRequest request, Authentication authentication) {
        Film film = filmRepository.findById(request.filmId())
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + request.filmId()));
        // Map and save the rental history record
        FilmRentalHistory history = filmRentalHistoryMapper.toFilmRentalHistory(request, authentication.getName(), film);
        FilmRentalHistory saved = filmRentalHistoryRepository.save(history);
        return filmRentalHistoryMapper.toFilmRentalHistoryResponse(saved);
    }

    /**
     * Retrieves a film rental history record by its ID.
     *
     * @param id The ID of the rental history record.
     * @return The corresponding {@link FilmRentalHistoryResponse} DTO.
     * @throws RuntimeException If no rental history is found with the given ID.
     */
    public FilmRentalHistoryResponse getRentalHistoryById(Integer id) {
        FilmRentalHistory history = filmRentalHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental history not found with id: " + id));
        return filmRentalHistoryMapper.toFilmRentalHistoryResponse(history);
    }

    /**
     * Retrieves all film rental history records.
     *
     * @return A list of {@link FilmRentalHistoryResponse} DTOs representing all rental history records.
     */
    public List<FilmRentalHistoryResponse> getAllRentalHistories() {
        return filmRentalHistoryRepository.findAll()
                .stream()
                .map(filmRentalHistoryMapper::toFilmRentalHistoryResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing film rental history record.
     * <p>
     * This method updates the rental date, return date, rental price, and return status.
     * The film and user details remain unchanged.
     * </p>
     *
     * @param id      The ID of the rental history record to update.
     * @param request The DTO containing updated rental history details.
     * @return The updated {@link FilmRentalHistoryResponse} DTO.
     * @throws RuntimeException If no rental history is found with the given ID.
     */
    @Transactional
    public FilmRentalHistoryResponse updateRentalHistory(Integer id, FilmRentalHistoryRequest request) {
        FilmRentalHistory existing = filmRentalHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental history not found with id: " + id));

        // Update fields; assuming user and film remain unchanged.
        existing.setRentalDate(request.rentalDate());
        existing.setReturnDate(request.returnDate());
        existing.setReturned(request.returned());
        existing.setRentalPrice(request.rentalPrice());

        FilmRentalHistory saved = filmRentalHistoryRepository.save(existing);
        return filmRentalHistoryMapper.toFilmRentalHistoryResponse(saved);
    }

    /**
     * Deletes a film rental history record by its ID.
     *
     * @param id The ID of the rental history record to delete.
     * @throws RuntimeException If no rental history is found with the given ID.
     */
    public void deleteRentalHistory(Integer id) {
        FilmRentalHistory existing = filmRentalHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental history not found with id: " + id));
        filmRentalHistoryRepository.delete(existing);
    }
}
