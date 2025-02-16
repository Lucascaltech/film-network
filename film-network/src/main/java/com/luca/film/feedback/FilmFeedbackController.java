package com.luca.film.feedback;

import com.luca.film.common.PageResponse;
import com.luca.film.feedback.dto.FilmFeedbackRequest;
import com.luca.film.feedback.dto.FilmFeedbackResponse;
import com.luca.film.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<FilmFeedbackResponse> createFeedback(@Valid @RequestBody FilmFeedbackRequest request, Authentication authentication) {

        FilmFeedbackResponse response = filmFeedbackService.createFeedback(request, authentication);
//        User user = (User) authentication.getPrincipal();
        if (response.getUserId().equals(authentication.getName())) {
            response.setOwnFeedback(true);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves a film feedback record by its ID.
     *
     * @param id the feedback ID
     * @return a ResponseEntity containing the FilmFeedbackResponse DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<FilmFeedbackResponse> getFeedbackById(@PathVariable Integer id, Authentication authentication) {

        FilmFeedbackResponse response = filmFeedbackService.getFeedbackById(id);
//        User user = (User) authentication.getPrincipal();
        if (response.getUserId().equals(authentication.getName())) {
            response.setOwnFeedback(true);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all film feedback records.
     *
     * @return a ResponseEntity containing a list of FilmFeedbackResponse DTOs
     */
    @GetMapping
    public ResponseEntity<List<FilmFeedbackResponse>> getAllFeedback(Authentication authentication) {

//        User user = (User) authentication.getPrincipal();
        List<FilmFeedbackResponse> responses = filmFeedbackService.getAllFeedback();
        responses.forEach(
                feedback -> {
                    if (authentication.getName().equals(feedback.getUserId())) {
                        feedback.setOwnFeedback(true);
                    }
                }
        );
        return ResponseEntity.ok(responses);
    }

    /**
     * Updates an existing film feedback record.
     *
     * @param id      the ID of the feedback record to update
     * @param request the DTO containing updated feedback details
     * @return a ResponseEntity containing the updated FilmFeedbackResponse DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<FilmFeedbackResponse> updateFeedback(@PathVariable Integer id, @Valid @RequestBody FilmFeedbackRequest request, Authentication authentication) {

//        User user = (User) authentication.getPrincipal();
        FilmFeedbackResponse response = filmFeedbackService.updateFeedback(id, request);
        if (authentication.getName().equals(response.getUserId())) {
            response.setOwnFeedback(true);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a film feedback record by its ID.
     *
     * @param id the ID of the feedback record to delete
     * @return a ResponseEntity with HTTP status 204 if deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable Integer id) {
        filmFeedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all feedback of given film id.
     *
     * @param filmId film identifier
     * @param page   page number
     * @param size   size of the page
     * @return Film Feedback Response
     */
    @GetMapping("/film/{filmId}")
    public ResponseEntity<PageResponse<FilmFeedbackResponse>> getFeedbackForFilm(
            @PathVariable Integer filmId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, Authentication authentication) {
        PageResponse<FilmFeedbackResponse> feedbackList = filmFeedbackService.getFeedbackForFilmContent(filmId, page, size);
        feedbackList.getContent().forEach(feedback -> {
            if (feedback.getUserId().equals(authentication.getName())) {
                feedback.setOwnFeedback(true);
            }
        });

        return ResponseEntity.ok(feedbackList);
    }
}
