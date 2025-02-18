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
 * REST controller for managing Film entities. This class provides endpoints for creating, updating,
 * and deleting films, as well as for retrieving film details and lists of films.
 * <p>
 * The controller also provides endpoints for managing the rental and return of films, as well as
 * for approving returned films.
 * </p>
 * <p>
 * Additionally, the controller provides endpoints for uploading film posters and toggling the
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
     * @param filmRequest the film details
     * @param authentication the authenticated user
     * @return the ID of the created film
     */
    @PostMapping
    public ResponseEntity<Integer> createFilm(@RequestBody @Valid FilmRequest filmRequest, Authentication authentication) {
        Integer filmId = filmService.save(filmRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(filmId);
    }

    /**
     * Retrieves a film by its ID.
     *
     * @param id the ID of the film
     * @return the film details
     */
    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> getFilmById(@PathVariable Integer id) {
        FilmResponse filmResponse = filmService.getById(id);
        return ResponseEntity.ok(filmResponse);
    }

    /**
     * Retrieves all films.
     *
     * @param page the page number
     * @param size the page size
     * @param authenticatedUser the authenticated user
     * @return a paginated list of films
     */
    @GetMapping
    public ResponseEntity<PageResponse<FilmResponse>> getAllFilms(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication authenticatedUser) {
        return ResponseEntity.ok(filmService.getAllFilms(page, size, authenticatedUser));
    }

    /**
     * Retrieves all films owned by the authenticated user.
     *
     * @param page the page number
     * @param size the page size
     * @param authenticatedUser the authenticated user
     * @return a paginated list of owned films
     */
    @GetMapping("/own")
    public ResponseEntity<PageResponse<FilmResponse>> getAllFilmsYouOwn(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication authenticatedUser) {
        return ResponseEntity.ok(filmService.getAllFilmsYouOwn(page, size, authenticatedUser));
    }

    /**
     * Updates an existing film.
     *
     * @param id the ID of the film
     * @param filmRequest the updated film details
     * @param authentication the authenticated user
     * @return the updated film details
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
     * @param id the ID of the film
     * @return a response entity indicating the result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFilm(@PathVariable Integer id) {
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
     * Retrieves films rented by the authenticated user.
     *
     * @param page the page number
     * @param size the page size
     * @param authentication the authenticated user
     * @return a paginated list of rented films
     */
    @GetMapping("/user/borrowed")
    public ResponseEntity<PageResponse<RentedFilmResponse>> getBorrowedFilms(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.getBorrowedFilms(page, size, authentication));
    }

    /**
     * Retrieves returned films for the owner.
     *
     * @param page the page number
     * @param size the page size
     * @param authentication the authenticated user
     * @return a paginated list of returned films
     */
    @GetMapping("/owner/returned")
    public ResponseEntity<PageResponse<RentedFilmResponse>> getReturnedFilmsForOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.getReturnedFilmsForOwner(page, size, authentication));
    }

    /**
     * Updates the archived status of a film. If the film is archived, it will be unarchived, and vice versa.
     * @param filmId the ID of the film to update
     * @param authentication the authenticated user making the request
     * @return the updated archive status of the film
     */
    @PatchMapping("/archived/{filmId}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.toggleArchiveStatus(filmId, authentication));
    }

    /**
     * Rents a film. The authenticated user will be added to the list of renters for the film.
     * @param filmId the ID of the film to rent
     * @param authentication the authenticated user making the request
     * @return the updated number of renters for the film
     */
    @PostMapping("/rent/{filmId}")
    public ResponseEntity<Integer> rentFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.rentFilm(filmId, authentication));
    }

    /**
     * Returns a rented film. The authenticated user will be removed from the list of renters for the film.
     * @param filmId the ID of the film to return from rental
     * @param authentication the authenticated user making the request to return the film
     * @return the updated number of renters for the film
     */
    @PatchMapping("/rent/return/{filmId}")
    public ResponseEntity<Integer> returnRentedFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.returnFilm(filmId, authentication));
    }

    /**
     * Approves the return of a rented film. The film will be removed from the list of rented films.
     * @param filmId the ID of the film to approve for return
     * @param authentication the authenticated user making the request to approve the return
     * @return the updated number of renters for the film
     */
    @PatchMapping("/rent/approve/{filmId}")
    public ResponseEntity<Integer> approveRentedFilm(
            @PathVariable("filmId") Integer filmId,
            Authentication authentication) {
        return ResponseEntity.ok(filmService.approveReturnFilm(filmId, authentication));
    }

    /**
     * Uploads a poster image for a film.
     * @param filmId the ID of the film to upload a poster for
     * @param file the poster image file
     * @param authentication the authenticated user making the request to upload the poster
     * @return the ID of the film that the poster was uploaded for
     */
    @PostMapping(value = "/poster/{filmId}", consumes = "multipart/form-data")
    public ResponseEntity<Integer> uploadPoster(
            @PathVariable("filmId") Integer filmId,
            @Parameter @RequestPart("file") MultipartFile file,
            Authentication authentication) {
        filmService.uploadFilmPoster(file, filmId, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(filmId);
    }
}
