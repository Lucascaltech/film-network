package com.luca.film.film;

import com.luca.film.common.PageResponse;
import com.luca.film.file.FileStorageService;
import com.luca.film.film.dto.FilmRequest;
import com.luca.film.film.dto.FilmResponse;
import com.luca.film.film.dto.RentedFilmResponse;
import com.luca.film.film.exceptions.OperationNotPermittedException;
import com.luca.film.film.mapper.FilmMapper;
import com.luca.film.film.mapper.FilmRentalHistoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Service class responsible for handling film-related operations.
 * This includes CRUD operations, renting, returning, and archiving films.
 */
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;
    private final FilmRentalHistoryRepository filmRentalHistoryRepository;
    private final FilmRentalHistoryMapper filmRentalHistoryMapper;
    private final FileStorageService fileStorageService;

    /**
     * Creates and saves a new film.
     *
     * @param filmRequest   DTO containing film details such as title, description, and category.
     * @param authentication The currently authenticated user creating the film.
     * @return The ID of the newly created film.
     */
    public Integer save(FilmRequest filmRequest, Authentication authentication) {

        if (filmRequest.title() ==  null || filmRequest.title().equals("")){
            throw new RuntimeException("Film title should not be empty");
        }

        if (filmRequest.director() == null || filmRequest.director().equals("")){
            throw new RuntimeException("Director should not be empty");
        }

        Film film = filmMapper.toFilm(filmRequest);
        film.setCreatedBy(authentication.getName());
        return filmRepository.save(film).getId();
    }

    /**
     * Retrieves a film by its ID.
     *
     * @param id The unique identifier of the film.
     * @return A FilmResponse DTO with film details.
     * @throws RuntimeException If no film is found with the given ID.
     */
    public FilmResponse getById(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        return filmMapper.toFilmResponse(film);
    }

    /**
     * Retrieves all films in the database.
     *
     * @return A list of FilmResponse DTOs for all films.
     */
    public List<FilmResponse> getAll() {
        return filmRepository.findAll()
                .stream()
                .map(filmMapper::toFilmResponse)
                .toList();
    }

    /**
     * Retrieves a paginated list of all films.
     *
     * @param page           The page number (zero-based index).
     * @param size           The number of records per page.
     * @param authentication The authenticated user requesting the films.
     * @return A PageResponse containing paginated FilmResponse DTOs.
     */
    public PageResponse<FilmResponse> getAllFilms(Integer page, Integer size, Authentication authentication) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Film> films = filmRepository.findAll(pageable, authentication.getName());
        List<FilmResponse> responses = films.stream().map(filmMapper::toFilmResponse).toList();
        return new PageResponse<>(responses, films.getNumber(), films.getSize(),
                films.getTotalElements(), films.getTotalPages(), films.isFirst(), films.isLast());
    }

    /**
     * Retrieves a paginated list of films owned by the authenticated user.
     *
     * @param page            The page number (zero-based index).
     * @param size            The number of records per page.
     * @param authenticatedUser The authenticated user requesting the list.
     * @return A PageResponse containing FilmResponse DTOs of owned films.
     */
    public PageResponse<FilmResponse> getAllFilmsYouOwn(Integer page, Integer size, Authentication authenticatedUser) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Film> films = filmRepository.findAllByCreatedBy(authenticatedUser.getName(), pageable);
        List<FilmResponse> responses = films.stream().map(filmMapper::toFilmResponse).toList();
        return new PageResponse<>(responses, films.getNumber(), films.getSize(),
                films.getTotalElements(), films.getTotalPages(), films.isFirst(), films.isLast());
    }

    /**
     * Updates an existing film.
     *
     * @param id              The ID of the film to update.
     * @param filmRequest     The DTO containing updated film details.
     * @param authentication  The authenticated user making the update.
     * @return The updated FilmResponse DTO.
     * @throws RuntimeException If no film is found with the given ID.
     */
    public FilmResponse update(Integer id, FilmRequest filmRequest, Authentication authentication) {
        Film existingFilm = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        Film updatedFilm = filmMapper.toFilm(filmRequest);
        updatedFilm.setId(existingFilm.getId());
        updatedFilm.setFilmPoster(existingFilm.getFilmPoster());
        updatedFilm.setCreatedBy(existingFilm.getCreatedBy());
        updatedFilm.setLastModifiedBy(authentication.getName());
        return filmMapper.toFilmResponse(filmRepository.save(updatedFilm));
    }

    /**
     * Deletes a film by its ID.
     *
     * @param id The ID of the film to delete.
     * @throws RuntimeException If no film is found with the given ID.
     */
    public void delete(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));

        System.out.println("Is film ");
        filmRepository.delete(film);
    }

    /**
     * Retrieves a paginated list of films that the current user has borrowed.
     *
     * @param page           the page number (0-based)
     * @param size           the page size (number of records per page)
     * @param authentication the authentication object representing the current user (borrower)
     * @return a paginated list of RentedFilmResponse DTOs for borrowed films
     */
    public PageResponse<RentedFilmResponse> getBorrowedFilms(int page, int size, Authentication authentication) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rentalDate").descending());
        Page<FilmRentalHistory> borrowedRecords = filmRentalHistoryRepository.findAllByCreatedBy(authentication.getName(), pageable);
        List<RentedFilmResponse> responses = borrowedRecords
                .stream()
                .map(filmRentalHistoryMapper::toRentedFilmResponse)
                .toList();
        return new PageResponse<>(
                responses,
                borrowedRecords.getNumber(),
                borrowedRecords.getSize(),
                borrowedRecords.getTotalElements(),
                borrowedRecords.getTotalPages(),
                borrowedRecords.isFirst(),
                borrowedRecords.isLast()
        );
    }

    /**
     * Retrieves a paginated list of returned films for which the current user is the owner.
     * This shows the rental records for films uploaded by the user that have been returned by borrowers.
     *
     * @param page           the page number (0-based)
     * @param size           the page size (number of records per page)
     * @param authentication the authentication object representing the current user (film owner)
     * @return a paginated list of RentedFilmResponse DTOs for returned films
     */
    public PageResponse<RentedFilmResponse> getReturnedFilmsForOwner(int page, int size, Authentication authentication) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("returnDate").descending());
        Page<FilmRentalHistory> returnedRecords = filmRentalHistoryRepository.findAllByFilm_CreatedByAndReturnedTrue(authentication.getName(), pageable);
        List<RentedFilmResponse> responses = returnedRecords
                .stream()
                .map(filmRentalHistoryMapper::toRentedFilmResponse)
                .toList();
        return new PageResponse<>(
                responses,
                returnedRecords.getNumber(),
                returnedRecords.getSize(),
                returnedRecords.getTotalElements(),
                returnedRecords.getTotalPages(),
                returnedRecords.isFirst(),
                returnedRecords.isLast()
        );
    }

    /**
     * Toggles the archive status of a film.
     *
     * @param filmId the ID of the film to toggle archive status
     * @param authentication the authenticated user requesting the change
     * @return the ID of the updated film
     * @throws OperationNotPermittedException if the user is not the creator of the film
     */
    public Integer toggleArchiveStatus(Integer filmId, Authentication authentication) throws OperationNotPermittedException {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with ID: " + filmId));

        if (!Objects.equals(film.getCreatedBy(), authentication.getName())) {
            throw new OperationNotPermittedException("You cannot update another user's film archive status");
        }

        film.setArchive(!film.isArchive());
        filmRepository.save(film);
        return film.getId();
    }

    /**
     * Rents a film to the authenticated user.
     *
     * @param filmId the ID of the film to rent
     * @param authentication the authenticated user renting the film
     * @return the rental record ID
     * @throws OperationNotPermittedException if the film is archived, already rented, or belongs to the user
     */
    public Integer rentFilm(Integer filmId, Authentication authentication) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

        if (film.isArchive()) {
            throw new OperationNotPermittedException("The requested film cannot be rented because it is archived.");
        }

        if (Objects.equals(film.getCreatedBy(), authentication.getName())) {
            throw new OperationNotPermittedException("You cannot rent your own film.");
        }

        boolean isRrturedApproved = filmRentalHistoryRepository.isAlreadyRentedByUser(film.getId(), authentication.getName());
        if (isRrturedApproved){
            throw new OperationNotPermittedException("You have returned the film but it not approved yet");
        }


        boolean isAlreadyRented = filmRentalHistoryRepository.isAlreadyRented(film.getId());
        if (isAlreadyRented) {
            throw new OperationNotPermittedException("Currently, the film is not available for rent.");
        }

        FilmRentalHistory rentalRecord = FilmRentalHistory.builder()
                .returnedApproved(false)
                .returned(false)
                .rentalDate(LocalDateTime.now())
                .film(film)
                .userId(authentication.getName())
                .rentalPrice(10)
                .build();

        return filmRentalHistoryRepository.save(rentalRecord).getId();
    }

    /**
     * Marks a rented film as returned by the authenticated user.
     *
     * @param filmId the ID of the film to return
     * @param authentication the authenticated user returning the film
     * @return the rental record ID
     * @throws OperationNotPermittedException if no active rental is found
     */
    public Integer returnFilm(Integer filmId, Authentication authentication) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

        if (film.isArchive()) {
            throw new OperationNotPermittedException("The requested film cannot be returned because it is archived.");
        }

        if (Objects.equals(film.getCreatedBy(), authentication.getName())) {
            throw new OperationNotPermittedException("You cannot return your own film.");
        }

        FilmRentalHistory rentalRecord = filmRentalHistoryRepository.findByFilmAndCreatedByAndReturnedAndReturnedApproved(
                film, authentication.getName(), false, false
        ).orElseThrow(() -> new OperationNotPermittedException("No active rental found for this film."));

        rentalRecord.setReturned(true);
        return filmRentalHistoryRepository.save(rentalRecord).getId();
    }

    /**
     * Approves the return of a rented film by the film owner.
     *
     * @param filmId the ID of the film being returned
     * @param authentication the authenticated owner approving the return
     * @return the rental record ID
     * @throws OperationNotPermittedException if the user is not the owner or no pending return exists
     */
    public Integer approveReturnFilm(Integer filmId, Authentication authentication) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

        if (film.isArchive()) {
            throw new OperationNotPermittedException("The requested film cannot have its return approved because it is archived.");
        }

        if (!Objects.equals(authentication.getName(), film.getCreatedBy()) ) {
            throw new OperationNotPermittedException("Only the film owner can approve the return of this film.");
        }

        FilmRentalHistory rentalRecord = filmRentalHistoryRepository.findByFilmAndReturnedAndReturnedApproved(
                film, true, false
        ).orElseThrow(() -> new OperationNotPermittedException("No pending return found for this film."));

        rentalRecord.setReturnedApproved(true);
        rentalRecord.setReturnDate(LocalDateTime.now());
        return filmRentalHistoryRepository.save(rentalRecord).getId();
    }

    /**
     * Uploads a film poster image.
     *
     * @param file            the image file to upload
     * @param filmId          the ID of the film
     * @param authentication  the authenticated user uploading the poster
     * @throws EntityNotFoundException if the film is not found
     */
    public void uploadFilmPoster(MultipartFile file, Integer filmId, Authentication authentication) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

        String poster = fileStorageService.saveFile(file, authentication.getName());
        film.setFilmPoster(poster);
        filmRepository.save(film);
    }

    /**
 * Searches for films based on title, director, or category.
 *
 * @param query         The search query (title, director, or category).
 * @param page          The page number (zero-based index).
 * @param size          The number of records per page.
 * @return A PageResponse containing paginated FilmResponse DTOs matching the search criteria.
 */
public PageResponse<FilmResponse> searchFilms(String query, int page, int size, Authentication authentication) {


    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    Page<Film> films = filmRepository.searchFilms(query, authentication.getName(),pageable);

    List<FilmResponse> responses = films.stream()
            .map(filmMapper::toFilmResponse)
            .toList();

    return new PageResponse<>(responses, films.getNumber(), films.getSize(),
            films.getTotalElements(), films.getTotalPages(), films.isFirst(), films.isLast());
}
}
