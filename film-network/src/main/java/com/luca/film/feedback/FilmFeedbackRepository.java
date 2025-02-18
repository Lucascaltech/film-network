package com.luca.film.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing film feedback records in the database.
 *
 * This repository provides methods to perform CRUD operations and retrieve feedback records
 * based on specific criteria, such as feedback for a particular film.
 */
@Repository
public interface FilmFeedbackRepository extends JpaRepository<FilmFeedback, Integer> {

    /**
     * Retrieves a paginated list of feedback records associated with a specific film.
     *
     * @param filmId   the unique identifier of the film
     * @param pageable the pagination and sorting criteria
     * @return a {@link Page} containing a paginated list of {@link FilmFeedback} records for the specified film
     */
    Page<FilmFeedback> findByFilm_Id(Integer filmId, Pageable pageable);
}
