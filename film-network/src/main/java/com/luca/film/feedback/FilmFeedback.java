package com.luca.film.feedback;

import com.luca.film.film.Film;
import com.luca.film.common.BaseEntity;
import com.luca.film.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FilmFeedback extends BaseEntity {

    @Min(0)
    @Max(5)
    private Double rating;  // 0-5 stars
    private String review;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @Column
    private String userid;

}
