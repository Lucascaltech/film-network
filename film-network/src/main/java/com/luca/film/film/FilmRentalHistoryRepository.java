package com.luca.film.film;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing film rental history.
 * <p>
 * This interface provides methods to track film rentals, check rental status,
 * and retrieve rental history records.
 * </p>
 */
@Repository
public interface FilmRentalHistoryRepository extends JpaRepository<FilmRentalHistory, Integer> {

    /**
     * Checks if a user has already rented a specific film.
     *
     * @param filmId The ID of the film to check.
     * @param userId The ID of the user who may have rented the film.
     * @return {@code true} if the user has already rented the film, {@code false} otherwise.
     */
    @Query("""
        SELECT
            (COUNT(*) > 0) AS isRented
        FROM FilmRentalHistory filmRentalHistory
        WHERE filmRentalHistory.userId = :userId
          AND filmRentalHistory.film.id = :filmId
        """)
    boolean isAlreadyRentedByUser(@Param("filmId") Integer filmId, @Param("userId") String userId);

    /**
     * Checks if a film is currently rented and has not yet been returned or approved for return.
     *
     * @param filmId The ID of the film to check.
     * @return {@code true} if the film is currently rented and not yet approved for return, {@code false} otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(frh) > 0 THEN true ELSE false END " +
           "FROM FilmRentalHistory frh " +
           "WHERE frh.film.id = :filmId " +
           "AND frh.returned = false " +
           "AND frh.returnedApproved = false")
    boolean isAlreadyRented(@Param("filmId") Integer filmId);

    /**
     * Finds a rental record for a specific film rented by a specific user that is not yet returned or approved.
     *
     * @param film             The film being checked.
     * @param userId           The user who rented the film.
     * @param returned         Whether the film has been marked as returned.
     * @param returnedApproved Whether the return has been approved.
     * @return An {@link Optional} containing the rental record if found, otherwise empty.
     */
    Optional<FilmRentalHistory> findByFilmAndCreatedByAndReturnedAndReturnedApproved(
            Film film, String userId, boolean returned, boolean returnedApproved);

    /**
     * Retrieves all rental records where the film was added by a specific user.
     *
     * @param filmAddedBy The ID of the user who added the film.
     * @return An {@link Optional} containing the rental records if found, otherwise empty.
     */
    Optional<FilmRentalHistory> findAllByFilm_CreatedBy(String filmAddedBy);

    /**
     * Retrieves a paginated list of all rental records where the given user is the borrower.
     *
     * @param user     The ID of the borrower.
     * @param pageable The pagination details.
     * @return A paginated list of rental history records for the given user.
     */
    Page<FilmRentalHistory> findAllByCreatedBy(String user, Pageable pageable);

    /**
     * Retrieves a paginated list of all rental records for films uploaded by a specific owner
     * where the films have been returned.
     *
     * @param ownerId  The ID of the film owner.
     * @param pageable The pagination details.
     * @return A paginated list of rental history records for returned films.
     */
    Page<FilmRentalHistory> findAllByFilm_CreatedByAndReturnedTrue(String ownerId, Pageable pageable);

    /**
     * Finds the rental record for a film that has been returned but not yet approved for return.
     *
     * @param film            The film being checked.
     * @param returned        Whether the film has been marked as returned.
     * @param returnedApproved Whether the return has been approved.
     * @return An {@link Optional} containing the rental record if found, otherwise empty.
     */
    Optional<FilmRentalHistory> findByFilmAndReturnedAndReturnedApproved(
            Film film, boolean returned, boolean returnedApproved);
}
