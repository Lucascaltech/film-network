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

    public Integer save(FilmRequest filmRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Film film = filmMapper.toFilm(filmRequest);
        film.setAddedBy(user);
        return filmRepository.save(film).getId();
    }

    public FilmResponse getById(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        return filmMapper.toFilmResponse(film);
    }

    public List<FilmResponse> getAll() {
        return filmRepository.findAll()
                .stream()
                .map(filmMapper::toFilmResponse)
                .toList();
    }

    public PageResponse<FilmResponse> getAllFilms(Integer page, Integer size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Film> films = filmRepository.findAll(pageable, user.getId());
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

    public FilmResponse update(Integer id, FilmRequest filmRequest, Authentication authentication) {
        Film existingFilm = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        Film updatedFilm = filmMapper.toFilm(filmRequest);
        updatedFilm.setId(existingFilm.getId());
        updatedFilm.setFilmPoster(existingFilm.getFilmPoster());
        updatedFilm.setAddedBy(existingFilm.getAddedBy());
        Film savedFilm = filmRepository.save(updatedFilm);
        return filmMapper.toFilmResponse(savedFilm);
    }

    public void delete(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + id));
        filmRepository.delete(film);
    }

    public PageResponse<FilmResponse> getAllFilmsYouOwn(Integer page, Integer size, Authentication authenticatedUser) {
        User user = (User) authenticatedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Film> films = filmRepository.findAllByAddedBy(user, pageable);
        List<FilmResponse> responses = films.stream().map(filmMapper::toFilmResponse).toList();
        return new PageResponse<>(
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
     * Retrieves a paginated list of films that the current user has borrowed.
     *
     * @param page           the page number (0-based)
     * @param size           the page size (number of records per page)
     * @param authentication the authentication object representing the current user (borrower)
     * @return a paginated list of RentedFilmResponse DTOs for borrowed films
     */
    public PageResponse<RentedFilmResponse> getBorrowedFilms(int page, int size, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("rentalDate").descending());
        Page<FilmRentalHistory> borrowedRecords = filmRentalHistoryRepository.findAllByUser(user, pageable);
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
        User owner = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("returnDate").descending());
        Page<FilmRentalHistory> returnedRecords = filmRentalHistoryRepository.findAllByFilm_AddedBy_IdAndReturnedTrue(owner.getId(), pageable);
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

    public Integer toggleArchiveStatus(Integer filmId, Authentication authentication) throws OperationNotPermittedException {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with ID: " + filmId));

        if (!Objects.equals(film.getAddedBy(), authentication.getPrincipal())) {
            throw new OperationNotPermittedException("You cannot update another user's film archive status");
        }

        film.setArchive(!film.isArchive());
        filmRepository.save(film);
        return film.getId();
    }

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

    public Integer approveReturnFilm(Integer filmId, Authentication authentication) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("No film found with the given id: " + filmId));

        if (film.isArchive()) {
            throw new OperationNotPermittedException("The requested film cannot have its return approved because it is archived.");
        }

        User user = (User) authentication.getPrincipal();
        // Only the owner of the film can approve its return.
        if (user.getId() != film.getAddedBy().getId() ) {
            throw new OperationNotPermittedException("Only the film owner can approve the return of this film.");
        }

        // Find the rental record for this film that has been returned (returned == true)
        // but not yet approved (returnedApproved == false). Note that the borrower is not the owner.
        FilmRentalHistory rentalRecord = filmRentalHistoryRepository.findByFilmAndReturnedAndReturnedApproved(
                film, true, false
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
