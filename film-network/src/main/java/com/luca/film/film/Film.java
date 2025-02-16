package com.luca.film.film;

import com.luca.film.common.BaseEntity;
import com.luca.film.feedback.FilmFeedback;
import com.luca.film.film.enums.Genre;
import com.luca.film.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Year;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Film extends BaseEntity {
    private String title;
    private String director;
    private String imdbId;
    private String synopsis;
    private String filmPoster;
    private String year;
    private boolean archive;
    private Double rating;

    @Enumerated(EnumType.STRING)
    private Genre genre;

//    @ManyToOne
//    @JoinColumn(name = "addedBy", nullable = false)
//    private User addedBy;

    @OneToMany(mappedBy = "film")
    private List<FilmFeedback> feedbacks;

    @OneToMany(mappedBy = "film")
    private List<FilmRentalHistory> histories;




}
