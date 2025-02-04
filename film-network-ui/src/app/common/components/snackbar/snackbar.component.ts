import { Component, Input, OnInit } from '@angular/core';
import {NgClass, NgIf} from '@angular/common';

@Component({
  selector: 'app-snackbar',
  standalone: true,
  imports: [NgClass, NgIf],
  templateUrl: './snackbar.component.html',
  styleUrls: ['./snackbar.component.scss']
})
export class SnackbarComponent implements OnInit {
  // The message to display in the snackbar.
  @Input() message: string = '';
  // The level of the message: 'success' or 'danger'.
  @Input() level: 'success' | 'danger' = 'success';
  // Duration (in milliseconds) for which the snackbar should be visible. Default: 5000 (5 seconds)
  @Input() duration: number = 5000;

  visible = false;
  private timeoutId: any;

  ngOnInit(): void {
    if (this.message) {
      this.show();
    }
  }

  show(): void {
    this.visible = true;
    this.timeoutId = setTimeout(() => {
      this.visible = false;
    }, this.duration);
  }

  close(): void {
    this.visible = false;
    // Clear the auto-hide timeout if the user manually closes the snackbar.
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }
  }
}
