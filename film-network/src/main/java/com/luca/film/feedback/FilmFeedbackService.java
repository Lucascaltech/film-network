package com.luca.film.feedback;

import com.luca.film.common.PageResponse;
import com.luca.film.feedback.dto.FilmFeedbackRequest;
import com.luca.film.feedback.dto.FilmFeedbackResponse;
import com.luca.film.film.Film;
import com.luca.film.film.FilmRepository;
import com.luca.film.film.exceptions.OperationNotPermittedException;
import com.luca.film.user.User;
import com.luca.film.user.UserRepository;
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
 * Service layer for managing Film Feedback records.
 */
@Service
@RequiredArgsConstructor
public class FilmFeedbackService {

    private final FilmFeedbackRepository filmFeedbackRepository;
    private final FilmFeedbackMapper filmFeedbackMapper;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new film feedback record.
     *
     * @param request the DTO containing feedback details
     * @return the created FilmFeedbackResponse DTO
     */
    @Transactional
    public FilmFeedbackResponse createFeedback(FilmFeedbackRequest request, Authentication authentication) {
        // Retrieve associated Film and User entities
        Film film = filmRepository.findById(request.filmId())
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + request.filmId()));

        if (film.isArchive()){
            throw new OperationNotPermittedException("you can not give feedback to archived film");
        }
        User user = (User) authentication.getPrincipal();
        if(Objects.equals(film.getAddedBy(), user)){
            throw  new OperationNotPermittedException("You can give feedback to your own film");
        }
        // Map and save the feedback
        FilmFeedback feedback = filmFeedbackMapper.toFilmFeedback(request, film, user);
        FilmFeedback saved = filmFeedbackRepository.save(feedback);
        return filmFeedbackMapper.toFilmFeedbackResponse(saved);
    }

    /**
     * Retrieves a film feedback record by its ID.
     *
     * @param id the feedback ID
     * @return the FilmFeedbackResponse DTO
     */
    public FilmFeedbackResponse getFeedbackById(Integer id) {
        com.luca.film.feedback.FilmFeedback feedback = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        return filmFeedbackMapper.toFilmFeedbackResponse(feedback);
    }

    /**
     * Retrieves all film feedback records.
     *
     * @return a list of FilmFeedbackResponse DTOs
     */
    public List<FilmFeedbackResponse> getAllFeedback() {
        return filmFeedbackRepository.findAll()
                .stream()
                .map(filmFeedbackMapper::toFilmFeedbackResponse)
                .toList();
    }

    /**
     * Updates an existing film feedback record.
     *
     * @param id      the ID of the feedback record to update
     * @param request the DTO containing updated feedback details
     * @return the updated FilmFeedbackResponse DTO
     */
    @Transactional
    public FilmFeedbackResponse updateFeedback(Integer id, FilmFeedbackRequest request) {
        com.luca.film.feedback.FilmFeedback existing = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        // For this example, we assume that the film and user are not changed during update.
        existing.setRating(request.rating());
        existing.setReview(request.review());
        com.luca.film.feedback.FilmFeedback saved = filmFeedbackRepository.save(existing);
        return filmFeedbackMapper.toFilmFeedbackResponse(saved);
    }

    /**
     * Deletes a film feedback record by its ID.
     *
     * @param id the ID of the feedback record to delete
     */
    public void deleteFeedback(Integer id) {
        com.luca.film.feedback.FilmFeedback existing = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        filmFeedbackRepository.delete(existing);
    }

   /**
     * Retrieves a page of feedback for a given film, sorted so that the most recent feedback is shown first,
     * and returns only the content as a list.
     *
     * @param filmId the ID of the film for which to retrieve feedback
     * @param page   the page number (0-based)
     * @param size   the number of feedback records per page
     * @return a list of FilmFeedbackResponse DTOs
     */
    public PageResponse<FilmFeedbackResponse> getFeedbackForFilmContent(Integer filmId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<FilmFeedback> feedbackPage = filmFeedbackRepository.findByFilm_Id(filmId, pageable);
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
}
