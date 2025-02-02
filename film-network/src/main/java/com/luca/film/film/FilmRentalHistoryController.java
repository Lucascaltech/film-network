package com.luca.film.film;

import com.luca.film.film.dto.FilmRentalHistoryRequest;
import com.luca.film.film.dto.FilmRentalHistoryResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing film rental history records.
 */
@RestController
@RequestMapping("film-rental-histories")
@RequiredArgsConstructor
@Tag(name = "Film Rental History", description = "The film rental history API")
public class FilmRentalHistoryController {

    private final FilmRentalHistoryService filmRentalHistoryService;

    /**
     * Creates a new film rental history record.
     *
     * @param request the DTO containing rental history details
     * @return a ResponseEntity containing the created rental history and HTTP status
     */
    @PostMapping
    public ResponseEntity<?> createRentalHistory(@Valid @RequestBody FilmRentalHistoryRequest request) {
        try {
            FilmRentalHistoryResponse response = filmRentalHistoryService.createRentalHistory(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating rental history: " + ex.getMessage());
        }
    }

    /**
     * Retrieves a film rental history record by its ID.
     *
     * @param id the rental history ID
     * @return a ResponseEntity containing the rental history details
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalHistoryById(@PathVariable Integer id) {
        try {
            FilmRentalHistoryResponse response = filmRentalHistoryService.getRentalHistoryById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rental history not found with id: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving rental history: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all film rental history records.
     *
     * @return a ResponseEntity containing a list of all rental history records
     */
    @GetMapping
    public ResponseEntity<?> getAllRentalHistories() {
        try {
            return ResponseEntity.ok(filmRentalHistoryService.getAllRentalHistories());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving rental histories: " + ex.getMessage());
        }
    }

    /**
     * Updates an existing film rental history record.
     *
     * @param id      the ID of the rental history record to update
     * @param request the DTO containing updated rental history details
     * @return a ResponseEntity containing the updated rental history record
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRentalHistory(@PathVariable Integer id, @Valid @RequestBody FilmRentalHistoryRequest request) {
        try {
            FilmRentalHistoryResponse response = filmRentalHistoryService.updateRentalHistory(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rental history not found with id: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating rental history: " + ex.getMessage());
        }
    }

    /**
     * Deletes a film rental history record by its ID.
     *
     * @param id the rental history ID to delete
     * @return a ResponseEntity with HTTP status 204 if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRentalHistory(@PathVariable Integer id) {
        try {
            filmRentalHistoryService.deleteRentalHistory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rental history not found with id: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting rental history: " + ex.getMessage());
        }
    }
}
