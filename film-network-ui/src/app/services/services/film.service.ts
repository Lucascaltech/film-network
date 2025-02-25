/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { approveRentedFilm } from '../fn/film/approve-rented-film';
import { ApproveRentedFilm$Params } from '../fn/film/approve-rented-film';
import { createFilm } from '../fn/film/create-film';
import { CreateFilm$Params } from '../fn/film/create-film';
import { deleteFilm } from '../fn/film/delete-film';
import { DeleteFilm$Params } from '../fn/film/delete-film';
import { FilmResponse } from '../models/film-response';
import { getAllFilms } from '../fn/film/get-all-films';
import { GetAllFilms$Params } from '../fn/film/get-all-films';
import { getAllFilmsYouOwn } from '../fn/film/get-all-films-you-own';
import { GetAllFilmsYouOwn$Params } from '../fn/film/get-all-films-you-own';
import { getBorrowedFilms } from '../fn/film/get-borrowed-films';
import { GetBorrowedFilms$Params } from '../fn/film/get-borrowed-films';
import { getFilmById } from '../fn/film/get-film-by-id';
import { GetFilmById$Params } from '../fn/film/get-film-by-id';
import { getReturnedFilmsForOwner } from '../fn/film/get-returned-films-for-owner';
import { GetReturnedFilmsForOwner$Params } from '../fn/film/get-returned-films-for-owner';
import { PageResponseFilmResponse } from '../models/page-response-film-response';
import { PageResponseRentedFilmResponse } from '../models/page-response-rented-film-response';
import { rentFilm } from '../fn/film/rent-film';
import { RentFilm$Params } from '../fn/film/rent-film';
import { returnRentedFilm } from '../fn/film/return-rented-film';
import { ReturnRentedFilm$Params } from '../fn/film/return-rented-film';
import { searchFilms } from '../fn/film/search-films';
import { SearchFilms$Params } from '../fn/film/search-films';
import { updateArchivedStatus } from '../fn/film/update-archived-status';
import { UpdateArchivedStatus$Params } from '../fn/film/update-archived-status';
import { updateFilm } from '../fn/film/update-film';
import { UpdateFilm$Params } from '../fn/film/update-film';
import { uploadPoster } from '../fn/film/upload-poster';
import { UploadPoster$Params } from '../fn/film/upload-poster';


/**
 * The Film API for CRUD operations
 */
@Injectable({ providedIn: 'root' })
export class FilmService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getFilmById()` */
  static readonly GetFilmByIdPath = '/films/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFilmById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFilmById$Response(params: GetFilmById$Params, context?: HttpContext): Observable<StrictHttpResponse<FilmResponse>> {
    return getFilmById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getFilmById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFilmById(params: GetFilmById$Params, context?: HttpContext): Observable<FilmResponse> {
    return this.getFilmById$Response(params, context).pipe(
      map((r: StrictHttpResponse<FilmResponse>): FilmResponse => r.body)
    );
  }

  /** Path part for operation `updateFilm()` */
  static readonly UpdateFilmPath = '/films/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateFilm()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateFilm$Response(params: UpdateFilm$Params, context?: HttpContext): Observable<StrictHttpResponse<FilmResponse>> {
    return updateFilm(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateFilm$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateFilm(params: UpdateFilm$Params, context?: HttpContext): Observable<FilmResponse> {
    return this.updateFilm$Response(params, context).pipe(
      map((r: StrictHttpResponse<FilmResponse>): FilmResponse => r.body)
    );
  }

  /** Path part for operation `deleteFilm()` */
  static readonly DeleteFilmPath = '/films/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteFilm()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteFilm$Response(params: DeleteFilm$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteFilm(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteFilm$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteFilm(params: DeleteFilm$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteFilm$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `getAllFilms()` */
  static readonly GetAllFilmsPath = '/films';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllFilms()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllFilms$Response(params?: GetAllFilms$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFilmResponse>> {
    return getAllFilms(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllFilms$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllFilms(params?: GetAllFilms$Params, context?: HttpContext): Observable<PageResponseFilmResponse> {
    return this.getAllFilms$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFilmResponse>): PageResponseFilmResponse => r.body)
    );
  }

  /** Path part for operation `createFilm()` */
  static readonly CreateFilmPath = '/films';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createFilm()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createFilm$Response(params: CreateFilm$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createFilm(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createFilm$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createFilm(params: CreateFilm$Params, context?: HttpContext): Observable<number> {
    return this.createFilm$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `rentFilm()` */
  static readonly RentFilmPath = '/films/rent/{filmId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `rentFilm()` instead.
   *
   * This method doesn't expect any request body.
   */
  rentFilm$Response(params: RentFilm$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return rentFilm(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `rentFilm$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  rentFilm(params: RentFilm$Params, context?: HttpContext): Observable<number> {
    return this.rentFilm$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `uploadPoster()` */
  static readonly UploadPosterPath = '/films/poster/{filmId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadPoster()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadPoster$Response(params: UploadPoster$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return uploadPoster(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadPoster$Response()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadPoster(params: UploadPoster$Params, context?: HttpContext): Observable<number> {
    return this.uploadPoster$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `returnRentedFilm()` */
  static readonly ReturnRentedFilmPath = '/films/rent/return/{filmId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `returnRentedFilm()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnRentedFilm$Response(params: ReturnRentedFilm$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return returnRentedFilm(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `returnRentedFilm$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnRentedFilm(params: ReturnRentedFilm$Params, context?: HttpContext): Observable<number> {
    return this.returnRentedFilm$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `approveRentedFilm()` */
  static readonly ApproveRentedFilmPath = '/films/rent/approve/{filmId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `approveRentedFilm()` instead.
   *
   * This method doesn't expect any request body.
   */
  approveRentedFilm$Response(params: ApproveRentedFilm$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return approveRentedFilm(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `approveRentedFilm$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  approveRentedFilm(params: ApproveRentedFilm$Params, context?: HttpContext): Observable<number> {
    return this.approveRentedFilm$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `updateArchivedStatus()` */
  static readonly UpdateArchivedStatusPath = '/films/archived/{filmId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateArchivedStatus()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateArchivedStatus$Response(params: UpdateArchivedStatus$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return updateArchivedStatus(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateArchivedStatus$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateArchivedStatus(params: UpdateArchivedStatus$Params, context?: HttpContext): Observable<number> {
    return this.updateArchivedStatus$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `getBorrowedFilms()` */
  static readonly GetBorrowedFilmsPath = '/films/user/borrowed';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBorrowedFilms()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBorrowedFilms$Response(params?: GetBorrowedFilms$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseRentedFilmResponse>> {
    return getBorrowedFilms(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBorrowedFilms$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBorrowedFilms(params?: GetBorrowedFilms$Params, context?: HttpContext): Observable<PageResponseRentedFilmResponse> {
    return this.getBorrowedFilms$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseRentedFilmResponse>): PageResponseRentedFilmResponse => r.body)
    );
  }

  /** Path part for operation `searchFilms()` */
  static readonly SearchFilmsPath = '/films/search';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `searchFilms()` instead.
   *
   * This method doesn't expect any request body.
   */
  searchFilms$Response(params: SearchFilms$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFilmResponse>> {
    return searchFilms(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `searchFilms$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  searchFilms(params: SearchFilms$Params, context?: HttpContext): Observable<PageResponseFilmResponse> {
    return this.searchFilms$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFilmResponse>): PageResponseFilmResponse => r.body)
    );
  }

  /** Path part for operation `getReturnedFilmsForOwner()` */
  static readonly GetReturnedFilmsForOwnerPath = '/films/owner/returned';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getReturnedFilmsForOwner()` instead.
   *
   * This method doesn't expect any request body.
   */
  getReturnedFilmsForOwner$Response(params?: GetReturnedFilmsForOwner$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseRentedFilmResponse>> {
    return getReturnedFilmsForOwner(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getReturnedFilmsForOwner$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getReturnedFilmsForOwner(params?: GetReturnedFilmsForOwner$Params, context?: HttpContext): Observable<PageResponseRentedFilmResponse> {
    return this.getReturnedFilmsForOwner$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseRentedFilmResponse>): PageResponseRentedFilmResponse => r.body)
    );
  }

  /** Path part for operation `getAllFilmsYouOwn()` */
  static readonly GetAllFilmsYouOwnPath = '/films/own';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllFilmsYouOwn()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllFilmsYouOwn$Response(params?: GetAllFilmsYouOwn$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFilmResponse>> {
    return getAllFilmsYouOwn(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllFilmsYouOwn$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllFilmsYouOwn(params?: GetAllFilmsYouOwn$Params, context?: HttpContext): Observable<PageResponseFilmResponse> {
    return this.getAllFilmsYouOwn$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFilmResponse>): PageResponseFilmResponse => r.body)
    );
  }

}
