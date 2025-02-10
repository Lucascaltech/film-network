import {Component, Input, OnInit} from '@angular/core';
import {FeedbackService} from "../../../../services/services/feedback.service";
import {PageResponseFilmFeedbackResponse} from "../../../../services/models/page-response-film-feedback-response";
import {FilmResponse} from "../../../../services/models/film-response";
import {DatePipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-feedbacks',
  standalone: true,
  imports: [
    DatePipe,
    NgIf,
    NgForOf
  ],
  templateUrl: './feedbacks.component.html',
  styleUrl: './feedbacks.component.scss'
})
export class FeedbackComponent implements OnInit {
  @Input() film: FilmResponse ={}; // film ID passed from the modal

  feedbacks: PageResponseFilmFeedbackResponse = {};
  isLoading = false;
  errorMessage = '';

  constructor(private feedbackService: FeedbackService) {}

  ngOnInit(): void {
    if (this.film.id !== null) {
      this.loadFeedbacks();
    }
  }

  loadFeedbacks(): void {
    this.isLoading = true;
    this.feedbackService.getFeedbackForFilm({filmId:this.film.id!}).subscribe({
      next: (data) => {
        this.feedbacks = data;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load feedbacks.';
        this.isLoading = false;
      }
    });
  }
}
