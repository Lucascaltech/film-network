package com.luca.film.feedback;

import com.luca.film.feedback.dto.FilmFeedbackRequest;
import com.luca.film.feedback.dto.FilmFeedbackResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing film feedback records.
 */
@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "The feedback API")
public class FilmFeedbackController {

    private final FilmFeedbackService filmFeedbackService;

    /**
     * Creates a new film feedback record.
     *
     * @param request the DTO containing feedback details
     * @return a ResponseEntity containing the created FilmFeedbackResponse DTO and HTTP status
     */
    @PostMapping
    public ResponseEntity<?> createFeedback(@Valid @RequestBody FilmFeedbackRequest request) {
        try {
            FilmFeedbackResponse response = filmFeedbackService.createFeedback(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating feedback: " + ex.getMessage());
        }
    }

    /**
     * Retrieves a film feedback record by its ID.
     *
     * @param id the feedback ID
     * @return a ResponseEntity containing the FilmFeedbackResponse DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedbackById(@PathVariable Integer id) {
        try {
            FilmFeedbackResponse response = filmFeedbackService.getFeedbackById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found with id: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving feedback: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all film feedback records.
     *
     * @return a ResponseEntity containing a list of FilmFeedbackResponse DTOs
     */
    @GetMapping
    public ResponseEntity<?> getAllFeedback() {
        try {
            return ResponseEntity.ok(filmFeedbackService.getAllFeedback());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving feedbacks: " + ex.getMessage());
        }
    }

    /**
     * Updates an existing film feedback record.
     *
     * @param id      the ID of the feedback record to update
     * @param request the DTO containing updated feedback details
     * @return a ResponseEntity containing the updated FilmFeedbackResponse DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeedback(@PathVariable Integer id, @Valid @RequestBody FilmFeedbackRequest request) {
        try {
            FilmFeedbackResponse response = filmFeedbackService.updateFeedback(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found with id: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating feedback: " + ex.getMessage());
        }
    }

    /**
     * Deletes a film feedback record by its ID.
     *
     * @param id the ID of the feedback record to delete
     * @return a ResponseEntity with HTTP status 204 if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Integer id) {
        try {
            filmFeedbackService.deleteFeedback(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found with id: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting feedback: " + ex.getMessage());
        }
    }

    @GetMapping("/film/{filmId}")
   public ResponseEntity<?> getFeedbackForFilm(
        @PathVariable Integer filmId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    try {
        List<FilmFeedbackResponse> feedbackList = filmFeedbackService.getFeedbackForFilmContent(filmId, page, size);
        return ResponseEntity.ok(feedbackList);
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving feedback: " + ex.getMessage());
    }
}
}
