import {Component, OnInit} from '@angular/core';
import { NgForOf, NgIf} from "@angular/common";
import {SnackbarComponent} from "../../../../common/components/snackbar/snackbar.component";
import {FilmResponse} from "../../../../services/models/film-response";
import {FilmService} from "../../../../services/services/film.service";
import {Router} from "@angular/router";
import {PageResponseRentedFilmResponse} from "../../../../services/models/page-response-rented-film-response";
import {RentedFilmResponse} from "../../../../services/models/rented-film-response";

@Component({
  selector: 'app-returned-films',
  standalone: true,
  imports: [

    NgForOf,
    NgIf,
    SnackbarComponent,
  ],
  templateUrl: './returned-films.component.html',
  styleUrl: './returned-films.component.scss'
})
export class ReturnedFilmsComponent  implements OnInit {
  page: number = 0;
  size: number = 10;
  filmResponse: PageResponseRentedFilmResponse = {};
  pageIndex: number = 0;
  message: string = '';
  level: 'success' | 'danger' = 'success';
  // Flag to control modal visibility
  showAddFilmModal: boolean = false;
  // Variable to hold the film to edit (if any)
  editingFilm: FilmResponse | null = null;

  constructor(
    private readonly filmService: FilmService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.findAllRentedFilms();
  }

  private findAllRentedFilms(): void {
    this.filmService.getReturnedFilmsForOwner({ page: this.page, size: this.size }).subscribe({
      next: (films) => {
        console.log(films);
        this.filmResponse = films;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  onDeleteFilm(film: FilmResponse): void {
    console.log("On Delete Button is clicked the deleted film is :", film);
    this.filmService.deleteFilm(
      {id:film.id as number}
    ).subscribe({
      next: (response) => {
        console.log("Film Deleted Successfully");
        this.message = 'Film Deleted Successfully';
        this.level = 'success';
        this.findAllRentedFilms();
      },
      error: (error) => {
        console.log(error);
        this.message = error.error.errorMessage;
        this.level = 'danger';
      }
    })

  }

  onReturnFilm(film: FilmResponse) {
    console.log("Returned ")
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

  onReturnApprove(film: RentedFilmResponse) {
    console.log("Return Approved Film : ", film)
    this.filmService.approveRentedFilm({
      filmId:film.id!
    }).subscribe({
      next:() =>{
        this.message = "Approved"
        this.level = "success"
        this.findAllRentedFilms()
      },
      error: (error)=>{
        this.message ="Error Occured"
        this.level = "danger"
    }
    })
  }

}
