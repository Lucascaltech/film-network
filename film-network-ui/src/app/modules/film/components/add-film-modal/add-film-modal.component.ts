import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FilmRequest } from '../../../../services/models/film-request';
import {NgForOf, NgIf} from "@angular/common";

// Define an interface for the event payload that includes both FilmRequest and the cover file.
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
export class AddFilmModalComponent implements OnInit {
  // Emits when the modal should be closed.
  @Output() closeModal = new EventEmitter<void>();
  // Emits the new film data and the film cover file.
  @Output() submitFilm = new EventEmitter<FilmCreationEvent>();

  // Film model with default values.
  // This should match your FilmRequest (except id) with filmPoster as an empty string.
  film = {
    title: '',
    director: '',
    imdbId: '',
    synopsis: '',
    year: '',      // as string, e.g., "2010"
    archive: false,
    rating: 0,
    genre: '',
    filmPoster: '' // This will remain empty since the file is handled separately.
  };

  // Holds the actual file object for the cover.
  posterFile: File | null = null;

  // Arrays for dropdown values (if needed)
  availableYears: string[] = [];
  availableGenres: string[] = ['Action', 'Comedy', 'Drama', 'Horror', 'Sci-Fi', 'Romance', 'Thriller'];
  selectedPage: any;

  ngOnInit(): void {
    // Generate years from 2000 to the current year for dropdown (optional)
    const currentYear = new Date().getFullYear();
    for (let year = 2000; year <= currentYear; year++) {
      this.availableYears.push(year.toString());
    }
  }

  onClose(): void {
    this.closeModal.emit();
  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      this.posterFile = event.target.files[0];
      // Create an object URL for the selected file and assign it to selectedPage
      this.selectedPage = URL.createObjectURL(this.posterFile!);
    }
  }

  onSubmit(): void {
    // Emit an object with two properties:
    // 1. film: the FilmRequest (with filmPoster as empty string)
    // 2. filmCoverFile: the actual file selected (or null)
    this.submitFilm.emit({
      film: { ...this.film, filmPoster: '' },
      filmCoverFile: this.posterFile
    });
    this.onClose();
  }
}
