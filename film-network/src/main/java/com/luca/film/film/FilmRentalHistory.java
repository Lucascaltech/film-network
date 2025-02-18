package com.luca.film.film;

import com.luca.film.common.BaseEntity;
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

/**
 * Entity representing the rental history of a film.
 * This records when a film was rented, returned, and whether the return was approved.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class FilmRentalHistory extends BaseEntity {

    /**
     * The date and time when the film was rented.
     */
    private LocalDateTime rentalDate;

    /**
     * The date and time when the film was returned.
     * This may be null if the film has not yet been returned.
     */
    private LocalDateTime returnDate;

    /**
     * Indicates whether the film has been returned.
     * If true, the film has been returned by the renter.
     */
    private boolean returned;

    /**
     * Indicates whether the film's return has been approved by the owner.
     * A returned film must be approved before being marked as fully processed.
     */
    @Column(nullable = true)
    private boolean returnedApproved;

    /**
     * The price paid for renting the film.
     */
    private double rentalPrice;

    /**
     * The ID of the user who rented the film.
     * Stored as a string instead of a foreign key to the User entity.
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * The film that was rented.
     * Many rental records can be associated with the same film.
     */
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
}
