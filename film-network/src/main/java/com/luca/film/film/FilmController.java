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

    /**
     * Creates a new film.
     *
     * @param filmRequest    the film details provided in the request body
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the created Film's ID and HTTP status
     */
    @PostMapping
    public ResponseEntity<Integer> createFilm( @RequestBody @Valid FilmRequest filmRequest, Authentication authentication) {
            Integer filmId = filmService.save(filmRequest, authentication);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmId);
    }

    /**
     * Retrieves a film by its ID.
     *
     * @param id the ID of the film to retrieve
     * @return a ResponseEntity containing the FilmResponse DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> getFilmById(@PathVariable Integer id) {
            FilmResponse filmResponse = filmService.getById(id);
            return ResponseEntity.ok(filmResponse);

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
    public ResponseEntity<PageResponse<FilmResponse>> getAllFilms(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
            ,@RequestParam(name="size" , defaultValue = "10", required = false) Integer size
            ,Authentication authenticatedUser
    ) {
            return ResponseEntity.ok(filmService.getAllFilms(page, size, authenticatedUser));
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
    public ResponseEntity<PageResponse<FilmResponse>> getAllFilmsYouOwn(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page
            ,@RequestParam(name="size" , defaultValue = "10", required = false) Integer size
            ,Authentication authenticatedUser
    ) {
            return ResponseEntity.ok(filmService.getAllFilmsYouOwn(page, size, authenticatedUser));
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
    public ResponseEntity<FilmResponse> updateFilm(@PathVariable Integer id,
                                        @Valid @RequestBody FilmRequest filmRequest,
                                        Authentication authentication) {
            FilmResponse updatedFilm = filmService.update(id, filmRequest, authentication);
            return ResponseEntity.ok(updatedFilm);
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
     * Retrieves all films that are rented or not rented.
     *
     * @param rented the rented status of the films to retrieve
     * @param page the page number
     * @param size the size of the page
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing a list of FilmResponse DTOs
     */
    @GetMapping("/user/rented")
    public ResponseEntity<PageResponse<RentedFilmResponse>> getAllRentedFilms(
            @RequestParam(value = "rented") boolean rented,
             @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name="size" , defaultValue = "10", required = false) Integer size,
            Authentication authentication
    )
    {

            return ResponseEntity.ok(filmService.getAllRentedFilms(page, size, authentication, rented));
    }

    /**
     * Retrieves all films that are rented or not rented.
     *
     * @param filmId the film ID
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing a list of FilmResponse DTOs
     */
    @PatchMapping("/archived/{filmId}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication
    ){
        return ResponseEntity.ok(filmService.toggleArchiveStatus(filmId, authentication));
    }

    /**
     * Rents a film.
     *
     * @param filmId the ID of the film to rent
     * @param authentication  the authentication object representing the current user
     * @return  a ResponseEntity containing the updated FilmResponse DTO
     */
    @PostMapping("/rent/{filmId}")
    public ResponseEntity<Integer> rentFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication
    ){
        return ResponseEntity.ok(filmService.rentFilm(filmId, authentication));
    }

    /**
     * Returns a rented film.
     * @param filmId the ID of the film to return
     * @param authentication the authentication object representing the current user
     * @return a ResponseEntity containing the updated FilmResponse DTO
     */
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
    public ResponseEntity<Integer> uploadPoster(
            @PathVariable("filmId") Integer filmId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication authentication
            ){
        filmService.uploadFilmPoster(file, filmId, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(filmId);
    }
}
