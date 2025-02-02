package com.luca.film.film;

import com.luca.film.film.dto.FilmRequest;
import com.luca.film.film.dto.FilmResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.Multipart;
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

    /**
     * Creates a new film.
     *
     * @param filmRequest    the film details provided in the request body
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the created Film's ID and HTTP status
     */
    @PostMapping
    public ResponseEntity<?> createFilm( @RequestBody @Valid FilmRequest filmRequest, Authentication authentication) {
        try {
            Integer filmId = filmService.save(filmRequest, authentication);
            return ResponseEntity.status(HttpStatus.CREATED).body("Film created with ID: " + filmId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the film: " + ex.getMessage());
        }
    }

    /**
     * Retrieves a film by its ID.
     *
     * @param id the ID of the film to retrieve
     * @return a ResponseEntity containing the FilmResponse DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Integer id) {
        try {
            FilmResponse filmResponse = filmService.getById(id);
            return ResponseEntity.ok(filmResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Film not found with ID: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the film: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all films.
     *
     * @param page page number
     * @param  size size of the page
     * @param authenticatedUser authenticated user
     * @return a ResponseEntity containing a list of FilmResponse DTOs
     */
    @GetMapping
    public ResponseEntity<?> getAllFilms(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
            ,@RequestParam(name="size" , defaultValue = "10", required = false) Integer size
            ,Authentication authenticatedUser
    ) {
        try {
            return ResponseEntity.ok(filmService.getAllFilms(page, size, authenticatedUser));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving films: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all films.
     *
     * @param page page number
     * @param  size size of the page
     * @param authenticatedUser authenticated user
     * @return a ResponseEntity containing a list of FilmResponse DTOs
     */
    @GetMapping("/own")
    public ResponseEntity<?> getAllFilmsYouOwn(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
            ,@RequestParam(name="size" , defaultValue = "10", required = false) Integer size
            ,Authentication authenticatedUser
    ) {
        try {
            return ResponseEntity.ok(filmService.getAllFilmsYouOwn(page, size, authenticatedUser));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving films: " + ex.getMessage());
        }
    }

    /**
     * Updates an existing film.
     *
     * @param id             the ID of the film to update
     * @param filmRequest    the updated film details
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the updated FilmResponse DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFilm(@PathVariable Integer id,
                                        @Valid @RequestBody FilmRequest filmRequest,
                                        Authentication authentication) {
        try {
            FilmResponse updatedFilm = filmService.update(id, filmRequest, authentication);
            return ResponseEntity.ok(updatedFilm);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Film not found with ID: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the film: " + ex.getMessage());
        }
    }

    /**
     * Deletes a film by its ID.
     *
     * @param id the ID of the film to delete
     * @return a ResponseEntity with HTTP status code 204 (No Content) if deletion is successful
     */
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

    /**
     *
     * @param rented
     * @param page
     * @param size
     * @param authentication
     * @return
     */
    @GetMapping("/user/rented")
    public ResponseEntity<?> getAllRentedFilms(
            @RequestParam(value = "rented") boolean rented,
             @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name="size" , defaultValue = "10", required = false) Integer size,
            Authentication authentication
    )
    {
         try {
            return ResponseEntity.ok(filmService.getAllRentedFilms(page, size, authentication, rented));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving films: " + ex.getMessage());
        }
    }

    /**
     *
     * @param filmId
     * @param authentication
     * @return
     */
    @PatchMapping("/archived/{filmId}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication
    ){
        return ResponseEntity.ok(filmService.toggleArchiveStatus(filmId, authentication));
    }

    /**
     *
     * @param filmId
     * @param authentication
     * @return
     */
    @PostMapping("/rent/{filmId}")
    public ResponseEntity<Integer> rentFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication
    ){
        return ResponseEntity.ok(filmService.rentFilm(filmId, authentication));
    }

    @PatchMapping("/rent/return/{filmId}")
    public ResponseEntity<Integer> returnRentedFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication
    ){
        return ResponseEntity.ok(filmService.returnFilm(filmId, authentication));
    }

    @PatchMapping("/rent/approve/{filmId}")
    public ResponseEntity<Integer> approveRentedFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication
    ){
        return  ResponseEntity.ok(filmService.approveReturnFilm(filmId, authentication));
    }

    @PostMapping(value = "/poster/{filmId}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadPoster(
            @PathVariable("filmId") Integer filmId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication authentication
            ){
        filmService.uploadFilmPoster(file, filmId, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body("The film poster is created");
    }
}
