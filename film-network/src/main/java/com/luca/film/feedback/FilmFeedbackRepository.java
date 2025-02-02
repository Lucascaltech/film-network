package com.luca.film.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmFeedbackRepository extends JpaRepository<FilmFeedback, Integer> {
    Page<FilmFeedback> findByFilm_Id(Integer filmId, Pageable pageable);
}
