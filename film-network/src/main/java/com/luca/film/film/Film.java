package com.luca.film.film;

import com.luca.film.common.BaseEntity;
import com.luca.film.feedback.FilmFeedback;
import com.luca.film.film.enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

/**
 * Entity representing a film.
 * This class contains metadata about a film, including its title, director, synopsis, rating,
 * and related feedback and rental history records.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Film extends BaseEntity {

    /**
     * The title of the film.
     */
    private String title;

    /**
     * The name of the film's director.
     */
    private String director;

    /**
     * The IMDb unique identifier for the film.
     */
    private String imdbId;

    /**
     * A brief synopsis of the film's plot.
     */
    private String synopsis;

    /**
     * URL or file path to the film's poster image.
     */
    private String filmPoster;

    /**
     * The release year of the film.
     */
    private String year;

    /**
     * Indicates whether the film is archived.
     * If true, the film is not available for rental or feedback.
     */
    private boolean archive;

    /**
     * The average user rating of the film, calculated from feedback.
     * Ratings range from 0 to 5.
     */
    private Double rating;

    /**
     * The genre of the film, stored as an enumerated value.
     */
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /**
     * List of user feedback associated with this film.
     * Mapped by the `film` field in the {@link FilmFeedback} entity.
     */
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmFeedback> feedbacks;

    /**
     * List of rental history records for this film.
     * Mapped by the `film` field in the {@link FilmRentalHistory} entity.
     */
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmRentalHistory> histories;

}
