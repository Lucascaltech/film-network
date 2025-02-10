import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FilmResponse} from "../../../../services/models/film-response";
import {NgClass, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-film-card',
  standalone: true,
  imports: [
    NgClass,
    NgForOf,
    NgIf
  ],
  templateUrl: './film-card.component.html',
  styleUrl: './film-ca' +
    'rd.component.scss'
})
export class FilmCardComponent{

  private _film: FilmResponse = {};
  private _filmPoster: string = '';
  private _manage = false;
  private _userId: string = localStorage.getItem('userId') as string;

  get userId(): number {
    return this._userId as unknown as number;
  }
  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }


  get filmPoster(): string {
    if (this._film) {
      return  'data:image/jpeg;base64,' + this._film.filmPoster;
    }
    return this._filmPoster;
  }

  set filmPoster(value: string) {
    this._filmPoster = value;
  }


  @Input()
  set film(value: FilmResponse) {
    this._film = value;
  }

  get film(): FilmResponse {
    return this._film;
  }

  @Output() private readonly archive: EventEmitter<FilmResponse> = new EventEmitter<FilmResponse>();
  @Output() private readonly addToWaitingList: EventEmitter<FilmResponse> = new EventEmitter<FilmResponse>();
  @Output() private readonly borrow: EventEmitter<FilmResponse> = new EventEmitter<FilmResponse>();
  @Output()  private readonly edit: EventEmitter<FilmResponse> = new EventEmitter<FilmResponse>();
  @Output() private readonly details: EventEmitter<FilmResponse> = new EventEmitter<FilmResponse>();


  onShowDetails() {
    console.log("Show film button is clicked")
    this.details.emit(this._film);
  }

  onFavorite() {
    console.log("Add to favorite Button is clicked")
    this.addToWaitingList.emit(this._film);
  }

  onBorrow() {
    console.log("Borrow film button is clicked")
    this.borrow.emit(this._film);
  }

  onDelete() {
    console.log("Delete film button is clicked")
    this.archive.emit(this._film);
  }

  onEdit() {
    console.log("Edit film button is clicked")
    this.edit.emit(this._film);
  }
}

