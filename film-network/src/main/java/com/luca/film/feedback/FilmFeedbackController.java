package com.luca.film.feedback;

import com.luca.film.common.PageResponse;
import com.luca.film.feedback.dto.FilmFeedbackRequest;
import com.luca.film.feedback.dto.FilmFeedbackResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing user feedback on films.
 * This controller provides endpoints for creating, retrieving, updating, and deleting feedback records.
 * Users can submit ratings and reviews for films, and retrieve feedback records by ID or associated film.
 */
@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "The Feedback API allows users to submit and retrieve feedback for films.")
public class FilmFeedbackController {

    private final FilmFeedbackService filmFeedbackService;

    /**
     * Creates a new feedback record for a film.
     *
     * This endpoint allows users to submit a review and rating for a specific film.
     * The authentication object is used to associate the feedback with the currently logged-in user.
     *
     * @param request       the DTO containing feedback details (film ID, rating, and review text)
     * @param authentication the authenticated user submitting the feedback
     * @return ResponseEntity containing the created {@link FilmFeedbackResponse} and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<FilmFeedbackResponse> createFeedback(
            @Valid @RequestBody FilmFeedbackRequest request,
            Authentication authentication) {

        FilmFeedbackResponse response = filmFeedbackService.createFeedback(request, authentication);

        // Mark feedback as "own feedback" if it belongs to the authenticated user
        if (response.getUserId().equals(authentication.getName())) {
            response.setOwnFeedback(true);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves a specific film feedback record by its unique ID.
     *
     * This endpoint fetches the details of a given feedback entry, including the rating, review, and user who submitted it.
     * The response is marked as "own feedback" if it belongs to the requesting user.
     *
     * @param id             the unique identifier of the feedback record
     * @param authentication the authenticated user making the request
     * @return ResponseEntity containing the requested {@link FilmFeedbackResponse} and HTTP status 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<FilmFeedbackResponse> getFeedbackById(
            @PathVariable Integer id,
            Authentication authentication) {

        FilmFeedbackResponse response = filmFeedbackService.getFeedbackById(id);

        // Mark feedback as "own feedback" if it belongs to the authenticated user
        if (response.getUserId().equals(authentication.getName())) {
            response.setOwnFeedback(true);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing film feedback record.
     *
     * This endpoint allows users to modify a previously submitted feedback entry by updating the rating and review text.
     * Only the owner of the feedback can update it.
     *
     * @param id             the unique identifier of the feedback record to be updated
     * @param request        the DTO containing the updated feedback details
     * @param authentication the authenticated user making the request
     * @return ResponseEntity containing the updated {@link FilmFeedbackResponse} and HTTP status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<FilmFeedbackResponse> updateFeedback(
            @PathVariable Integer id,
            @Valid @RequestBody FilmFeedbackRequest request,
            Authentication authentication) {

        FilmFeedbackResponse response = filmFeedbackService.updateFeedback(id, request);

        // Mark feedback as "own feedback" if it belongs to the authenticated user
        if (authentication.getName().equals(response.getUserId())) {
            response.setOwnFeedback(true);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a film feedback record by its unique ID.
     *
     * This endpoint removes a user's feedback from the system. Only the owner of the feedback can delete it.
     * If the deletion is successful, a 204 (No Content) response is returned.
     *
     * @param id the unique identifier of the feedback record to be deleted
     * @return ResponseEntity with HTTP status 204 (No Content) if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable Integer id) {
        filmFeedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all feedback for a given film, paginated.
     *
     * This endpoint allows users to view all reviews and ratings submitted for a specific film.
     * The results are paginated based on the provided `page` and `size` parameters.
     * Each feedback entry is marked as "own feedback" if it belongs to the requesting user.
     *
     * @param filmId         the unique identifier of the film whose feedback is being retrieved
     * @param page           the page number to retrieve (default: 0)
     * @param size           the number of feedback records per page (default: 10)
     * @param authentication the authenticated user making the request
     * @return ResponseEntity containing a paginated list of {@link FilmFeedbackResponse} objects
     */
    @GetMapping("/film/{filmId}")
    public ResponseEntity<PageResponse<FilmFeedbackResponse>> getFeedbackForFilm(
            @PathVariable Integer filmId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        PageResponse<FilmFeedbackResponse> feedbackList = filmFeedbackService.getFeedbackForFilmContent(filmId, page, size);

        // Mark feedback as "own feedback" if it belongs to the authenticated user
        feedbackList.getContent().forEach(feedback -> {
            if (feedback.getUserId().equals(authentication.getName())) {
                feedback.setOwnFeedback(true);
            }
        });

        return ResponseEntity.ok(feedbackList);
    }
}
