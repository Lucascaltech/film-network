import {Component, OnInit} from '@angular/core';
import {AddFilmModalComponent} from "../../components/add-film-modal/add-film-modal.component";
import {NgForOf, NgIf} from "@angular/common";
import {SnackbarComponent} from "../../../../common/components/snackbar/snackbar.component";
import {FilmResponse} from "../../../../services/models/film-response";
import {FilmService} from "../../../../services/services/film.service";
import {Router} from "@angular/router";
import {PageResponseRentedFilmResponse} from "../../../../services/models/page-response-rented-film-response";
import {RentedFilmResponse} from "../../../../services/models/rented-film-response";
import {ReturnFilmModalComponent} from "../../components/return-film-modal/return-film-modal.component";
import {FilmFeedbackRequest} from "../../../../services/models/film-feedback-request";
import {FeedbackService} from "../../../../services/services/feedback.service";

@Component({
  selector: 'app-borrowed-films',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    SnackbarComponent,
    ReturnFilmModalComponent
  ],
  templateUrl: './borrowed-films.component.html',
  styleUrl: './borrowed-films.component.scss'
})
export class BorrowedFilmsComponent implements OnInit {
  page: number = 0;
  size: number = 10;
  filmResponse: PageResponseRentedFilmResponse = {};
  pageIndex: number = 0;
  message: string = '';
  level: 'success' | 'danger' = 'success';
  // Flag to control modal visibility
  showAddFilmModal: boolean = false;
  showReturnModal: boolean = false;
  // Variable to hold the film to edit (if any)

  selectedFilm: RentedFilmResponse = {};
  feedbackRequest: FilmFeedbackRequest = {
    filmId: 0,
    rating: 0,
    review: ''
  }


  constructor(
    private readonly filmService: FilmService,
    private readonly feedbackService: FeedbackService,
    private readonly router: Router
  ) {
  }

  ngOnInit(): void {
    this.findAllRentedFilms();
  }

  private findAllRentedFilms(): void {
    this.filmService.getBorrowedFilms({ page: this.page, size: this.size}).subscribe({
      next: (films) => {
        console.log(films);
        this.filmResponse = films;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  onReturnFilm(film: FilmResponse) {
    console.log("Returned ")
    this.selectedFilm = film;
    console.log(film)
    this.showReturnModal = true;
  }

  onShowFilmDetails(film: FilmResponse): void {
    // Implement further film information display (e.g., navigate to a details page or open a modal)
    console.log('Film details for:', film);
    // For example, you could navigate to a details route:
    // this.router.navigate(['/film-details', film.id]);
  }

  onModalClose(): void {
    this.showAddFilmModal = false;
  }

  goToFirstPage(): void {
    if (this.page !== 0) {
      this.page = 0;
      this.findAllRentedFilms();
    }
  }

  onPreviousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.findAllRentedFilms();
    }
  }

  goToPage(pageIndex: number): void {
    if (pageIndex !== this.page) {
      this.page = pageIndex;
      this.findAllRentedFilms();
    }
  }

  onNextPage(): void {
    if (this.page < (this.filmResponse.totalPages! - 1)) {
      this.page++;
      this.findAllRentedFilms();
    }
  }

  goToLastPage(): void {
    if (this.page !== (this.filmResponse.totalPages! - 1)) {
      this.page = this.filmResponse.totalPages! - 1;
      this.findAllRentedFilms();
    }
  }

  onReturnModalClose(): void {
    this.showReturnModal = false;
  }

  onReturnSubmit(data: { film: RentedFilmResponse; feedback: string; rating: number; }): void {
    console.log('Return submitted for film:', data.film, 'with feedback:', data.feedback);
    this.feedbackRequest.filmId = data.film.id as number;
    this.feedbackRequest.review = data.feedback as string;
    this.feedbackRequest.rating = data.rating as number;

    this.feedbackService.createFeedback({
      body: this.feedbackRequest
    }).subscribe({
      next: (response) => {
        console.log("Feedback created successfully");
        this.message = 'Feedback created successfully';
        this.level = 'success';
        this.showReturnModal = false;
        this.findAllRentedFilms()
      },
      error: (error) => {
        console.log(error);
        this.message = error.error.errorMessage;
        this.level = 'danger';
      }
    })

    // Place your API call or additional logic here to process the return.
  }

  onSubmitReturnOnly(data: { filmId: number }) {
    this.filmService.returnRentedFilm({
      filmId: data.filmId
    }).subscribe({
      next: (response) => {
        this.message = 'Feedback created successfully';
        this.level = 'success';
        this.showReturnModal = false;
        this.findAllRentedFilms()
      },
      error: (error) =>{
        console.log(error);
        this.message = error.error.errorMessage;
        this.level = 'danger';
      }
    })
  }
}
