package com.luca.film.film;

import com.luca.film.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRentalHistoryRepository extends JpaRepository<FilmRentalHistory, Integer> {

    @Query("SELECT f FROM FilmRentalHistory f " +
           "WHERE f.user = :user " +
           "AND (f.returned = :returned OR f.returnedApproved = :returnedApproved)")
    Page<FilmRentalHistory> findAllByUserAndReturnedOrReturnedApproved(
            @Param("user") User user,
            @Param("returned") boolean returned,
            @Param("returnedApproved") boolean returnedApproved,
            Pageable pageable);

    @Query("""
        SELECT 
            (COUNT(*) > 0) AS isRented
        FROM FilmRentalHistory filmRentalHistory
        WHERE filmRentalHistory.user.id = :userId
          AND filmRentalHistory.film.id = :filmId
        """)
    boolean isAlreadyRentedByUser(Integer filmId, Integer userId);

    Optional<FilmRentalHistory> findByFilmAndUserAndReturnedAndReturnedApproved(
            Film film, User user, boolean returned, boolean returnedApproved);

    Optional<FilmRentalHistory> findAllByFilm_AddedBy(User filmAddedBy);

    // NEW METHOD: Retrieve all rental records where the borrower is the given user.
    Page<FilmRentalHistory> findAllByUser(User user, Pageable pageable);

    // NEW METHOD: Retrieve all rental records for films uploaded by the given owner (by ID) that have been returned.
    Page<FilmRentalHistory> findAllByFilm_AddedBy_IdAndReturnedTrue(Integer ownerId, Pageable pageable);

    // NEW METHOD for approval:
    // Finds the rental record for a film that has been returned (returned == true)
    // but not yet approved (returnedApproved == false), regardless of borrower.
    Optional<FilmRentalHistory> findByFilmAndReturnedAndReturnedApproved(
            Film film, boolean returned, boolean returnedApproved);
}
