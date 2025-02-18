package com.luca.film.film;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Film} entities.
 * <p>
 * This interface provides methods for retrieving films from the database,
 * including filtering based on ownership and archive status.
 * </p>
 */
@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    /**
     * Retrieves a paginated list of all films that are not archived and were not created by the given user.
     * <p>
     * This method excludes films created by the requesting user, ensuring that only available
     * films from other users are displayed.
     * </p>
     *
     * @param pageable The pagination information (page number, size, sorting).
     * @param userId   The ID of the currently authenticated user, used to exclude their own films.
     * @return A {@link Page} containing films that are not archived and not owned by the given user.
     */
    @Query("""
           SELECT film FROM Film film
           WHERE film.archive = false
             AND film.createdBy != :userId
           """)
    Page<Film> findAll(Pageable pageable, String userId);

    /**
     * Retrieves a paginated list of all films created by a specific user.
     * <p>
     * This method allows users to view the films they have uploaded.
     * </p>
     *
     * @param createdBy The ID of the user who created the films.
     * @param pageable  The pagination information (page number, size, sorting).
     * @return A {@link Page} containing films uploaded by the specified user.
     */
    Page<Film> findAllByCreatedBy(String createdBy, Pageable pageable);

}
