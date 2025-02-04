import {Component, OnInit} from '@angular/core';
import {FilmCardComponent} from "../../components/film-card/film-card.component";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {SnackbarComponent} from "../../../../common/components/snackbar/snackbar.component";
import {PageResponseFilmResponse} from "../../../../services/models/page-response-film-response";
import {FilmService} from "../../../../services/services/film.service";
import {Router} from "@angular/router";
import {FilmResponse} from "../../../../services/models/film-response";
import {AddFilmModalComponent, FilmCreationEvent} from "../../components/add-film-modal/add-film-modal.component";

@Component({
  selector: 'app-my-films',
  standalone: true,
  imports: [
    FilmCardComponent,
    NgForOf,
    NgIf,
    SnackbarComponent,
    NgClass,
    AddFilmModalComponent
  ],
  templateUrl: './my-films.component.html',
  styleUrl: './my-films.component.scss'
})
export class MyFilmsComponent implements OnInit{
page: number = 0;
  size: number = 10;
  filmResponse: PageResponseFilmResponse = {};
  pageIndex: number = 0;
  message: string = '';
  level: 'success' | 'danger' = 'success';
  // Flag to control modal visibility
  showAddFilmModal: boolean = false;

  constructor(
    private readonly filmService: FilmService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.findAllFilms();
  }

  private findAllFilms(): void {
    this.filmService.getAllFilmsYouOwn({ page: this.page, size: this.size }).subscribe({
      next: (films) => {
        console.log(films);
        this.filmResponse = films;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  onPreviousPage(): void {
    this.page--;
    this.findAllFilms();
  }

  onNextPage(): void {
    this.page++;
    this.findAllFilms();
  }

  goToFirstPage(): void {
    this.page = 0;
    this.findAllFilms();
  }

  goToPage(pageIndex: number): void {
    this.page = pageIndex;
    this.findAllFilms();
  }

  goToLastPage(): void {
    this.page = (this.filmResponse.totalPages as number) - 1;
    this.findAllFilms();
  }


  onEditFilm(film: FilmResponse) {
    console.log("On Edit Button is clicked")
  }

  onDeleteFilm(film: FilmResponse) {
    console.log("On Delete Button is clicked")
  }

  // Called when the "Add Film" button is clicked.
  onAddFilm(): void {
    this.showAddFilmModal = true;
  }

  // Called when the modal emits the close event.
  onModalClose(): void {
    this.showAddFilmModal = false;
  }

  onFilmSubmit(filmData: FilmCreationEvent) {
      console.log("On Film Submit is clicked", filmData)
      this.filmService.createFilm({
        body:filmData.film,
      }).subscribe({
        next: (response) => {
          console.log("Film Created Successfully");
          this.message = 'Film Created Successfully';
          this.level = 'success';
          this.filmService.uploadPoster({
            filmId: response,
            body: {file:filmData.filmCoverFile!}
          }).subscribe({
            next: (response) => {
              console.log("Poster Uploaded Successfully");
              this.message = 'Poster Uploaded Successfully';
              this.level = 'success';
              this.showAddFilmModal = false;
              this.findAllFilms();
            },
            error: (error) => {
              console.log(error);
              this.message = error.error.errorMessage;
              this.level = 'danger';
            }
          })
        },
        error: (error) => {
          console.log(error);
          this.message = error.error.errorMessage;
          this.level = 'danger';
        }
      })
  }
}

