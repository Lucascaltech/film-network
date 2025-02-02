package com.luca.film.feedback;

import com.luca.film.feedback.dto.FilmFeedbackRequest;
import com.luca.film.feedback.dto.FilmFeedbackResponse;
import com.luca.film.film.Film;
import com.luca.film.user.User;
import org.springframework.stereotype.Service;

/**
 * Mapper for converting between FilmFeedback entities and DTOs.
 */
@Service
public class FilmFeedbackMapper {

    /**
     * Maps a FilmFeedbackRequest, along with the associated Film and User, to a FilmFeedback entity.
     *
     * @param request the DTO containing feedback details
     * @param film    the Film entity associated with the feedback
     * @param user    the User entity who provided the feedback
     * @return the FilmFeedback entity
     */
    public com.luca.film.feedback.FilmFeedback toFilmFeedback(FilmFeedbackRequest request, Film film, User user) {
        return com.luca.film.feedback.FilmFeedback.builder()
                .rating(request.rating())
                .review(request.review())
                .film(film)
                .user(user)
                .build();
    }

    /**
     * Converts a FilmFeedback entity to a FilmFeedbackResponse DTO.
     *
     * @param feedback the FilmFeedback entity
     * @return the FilmFeedbackResponse DTO
     */
    public FilmFeedbackResponse toFilmFeedbackResponse(FilmFeedback feedback) {
        return FilmFeedbackResponse.builder()
                .id(feedback.getId())
                .rating(feedback.getRating())
                .review(feedback.getReview())
                .filmId(feedback.getFilm() != null ? feedback.getFilm().getId() : null)
                .userId(feedback.getUser() != null ? feedback.getUser().getId() : null)
                .createdAt(feedback.getCreatedAt())
                .lastModifiedAt(feedback.getLastModifiedAt())
                .build();
    }
}
