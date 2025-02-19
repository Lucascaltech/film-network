package com.luca.film.feedback;

import com.luca.film.common.PageResponse;
import com.luca.film.feedback.dto.FilmFeedbackRequest;
import com.luca.film.feedback.dto.FilmFeedbackResponse;
import com.luca.film.film.Film;
import com.luca.film.film.FilmRentalHistory;
import com.luca.film.film.FilmRentalHistoryRepository;
import com.luca.film.film.FilmRepository;
import com.luca.film.film.exceptions.OperationNotPermittedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Service class for managing film feedback operations, including creation, retrieval,
 * updating, and deletion of feedback records.
 */
@Service
@RequiredArgsConstructor
public class FilmFeedbackService {

    private final FilmFeedbackRepository filmFeedbackRepository;
    private final FilmFeedbackMapper filmFeedbackMapper;
    private final FilmRepository filmRepository;
    private final FilmRentalHistoryRepository filmRentalHistoryRepository;

    /**
     * Creates a new film feedback record after verifying the user has rented the film.
     * A user cannot provide feedback for their own film.
     *
     * @param request        the feedback request containing rating and review details
     * @param authentication the authenticated user providing feedback
     * @return the created {@link FilmFeedbackResponse} DTO
     * @throws OperationNotPermittedException if the user attempts to give feedback on their own film
     *                                        or if there is no active rental record for the film
     */
    @Transactional
    public FilmFeedbackResponse createFeedback(FilmFeedbackRequest request, Authentication authentication) {
        // Fetch film details
        Film film = filmRepository.findById(request.filmId())
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + request.filmId()));

        // Prevent users from reviewing their own films
        if (Objects.equals(film.getCreatedBy(), authentication.getName())) {
            throw new OperationNotPermittedException("You cannot give feedback on your own film.");
        }

        // Ensure the user has an active rental record for the film
        FilmRentalHistory rentalRecord = filmRentalHistoryRepository.findByFilmAndCreatedByAndReturnedAndReturnedApproved(
                film, authentication.getName(), false, false
        ).orElseThrow(() -> new OperationNotPermittedException("No active rental found for this film."));

        rentalRecord.setReturned(true);

        // Map and save the feedback
        FilmFeedback feedback = filmFeedbackMapper.toFilmFeedback(request, film, authentication.getName());
        FilmFeedback savedFeedback = filmFeedbackRepository.save(feedback);

        // Update the film's average rating
        updateFilmRating(film);

        // Save the updated rental record
        filmRentalHistoryRepository.save(rentalRecord);

        return filmFeedbackMapper.toFilmFeedbackResponse(savedFeedback);
    }

    /**
     * Retrieves a feedback record by its unique ID.
     *
     * @param id the unique identifier of the feedback
     * @return the {@link FilmFeedbackResponse} DTO containing feedback details
     * @throws RuntimeException if the feedback record is not found
     */
    public FilmFeedbackResponse getFeedbackById(Integer id) {
        FilmFeedback feedback = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        return filmFeedbackMapper.toFilmFeedbackResponse(feedback);
    }

    /**
     * Retrieves a paginated list of feedback records for a specific film.
     * Ensures the film exists before fetching feedback.
     *
     * @param filmId the unique identifier of the film
     * @param page   the requested page number (zero-based)
     * @param size   the number of feedback records per page
     * @return a {@link PageResponse} containing a list of {@link FilmFeedbackResponse} DTOs
     * @throws RuntimeException if the film is not found
     */
    @Transactional(readOnly = true)
    public PageResponse<FilmFeedbackResponse> getFeedbackForFilmContent(Integer filmId, int page, int size) {
        // Ensure the film exists before fetching feedback
        filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + filmId));

        // Define paging and sorting criteria (descending order by creation date)
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // Retrieve feedback from the database
        Page<FilmFeedback> feedbackPage = filmFeedbackRepository.findByFilm_Id(filmId, pageable);

        // Convert feedback records to DTOs
        return new PageResponse<>(
                feedbackPage.stream()
                        .map(filmFeedbackMapper::toFilmFeedbackResponse)
                        .toList(),
                feedbackPage.getNumber(),
                feedbackPage.getSize(),
                feedbackPage.getTotalElements(),
                feedbackPage.getTotalPages(),
                feedbackPage.isFirst(),
                feedbackPage.isLast()
        );
    }

    /**
     * Updates an existing feedback record.
     *
     * @param id      the unique identifier of the feedback record
     * @param request the DTO containing updated rating and review details
     * @return the updated {@link FilmFeedbackResponse} DTO
     * @throws RuntimeException if the feedback record is not found
     */
    @Transactional
    public FilmFeedbackResponse updateFeedback(Integer id, FilmFeedbackRequest request) {
        // Retrieve existing feedback record
        FilmFeedback existingFeedback = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));

        // Update feedback details
        existingFeedback.setRating(request.rating());
        existingFeedback.setReview(request.review());

        // Save updated feedback
        FilmFeedback updatedFeedback = filmFeedbackRepository.save(existingFeedback);

        // Update the film's average rating
        updateFilmRating(updatedFeedback.getFilm());

        return filmFeedbackMapper.toFilmFeedbackResponse(updatedFeedback);
    }

    /**
     * Deletes a feedback record by its ID and updates the film's average rating.
     *
     * @param id the unique identifier of the feedback record to delete
     * @throws RuntimeException if the feedback record is not found
     */
    @Transactional
    public void deleteFeedback(Integer id) {
        // Retrieve existing feedback record
        FilmFeedback existingFeedback = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));

        // Retrieve the associated film
        Film film = existingFeedback.getFilm();

        // Delete feedback record
        filmFeedbackRepository.delete(existingFeedback);

        // Update the film's average rating
        updateFilmRating(film);
    }

    /**
     * Recalculates and updates the average rating of a film based on all its feedback.
     *
     * @param film the film whose rating needs to be updated
     */
    private void updateFilmRating(Film film) {
        // Retrieve all feedback records for the film
        List<FilmFeedback> feedbacks = filmFeedbackRepository.findByFilm_Id(film.getId(), Pageable.unpaged()).getContent();

        // Calculate the new average rating
        double averageRating = feedbacks.stream()
                .mapToDouble(FilmFeedback::getRating)
                .average()
                .orElse(0.0);

        // Round based on 3.5 threshold
    double roundedRating = (averageRating <= 3.5) ? (double) Math.floor(averageRating) : (double) Math.ceil(averageRating);

    // Update the film's rating
    film.setRating(roundedRating);
        filmRepository.save(film);
    }
}
