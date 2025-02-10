import {Component, OnInit} from '@angular/core';
import {PageResponseFilmResponse} from "../../../../services/models/page-response-film-response";
import {FilmResponse} from "../../../../services/models/film-response";
import {FilmService} from "../../../../services/services/film.service";
import {Router} from "@angular/router";
import {AddFilmModalComponent, FilmCreationEvent} from "../../components/add-film-modal/add-film-modal.component";
import {FilmCardComponent} from "../../components/film-card/film-card.component";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {SnackbarComponent} from "../../../../common/components/snackbar/snackbar.component";

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [
    AddFilmModalComponent,
    FilmCardComponent,
    NgForOf,
    NgIf,
    SnackbarComponent,
    NgClass
  ],
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.scss'
})
export class WishlistComponent implements OnInit {
  page: number = 0;
  size: number = 10;
  filmResponse: PageResponseFilmResponse = {};
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

  onEditFilm(film: FilmResponse): void {
    console.log("On Edit Button is clicked");
    this.editingFilm = film;
    this.showAddFilmModal = true;
    console.log("The film at onEdit is : ", film)
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
        this.findAllFilms();
      },
      error: (error) => {
        console.log(error);
        this.message = error.error.errorMessage;
        this.level = 'danger';
      }
    })

  }

  // Called when the "Add Film" button is clicked.
  onAddFilm(): void {
    this.editingFilm = null;
    this.showAddFilmModal = true;
  }

  // Called when the modal emits the close event.
  onModalClose(): void {
    this.showAddFilmModal = false;
  }

  onFilmSubmit(filmData: FilmCreationEvent): void {
    if (this.editingFilm) {
      console.log("This is the film", this.editingFilm)
      this.filmService.updateFilm({
          id:this.editingFilm.id as number,
          body: filmData.film
        }).subscribe({
          next: (response) => {
            console.log("Film Updated Successfully");
            this.message = 'Film Updated Successfully';
            this.level = 'success';

            console.log(filmData)
            if (filmData.filmCoverFile !== null) {
              this.filmService.uploadPoster({
                filmId: this.editingFilm!.id as number,
                body: { file: filmData.filmCoverFile }
              }).subscribe({
                next: (res) => {
                  console.log("Poster Updated Successfully");
                  this.message = 'Poster Updated Successfully';
                  this.finishFilmModal();
                },
                error: (error) => {
                  console.log(error);
                  this.message = error.error.errorMessage;
                  this.level = 'danger';
                }
              });
            } else {
              this.finishFilmModal();
            }
          },
          error: (error) => {
            console.log(error);
            this.message = error.error.errorMessage;
            this.level = 'danger';
          }
      })

    } else {
      // Create mode: use existing create logic (do not change film create logic)
      this.filmService.createFilm({
        body: filmData.film,
      }).subscribe({
        next: (response) => {
          console.log("Film Created Successfully");
          this.message = 'Film Created Successfully';
          this.level = 'success';
          this.filmService.uploadPoster({
            filmId: response,
            body: { file: filmData.filmCoverFile! }
          }).subscribe({
            next: (res) => {
              console.log("Poster Uploaded Successfully");
              this.message = 'Poster Uploaded Successfully';
              this.finishFilmModal();
            },
            error: (error) => {
              console.log(error);
              this.message = error.error.errorMessage;
              this.level = 'danger';
            }
          });
        },
        error: (error) => {
          console.log(error);
          this.message = error.error.errorMessage;
          this.level = 'danger';
        }
      });
    }
  }

  private finishFilmModal(): void {
    this.showAddFilmModal = false;
    this.editingFilm = null;
    this.findAllFilms();
  }
}
