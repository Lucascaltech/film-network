package com.luca.film.feedback;

import com.luca.film.feedback.dto.FilmFeedbackRequest;
import com.luca.film.feedback.dto.FilmFeedbackResponse;
import com.luca.film.film.Film;
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
    public com.luca.film.feedback.FilmFeedback toFilmFeedback(FilmFeedbackRequest request, Film film, String user) {
        return com.luca.film.feedback.FilmFeedback.builder()
                .rating(request.rating())
                .review(request.review())
                .film(film)
                .userid(user)
                .build();
    }

    /**
 * Converts a FilmFeedback entity to a FilmFeedbackResponse DTO.
 *
 * @param feedback the FilmFeedback entity
 * @return the FilmFeedbackResponse DTO
 */
public FilmFeedbackResponse toFilmFeedbackResponse(FilmFeedback feedback) {
    // Round the rating based on the given rules
    double originalRating = feedback.getRating();
    double roundedRating = (originalRating <= 3.5) ? (double) Math.floor(originalRating) : (double) Math.ceil(originalRating);

    return FilmFeedbackResponse.builder()
            .id(feedback.getId())
            .rating(roundedRating)  // Use rounded rating
            .review(feedback.getReview())
            .filmId(feedback.getFilm() != null ? feedback.getFilm().getId() : null)
            .userId(feedback.getUserid() != null ? feedback.getUserid() : null)
            .createdAt(feedback.getCreatedAt())
            .lastModifiedAt(feedback.getLastModifiedAt())
            .build();
}
}
