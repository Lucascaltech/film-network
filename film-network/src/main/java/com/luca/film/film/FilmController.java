package com.luca.film.film;

import com.luca.film.common.PageResponse;
import com.luca.film.film.dto.FilmRequest;
import com.luca.film.film.dto.FilmResponse;
import com.luca.film.film.dto.RentedFilmResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing Film entities.
 */
@RestController
@RequestMapping("films")
@RequiredArgsConstructor
@Tag(name = "Film", description = "The Film API for CRUD operations")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public ResponseEntity<Integer> createFilm(@RequestBody @Valid FilmRequest filmRequest, Authentication authentication) {
        Integer filmId = filmService.save(filmRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(filmId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> getFilmById(@PathVariable Integer id) {
        FilmResponse filmResponse = filmService.getById(id);
        return ResponseEntity.ok(filmResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<FilmResponse>> getAllFilms(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication authenticatedUser) {
        return ResponseEntity.ok(filmService.getAllFilms(page, size, authenticatedUser));
    }

    @GetMapping("/own")
    public ResponseEntity<PageResponse<FilmResponse>> getAllFilmsYouOwn(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication authenticatedUser) {
        return ResponseEntity.ok(filmService.getAllFilmsYouOwn(page, size, authenticatedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmResponse> updateFilm(@PathVariable Integer id,
                                                     @Valid @RequestBody FilmRequest filmRequest,
                                                     Authentication authentication) {
        FilmResponse updatedFilm = filmService.update(id, filmRequest, authentication);
        return ResponseEntity.ok(updatedFilm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable Integer id) {
        try {
            filmService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Film not found with ID: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the film: " + ex.getMessage());
        }
    }

//    @GetMapping("/user/rented")
//    public ResponseEntity<PageResponse<RentedFilmResponse>> getAllRentedFilms(
//            @RequestParam(value = "rented") boolean rented,
//            @RequestParam(value = "approved") boolean approved,
//            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
//            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
//            Authentication authentication) {
//        return ResponseEntity.ok(filmService.get(page, size, authentication, rented, approved));
//    }

    // NEW ENDPOINT: Get all films borrowed by the current user.
    @GetMapping("/user/borrowed")
    public ResponseEntity<PageResponse<RentedFilmResponse>> getBorrowedFilms(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.getBorrowedFilms(page, size, authentication));
    }

    // NEW ENDPOINT: Get all films (rental records) returned for films owned by the current user.
    @GetMapping("/owner/returned")
    public ResponseEntity<PageResponse<RentedFilmResponse>> getReturnedFilmsForOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.getReturnedFilmsForOwner(page, size, authentication));
    }

    @PatchMapping("/archived/{filmId}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.toggleArchiveStatus(filmId, authentication));
    }

    @PostMapping("/rent/{filmId}")
    public ResponseEntity<Integer> rentFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.rentFilm(filmId, authentication));
    }

    @PatchMapping("/rent/return/{filmId}")
    public ResponseEntity<Integer> returnRentedFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.returnFilm(filmId, authentication));
    }

    @PatchMapping("/rent/approve/{filmId}")
    public ResponseEntity<Integer> approveRentedFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.approveReturnFilm(filmId, authentication));
    }

    @PostMapping(value = "/poster/{filmId}", consumes = "multipart/form-data")
    public ResponseEntity<Integer> uploadPoster(
            @PathVariable("filmId") Integer filmId,
            @Parameter @RequestPart("file") MultipartFile file,
            Authentication authentication) {
        filmService.uploadFilmPoster(file, filmId, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(filmId);
    }
}
