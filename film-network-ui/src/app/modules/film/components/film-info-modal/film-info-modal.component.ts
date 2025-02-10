import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FilmResponse} from "../../../../services/models/film-response";
import {FeedbackComponent} from "../feedbacks/feedbacks.component";

@Component({
  selector: 'app-film-info-modal',
  standalone: true,
  imports: [
    FeedbackComponent
  ],
  templateUrl: './film-info-modal.component.html',
  styleUrl: './film-info-modal.component.scss'
})
export class ViewFilmModalComponent  {
  @Input() film: FilmResponse | null = null;
  @Output() closeModal = new EventEmitter<void>();

  onClose(): void {
    this.closeModal.emit();
  }

  get filmPosterUrl(): string {
    if (this.film && this.film.filmPoster) {
      // Check if the film poster string already includes the data URL prefix.
      if (this.film.filmPoster.startsWith('data:image')) {
        return this.film.filmPoster;
      } else {
        return 'data:image/jpeg;base64,' + this.film.filmPoster;
      }
    }
    // Optionally, return a default image if none is provided.
    return 'assets/default-film-poster.png';
  }
}
