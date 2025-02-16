package com.luca.film.film;

import com.luca.film.common.BaseEntity;
import com.luca.film.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class FilmRentalHistory extends BaseEntity {


    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private boolean returned;

    @Column(nullable = true)
    private boolean returnedApproved;
    private double rentalPrice;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
}
