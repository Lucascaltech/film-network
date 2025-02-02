package com.luca.film.film;

import com.luca.film.film.dto.FilmRentalHistoryRequest;
import com.luca.film.film.dto.FilmRentalHistoryResponse;
import com.luca.film.film.mapper.FilmRentalHistoryMapper;
import com.luca.film.user.User;
import com.luca.film.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing Film Rental History records.
 */
@Service
@RequiredArgsConstructor
public class FilmRentalHistoryService {

    private final FilmRentalHistoryRepository filmRentalHistoryRepository;
    private final FilmRentalHistoryMapper filmRentalHistoryMapper;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new film rental history record.
     *
     * @param request the DTO containing rental history details
     * @return the created FilmRentalHistoryResponse DTO
     */
    @Transactional
    public FilmRentalHistoryResponse createRentalHistory(FilmRentalHistoryRequest request) {
        // Retrieve associated user and film entities
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.userId()));
        Film film = filmRepository.findById(request.filmId())
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + request.filmId()));
        // Map and save the rental history record
        FilmRentalHistory history = filmRentalHistoryMapper.toFilmRentalHistory(request, user, film);
        FilmRentalHistory saved = filmRentalHistoryRepository.save(history);
        return filmRentalHistoryMapper.toFilmRentalHistoryResponse(saved);
    }

    /**
     * Retrieves a film rental history record by its ID.
     *
     * @param id the rental history ID
     * @return the FilmRentalHistoryResponse DTO
     */
    public FilmRentalHistoryResponse getRentalHistoryById(Integer id) {
        FilmRentalHistory history = filmRentalHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental history not found with id: " + id));
        return filmRentalHistoryMapper.toFilmRentalHistoryResponse(history);
    }

    /**
     * Retrieves all film rental history records.
     *
     * @return a list of FilmRentalHistoryResponse DTOs
     */
    public List<FilmRentalHistoryResponse> getAllRentalHistories() {
        return filmRentalHistoryRepository.findAll()
                .stream()
                .map(filmRentalHistoryMapper::toFilmRentalHistoryResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing film rental history record.
     *
     * @param id      the ID of the rental history record to update
     * @param request the DTO containing updated details
     * @return the updated FilmRentalHistoryResponse DTO
     */
    @Transactional
    public FilmRentalHistoryResponse updateRentalHistory(Integer id, FilmRentalHistoryRequest request) {
        FilmRentalHistory existing = filmRentalHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental history not found with id: " + id));
        // Update fields; here we assume user and film are not changed during update.
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
     * @param id the ID of the rental history record to delete
     */
    public void deleteRentalHistory(Integer id) {
        FilmRentalHistory existing = filmRentalHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental history not found with id: " + id));
        filmRentalHistoryRepository.delete(existing);
    }
}
