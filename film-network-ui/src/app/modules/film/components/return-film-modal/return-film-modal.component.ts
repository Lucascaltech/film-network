import {Component, EventEmitter, Input, Output} from '@angular/core';
import {RentedFilmResponse} from "../../../../services/models/rented-film-response";
import {FilmResponse} from "../../../../services/models/film-response";
import {FormsModule} from "@angular/forms";
import {NgClass, NgForOf} from "@angular/common";
import {FilmService} from "../../../../services/services/film.service";
import {FeedbackService} from "../../../../services/services/feedback.service";

@Component({
  selector: 'app-return-film-modal',
  standalone: true,
  imports: [
    FormsModule,
    NgClass,
    NgForOf
  ],
  templateUrl: './return-film-modal.component.html',
  styleUrl: './return-film-modal.component.scss'
})
export class ReturnFilmModalComponent {

  // The film for which the return is being processed.
  @Input() film: any | null = null;

  // Emit when the modal should be closed.
  @Output() closeModal = new EventEmitter<void>();

  // Emit when the return is submitted with the film and feedback.
  @Output() submitReturn = new EventEmitter<{ film: any, feedback: string , rating : number}>();

  @Output() submitReturnOnly =  new EventEmitter<{filmId: number}>();

  // Local variable to hold the user's feedback.
  feedback: string = '';
  // Local variable for the rating (1 to 5).
  rating: number = 0;

  /**
   * Sets the rating based on the clicked star.
   * @param star The star number (1 to 5) that was clicked.
   */
  setRating(star: number): void {
    this.rating = star;
  }

  onClose(): void {
    this.closeModal.emit();
  }

  onSubmit(): void {
    if (this.film && this.feedback.trim() && this.rating) {
      // Emit the film and the provided feedback.
      this.submitReturn.emit({ film: this.film, feedback: this.feedback.trim(), rating:this.rating  });
      // this.onClose();
    }
  }

  onReturnSubmit() {
    if(this.film.id){
      this.submitReturnOnly.emit({
        filmId:this.film.id
      })
    }
  }
}
