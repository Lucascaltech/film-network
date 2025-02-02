package com.luca.film.film;

import com.luca.film.common.PageResponse;
import com.luca.film.file.FileStorageService;
import com.luca.film.film.dto.FilmRequest;
import com.luca.film.film.dto.FilmResponse;
import com.luca.film.film.dto.RentedFilmResponse;
import com.luca.film.film.exceptions.OperationNotPermittedException;
import com.luca.film.film.mapper.FilmMapper;
import com.luca.film.film.mapper.FilmRentalHistoryMapper;
import com.luca.film.user.User;
import jakarta.mail.Multipart;
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

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;
    private final FilmRentalHistoryRepository filmRentalHistoryRepository;
    private final FilmRentalHistoryMapper filmRentalHistoryMapper;
    private final FileStorageService fileStorageService;

    /**
     * Creates a new Film and returns its ID.
     *
     * @param filmRequest    the film details from the request
     * @param authentication the authentication object representing the current user
     * @return the ID of the newly created Film
     */
    public Integer save(FilmRequest filmRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Film film = filmMapper.toFilm(filmRequest);
        film.setAddedBy(user);
        return filmRepository.save(film).getId();
    }

    /**
     * Retrieves a Film by its ID and maps it to a FilmResponse.
     *
     * @param id the ID of the film to retrieve
     * @return the FilmResponse DTO
     */
    public FilmResponse getById(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        return filmMapper.toFilmResponse(film);
    }

    /**
     * Retrieves all films and maps them to a list of FilmResponse DTOs.
     *
     * @return a list of FilmResponse DTOs
     */
    public List<FilmResponse> getAll() {
        return filmRepository.findAll()
                .stream()
                .map(filmMapper::toFilmResponse)
                .toList();
    }


    public PageResponse<FilmResponse> getAllFilms(Integer page, Integer size, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Film> films  = filmRepository.findAll(pageable, user.getId());
        List<FilmResponse> filmsResponse = films.stream().map(filmMapper::toFilmResponse).toList();
        return new PageResponse<>(
                filmsResponse,
                films.getNumber(),
                films.getSize(),
                films.getTotalElements(),
                films.getTotalPages(),
                films.isFirst(),
                films.isLast()
        );

    }

    /**
     * Updates an existing film.
     *
     * @param id             the ID of the film to update
     * @param filmRequest    the updated film details
     * @param authentication the authentication object representing the current user
     * @return the updated FilmResponse DTO
     */
    public FilmResponse update(Integer id, FilmRequest filmRequest, Authentication authentication) {
        Film existingFilm = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        Film updatedFilm = filmMapper.toFilm(filmRequest);
        updatedFilm.setId(existingFilm.getId());
        updatedFilm.setAddedBy(existingFilm.getAddedBy());
        Film savedFilm = filmRepository.save(updatedFilm);
        return filmMapper.toFilmResponse(savedFilm);
    }

    /**
     * Deletes a film by its ID.
     *
     * @param id the ID of the film to delete
     */
    public void delete(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        filmRepository.delete(film);
    }

    public PageResponse<FilmResponse> getAllFilmsYouOwn(Integer page, Integer size, Authentication authenticatedUser){
        User user = (User) authenticatedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
         Page<Film> films  = filmRepository.findAllByAddedBy(user, pageable);
         List<FilmResponse> responses = films.stream().map(filmMapper::toFilmResponse).toList();
        return  new PageResponse<>(
                responses,
                films.getNumber(),
                films.getSize(),
                films.getTotalElements(),
                films.getTotalPages(),
                films.isFirst(),
                films.isLast()
        );
    }

    /**
     * Retrieves a paginated list of films that the connected user has rented.
     *
     * @param page           the page number (0-based)
     * @param size           the page size (number of records per page)
     * @param authentication the authentication object representing the current user
     * @return a paginated list of RentedFilmResponse DTOs
     */
    public PageResponse<RentedFilmResponse> getAllRentedFilms(int page, int size, Authentication authentication, boolean rented) {

        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<FilmRentalHistory> allRentedFilms = filmRentalHistoryRepository.findAllByReturnedAndUser(rented,user, pageable);
        List<RentedFilmResponse> response = allRentedFilms.stream().map(filmRentalHistoryMapper::toRentedFilmResponse).toList();

        return new PageResponse<>(
                response,
                allRentedFilms.getNumber(),
                allRentedFilms.getSize(),
                allRentedFilms.getTotalElements(),
                allRentedFilms.getTotalPages(),
                allRentedFilms.isFirst(),
                allRentedFilms.isLast()
        );
    }

    /**
     * Toggles the archive status of a film.
     *
     * @param filmId         the ID of the film whose archive status is to be toggled
     * @param authentication the authentication object representing the current user
     * @return the ID of the film after toggling its archive status
     * @throws EntityNotFoundException       if no film is found with the provided filmId
     * @throws OperationNotPermittedException if the connected user is not allowed to modify the film
     */
    public Integer toggleArchiveStatus(Integer filmId, Authentication authentication) throws OperationNotPermittedException {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with ID: " + filmId));

        if (!Objects.equals(film.getAddedBy(), authentication.getPrincipal())) {
            throw new OperationNotPermittedException("You cannot update another user's film archive status");
        }

        // Toggle the archive status.
        film.setArchive(!film.isArchive());
        filmRepository.save(film);
        return film.getId();
    }


     /**
     * Rents a film for the current user.
     *
     * @param filmId         the identifier of the film to rent
     * @param authentication the current authenticated user
     * @return the ID of the newly created rental history record
     * @throws EntityNotFoundException       if no film is found with the given ID
     * @throws OperationNotPermittedException if the film is archived, belongs to the user, or is already rented
     */
    public Integer rentFilm(Integer filmId, Authentication authentication) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

        if (film.isArchive()) {
            throw new OperationNotPermittedException("The requested film cannot be rented because it is archived.");
        }

        User user = (User) authentication.getPrincipal();
        if (Objects.equals(film.getAddedBy(), user)) {
            throw new OperationNotPermittedException("You cannot rent your own film.");
        }

        boolean isAlreadyRentedByUser = filmRentalHistoryRepository.isAlreadyRentedByUser(filmId, user.getId());
        if (isAlreadyRentedByUser) {
            throw new OperationNotPermittedException("You have already rented this film.");
        }

        FilmRentalHistory rentalRecord = FilmRentalHistory.builder()
                .returnedApproved(false)
                .returned(false)
                .rentalDate(LocalDateTime.now())
                .film(film)
                .user(user)
                .rentalPrice(10) // adjust price as needed
                .build();

        return filmRentalHistoryRepository.save(rentalRecord).getId();
    }

    /**
     * Marks a rented film as returned.
     *
     * @param filmId         the identifier of the film to return
     * @param authentication the current authenticated user
     * @return the ID of the updated rental history record
     * @throws EntityNotFoundException       if no film is found with the given ID
     * @throws OperationNotPermittedException if the film is archived, belongs to the user, or no active rental exists
     */
    public Integer returnFilm(Integer filmId, Authentication authentication) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

        if (film.isArchive()) {
            throw new OperationNotPermittedException("The requested film cannot be returned because it is archived.");
        }

        User user = (User) authentication.getPrincipal();
        if (Objects.equals(film.getAddedBy(), user)) {
            throw new OperationNotPermittedException("You cannot return your own film.");
        }

        FilmRentalHistory rentalRecord = filmRentalHistoryRepository.findByFilmAndUserAndReturnedAndReturnedApproved(
                film, user, false, false
        ).orElseThrow(() -> new OperationNotPermittedException("No active rental found for this film."));

        rentalRecord.setReturned(true);
        return filmRentalHistoryRepository.save(rentalRecord).getId();
    }

    /**
     * Approves the return of a rented film.
     *
     * @param filmId         the identifier of the film for which to approve the return
     * @param authentication the current authenticated user (must be the film owner)
     * @return the ID of the updated rental history record
     * @throws EntityNotFoundException       if no film is found with the given ID
     * @throws OperationNotPermittedException if the current user is not the film owner or if no pending return is found
     */
    public Integer approveReturnFilm(Integer filmId, Authentication authentication) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

        if (film.isArchive()) {
            throw new OperationNotPermittedException("The requested film cannot have its return approved because it is archived.");
        }

        User user = (User) authentication.getPrincipal();
        // Only the owner of the film can approve its return.
        if (!Objects.equals(film.getAddedBy(), user)) {
            throw new OperationNotPermittedException("Only the film owner can approve the return of this film.");
        }

        // Find the rental record where the film has been returned but not yet approved.
        FilmRentalHistory rentalRecord = filmRentalHistoryRepository.findByFilmAndUserAndReturnedAndReturnedApproved(
                film, user, true, false
        ).orElseThrow(() -> new OperationNotPermittedException("No pending return found for this film."));

        rentalRecord.setReturnedApproved(true);
        rentalRecord.setReturnDate(LocalDateTime.now());
        return filmRentalHistoryRepository.save(rentalRecord).getId();
    }

    public void uploadFilmPoster(MultipartFile file, Integer filmId, Authentication authentication) {
         Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

         User user = (User) authentication.getPrincipal();
         String poster = (String) fileStorageService.saveFile(file, user.getId());
         film.setFilmPoster(poster);
         filmRepository.save(film);
    }
}
