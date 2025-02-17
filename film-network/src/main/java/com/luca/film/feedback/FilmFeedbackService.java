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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmFeedbackService {

    private final FilmFeedbackRepository filmFeedbackRepository;
    private final FilmFeedbackMapper filmFeedbackMapper;
    private final FilmRepository filmRepository;
    private final FilmRentalHistoryRepository filmRentalHistoryRepository;

    @Transactional
    public FilmFeedbackResponse createFeedback(FilmFeedbackRequest request, Authentication authentication) {
        Film film = filmRepository.findById(request.filmId())
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + request.filmId()));

        if(Objects.equals(film.getCreatedBy(), authentication.getName())){
            throw  new OperationNotPermittedException("You can give feedback to your own film");
        }
        FilmRentalHistory rentalRecord = filmRentalHistoryRepository.findByFilmAndCreatedByAndReturnedAndReturnedApproved(
                film, authentication.getName(), false, false
        ).orElseThrow(() -> new OperationNotPermittedException("No active rental found for this film."));

        rentalRecord.setReturned(true);
        // Map and save the feedback
        FilmFeedback feedback = filmFeedbackMapper.toFilmFeedback(request, film, authentication.getName());
        FilmFeedback saved = filmFeedbackRepository.save(feedback);
        filmRentalHistoryRepository.save(rentalRecord);
        return filmFeedbackMapper.toFilmFeedbackResponse(saved);
    }

    public FilmFeedbackResponse getFeedbackById(Integer id) {
        FilmFeedback feedback = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        return filmFeedbackMapper.toFilmFeedbackResponse(feedback);
    }

    /**
     * Retrieves feedback for a film ensuring it is always fresh and correctly ordered.
     * Uses explicit sorting and verifies the film's existence.
     */
    @Transactional(readOnly = true)
    public PageResponse<FilmFeedbackResponse> getFeedbackForFilmContent(Integer filmId, int page, int size) {
        // Ensure film exists before fetching feedback
        filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + filmId));

        // Define paging and sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        // Fetch fresh data from the database
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

    @Transactional
    public FilmFeedbackResponse updateFeedback(Integer id, FilmFeedbackRequest request) {
        FilmFeedback existingFeedback = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));

        existingFeedback.setRating(request.rating());
        existingFeedback.setReview(request.review());

        FilmFeedback updatedFeedback = filmFeedbackRepository.save(existingFeedback);
        updateFilmRating(updatedFeedback.getFilm());

        return filmFeedbackMapper.toFilmFeedbackResponse(updatedFeedback);
    }

    @Transactional
    public void deleteFeedback(Integer id) {
        FilmFeedback existingFeedback = filmFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));

        Film film = existingFeedback.getFilm();
        filmFeedbackRepository.delete(existingFeedback);
        updateFilmRating(film);
    }

    private void updateFilmRating(Film film) {
        List<FilmFeedback> feedbacks = filmFeedbackRepository.findByFilm_Id(film.getId(), Pageable.unpaged()).getContent();

        double averageRating = feedbacks.stream()
                .mapToDouble(FilmFeedback::getRating)
                .average()
                .orElse(0.0);

        film.setRating(averageRating);
        filmRepository.save(film);
    }
}
