import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { PageResponseFilmResponse } from "../../../../services/models/page-response-film-response";
import { FilmResponse } from "../../../../services/models/film-response";
import { FilmService } from "../../../../services/services/film.service";
import {ViewFilmModalComponent} from "../../components/film-info-modal/film-info-modal.component";
import {SnackbarComponent} from "../../../../common/components/snackbar/snackbar.component";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {FilmCardComponent} from "../../components/film-card/film-card.component";
import {AddFilmModalComponent} from "../../components/add-film-modal/add-film-modal.component";

@Component({
  selector: 'app-search-films',
  standalone: true,
  imports: [
    ViewFilmModalComponent,
    SnackbarComponent,
    NgClass,
    NgForOf,
    FilmCardComponent,
    NgIf,
  ],
  templateUrl: './search-films.component.html',
  styleUrl: './search-films.component.scss'
})
export class SearchFilmsComponent implements OnInit {
  page: number = 0;
  size: number = 10;
  filmResponse: PageResponseFilmResponse = {};
  pageIndex: number = 0;
  message: string = '';
  level: 'success' | 'danger' = 'success';
  showViewFilmModal: boolean = false;
  selectedFilm: FilmResponse = {};
  searchQuery: string = '';  // Holds the search query

  constructor(
    private readonly filmService: FilmService,
    private readonly router: Router,
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit(): void {
  this.route.queryParams.subscribe(params => {
    this.searchQuery = params['q'] || '';
    if (this.searchQuery) {
      this.findAllFilms();  // Fetch search results when there's a query
    }
  });
}

  private findAllFilms(): void {
    this.filmService.searchFilms({
      page: this.page,
      size: this.size,
      keyword: this.searchQuery  // Pass search query
    }).subscribe({
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
    if (this.page > 0) {
      this.page--;
      this.findAllFilms();
    }
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
      next: () => {
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
