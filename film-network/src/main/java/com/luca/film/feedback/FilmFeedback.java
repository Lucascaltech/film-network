package com.luca.film.feedback;

import com.luca.film.film.Film;
import com.luca.film.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entity representing user feedback for a film.
 * This includes a rating, a textual review, and references to the related film and user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FilmFeedback extends BaseEntity {

    /**
     * The rating given to the film by the user.
     * Must be between 0 and 5.
     */
    @Min(0)
    @Max(5)
    private Double rating;  // 0-5 stars

    /**
     * The textual review provided by the user about the film.
     */
    private String review;

    /**
     * The film that this feedback is associated with.
     * This establishes a many-to-one relationship with the Film entity.
     */
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    /**
     * The unique identifier of the user who provided the feedback.
     */
    @Column
    private String userid;
}
