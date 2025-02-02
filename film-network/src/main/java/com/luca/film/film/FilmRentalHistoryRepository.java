package com.luca.film.film;

import com.luca.film.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRentalHistoryRepository extends JpaRepository<FilmRentalHistory, Integer> {
    Page<FilmRentalHistory> findAllByReturnedAndUser(boolean returned,User user, Pageable pageable);

    @Query("""
        SELECT 
                (COUNT (*) > 0) AS isRented
                        FROM FilmRentalHistory filmRentalHistory
                                WHERE filmRentalHistory.user.id = :userId
                                        AND filmRentalHistory.film.id = :filmId
        """)
    boolean isAlreadyRentedByUser(Integer filmId, Integer userId);

    Optional<FilmRentalHistory> findByFilmAndUserAndReturnedAndReturnedApproved(Film film, User user, boolean returned, boolean returnedApproved);
}
