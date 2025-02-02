package com.luca.film.film;

import com.luca.film.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    @Query("""
           select film from Film film 
           where film.archive = false 
             AND film.addedBy.id != :userId
           """)
    Page<Film> findAll(Pageable pageable, Integer userId);

    Page<Film> findAllByAddedBy(User addedBy, Pageable pageable);
}
