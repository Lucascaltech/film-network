package com.luca.film.film;

import com.luca.film.film.dto.FilmRentalHistoryRequest;
import com.luca.film.film.dto.FilmRentalHistoryResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing film rental history records.
 */
@RestController
@RequestMapping("film-rental-histories")
@RequiredArgsConstructor
@Tag(name = "Film-Rental-History", description = "The film rental history API")
public class FilmRentalHistoryController {

    private final FilmRentalHistoryService filmRentalHistoryService;

    /**
     * Creates a new film rental history record.
     *
     * @param request the DTO containing rental history details
     * @return a ResponseEntity containing the created rental history and HTTP status
     */
    @PostMapping
    public ResponseEntity<FilmRentalHistoryResponse> createRentalHistory(@Valid @RequestBody FilmRentalHistoryRequest request, Authentication authentication) {
            FilmRentalHistoryResponse response = filmRentalHistoryService.createRentalHistory(request, authentication);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    /**
     * Retrieves a film rental history record by its ID.
     *
     * @param id the rental history ID
     * @return a ResponseEntity containing the rental history details
     */
    @GetMapping("/{id}")
    public ResponseEntity<FilmRentalHistoryResponse> getRentalHistoryById(@PathVariable Integer id) {

            FilmRentalHistoryResponse response = filmRentalHistoryService.getRentalHistoryById(id);
            return ResponseEntity.ok(response);

    }

    /**
     * Retrieves all film rental history records.
     *
     * @return a ResponseEntity containing a list of all rental history records
     */
    @GetMapping
    public ResponseEntity<List<FilmRentalHistoryResponse>> getAllRentalHistories() {
            return ResponseEntity.ok(filmRentalHistoryService.getAllRentalHistories());

    }

    /**
     * Updates an existing film rental history record.
     *
     * @param id      the ID of the rental history record to update
     * @param request the DTO containing updated rental history details
     * @return a ResponseEntity containing the updated rental history record
     */
    @PutMapping("/{id}")
    public ResponseEntity<FilmRentalHistoryResponse> updateRentalHistory(@PathVariable Integer id, @Valid @RequestBody FilmRentalHistoryRequest request) {

            FilmRentalHistoryResponse response = filmRentalHistoryService.updateRentalHistory(id, request);
            return ResponseEntity.ok(response);

    }

    /**
     * Deletes a film rental history record by its ID.
     *
     * @param id the rental history ID to delete
     * @return a ResponseEntity with HTTP status 204 if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRentalHistory(@PathVariable Integer id) {
            filmRentalHistoryService.deleteRentalHistory(id);
            return ResponseEntity.noContent().build();
    }
}
