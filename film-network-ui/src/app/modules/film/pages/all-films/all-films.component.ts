import { Component, OnInit } from '@angular/core';
import { FilmService } from '../../../../services/services/film.service';
import { Router } from '@angular/router';
import { PageResponseFilmResponse } from '../../../../services/models/page-response-film-response';
import { FilmResponse } from '../../../../services/models/film-response';
import { NgClass, NgForOf, NgIf } from '@angular/common';
import { FilmCardComponent } from '../../components/film-card/film-card.component';
import {SnackbarComponent} from "../../../../common/components/snackbar/snackbar.component";
import {ViewFilmModalComponent} from "../../components/film-info-modal/film-info-modal.component";

@Component({
  selector: 'app-all-films',
  standalone: true,
  imports: [
    NgForOf,
    FilmCardComponent,
    NgClass,
    NgIf,
    SnackbarComponent,
    ViewFilmModalComponent,
  ],
  templateUrl: './all-films.component.html',
  styleUrls: ['./all-films.component.scss']  // Correct plural form
})
export class AllFilmsComponent implements OnInit {
  page: number = 0;
  size: number = 10;
  filmResponse: PageResponseFilmResponse = {};
  pageIndex: number = 0;
  message: string = '';
  level: 'success' | 'danger' = 'success';
  showViewFilmModal: boolean =false;
  selectedFilm: FilmResponse = {};

  constructor(
    private readonly filmService: FilmService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.findAllFilms();
  }

  private findAllFilms(): void {
    this.filmService.getAllFilms({ page: this.page, size: this.size }).subscribe({
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

  borrowFilm(film: FilmResponse): void {
    this.message = '';
    this.filmService.rentFilm({ filmId: film.id as number }).subscribe({
      next: (response) => {
        console.log("Film Rented Successfully");
        this.message = 'Film Rented Successfully';
        this.level = 'success';
      },
      error: (error) => {
        console.log(error);
        this.message = error.error.errorMessage;
        this.level = 'danger';
      }
    });
  }

  onShowFilmDetails(film: FilmResponse): void {
  this.selectedFilm = film;
  this.showViewFilmModal = true;
}

  onViewFilmModalClose() {
    this.showViewFilmModal = false;
  }
}
