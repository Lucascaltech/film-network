import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FilmRequest } from '../../../../services/models/film-request';
import { NgForOf, NgIf } from "@angular/common";
import {FilmResponse} from "../../../../services/models/film-response";

export interface FilmCreationEvent {
  film: FilmRequest;
  filmCoverFile: File | null;
}

@Component({
  selector: 'app-add-film-modal',
  standalone: true,
  imports: [FormsModule, NgForOf, NgIf],
  templateUrl: './add-film-modal.component.html',
  styleUrls: ['./add-film-modal.component.scss']
})
export class AddFilmModalComponent implements OnInit, OnChanges {
  /**
   * Input property: if provided (edit mode), the modal pre-populates the form.
   * When adding a new film, this will be null.
   */
  @Input() filmToEdit: FilmResponse | null = null;

  @Output() closeModal = new EventEmitter<void>();
  @Output() submitFilm = new EventEmitter<FilmCreationEvent>();

  // Local film model used for binding.
  film = {
    title: '',
    director: '',
    imdbId: '',
    synopsis: '',
    year: '',
    archive: false,
    rating: 0,
    genre: '',
    filmPoster: ''
  };

  posterFile: File | null = null;
  availableYears: string[] = [];
  availableGenres: string[] = ['Action', 'Comedy', 'Drama', 'Horror', 'Sci-Fi', 'Romance', 'Thriller'];
  selectedPage: any;

  ngOnInit(): void {
    // Populate availableYears (from 2000 to current year)
    const currentYear = new Date().getFullYear();
    for (let year = 2000; year <= currentYear; year++) {
      this.availableYears.push(year.toString());
    }
    if (this.filmToEdit) {
      this.populateForm(this.filmToEdit);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['filmToEdit']) {
      if (changes['filmToEdit'].currentValue) {
        this.populateForm(changes['filmToEdit'].currentValue);
      }
    }
  }

  private populateForm(filmData: FilmResponse): void {
    this.film.title= filmData.title as string;
    this.film.genre = filmData.genre as string;
    this.film.director = filmData.director as string;
    this.film.imdbId = filmData.imdbId as string;
    this.film.synopsis = filmData.synopsis as string;
    this.film.year = filmData.year as string;
    this.film.archive = filmData.archive as boolean;

    if (filmData.filmPoster){
      this.selectedPage = 'data:image/jpeg;base64,' + filmData.filmPoster;
    }
  }

  onClose(): void {
    this.closeModal.emit();
  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      this.posterFile = event.target.files[0];
      this.selectedPage = URL.createObjectURL(this.posterFile!);
    }
  }

  onSubmit(): void {
    this.submitFilm.emit({
      film: { ...this.film, filmPoster: ''},
      filmCoverFile: this.posterFile
    });

    this.onClose();
  }
}
