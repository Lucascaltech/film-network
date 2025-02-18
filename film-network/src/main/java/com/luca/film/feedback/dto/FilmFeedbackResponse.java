package com.luca.film.feedback.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response DTO (Data Transfer Object) for providing film feedback details to the client.
 * This class is used to send feedback information, including ratings and reviews, back to the user.
 */
@Getter
@Setter
@Builder
public class FilmFeedbackResponse {

    /**
     * The unique identifier of the feedback record.
     */
    private Integer id;

    /**
     * The rating given to the film.
     * Typically a value between 0 and 5.
     */
    private Double rating;

    /**
     * The textual review provided by the user.
     */
    private String review;

    /**
     * The unique identifier of the film that this feedback is associated with.
     */
    private Integer filmId;

    /**
     * The unique identifier of the user who provided the feedback.
     */
    private String userId;

    /**
     * A flag indicating whether the feedback belongs to the requesting user.
     * If true, the user is the author of the feedback.
     */
    private boolean ownFeedback;

    /**
     * The timestamp of when the feedback was initially created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp of when the feedback was last modified.
     */
    private LocalDateTime lastModifiedAt;
}
