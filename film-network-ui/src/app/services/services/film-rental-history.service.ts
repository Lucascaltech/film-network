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

import { createRentalHistory } from '../fn/film-rental-history/create-rental-history';
import { CreateRentalHistory$Params } from '../fn/film-rental-history/create-rental-history';
import { deleteRentalHistory } from '../fn/film-rental-history/delete-rental-history';
import { DeleteRentalHistory$Params } from '../fn/film-rental-history/delete-rental-history';
import { FilmRentalHistoryResponse } from '../models/film-rental-history-response';
import { getAllRentalHistories } from '../fn/film-rental-history/get-all-rental-histories';
import { GetAllRentalHistories$Params } from '../fn/film-rental-history/get-all-rental-histories';
import { getRentalHistoryById } from '../fn/film-rental-history/get-rental-history-by-id';
import { GetRentalHistoryById$Params } from '../fn/film-rental-history/get-rental-history-by-id';
import { updateRentalHistory } from '../fn/film-rental-history/update-rental-history';
import { UpdateRentalHistory$Params } from '../fn/film-rental-history/update-rental-history';


/**
 * The film rental history API
 */
@Injectable({ providedIn: 'root' })
export class FilmRentalHistoryService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getRentalHistoryById()` */
  static readonly GetRentalHistoryByIdPath = '/film-rental-histories/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getRentalHistoryById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRentalHistoryById$Response(params: GetRentalHistoryById$Params, context?: HttpContext): Observable<StrictHttpResponse<FilmRentalHistoryResponse>> {
    return getRentalHistoryById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getRentalHistoryById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRentalHistoryById(params: GetRentalHistoryById$Params, context?: HttpContext): Observable<FilmRentalHistoryResponse> {
    return this.getRentalHistoryById$Response(params, context).pipe(
      map((r: StrictHttpResponse<FilmRentalHistoryResponse>): FilmRentalHistoryResponse => r.body)
    );
  }

  /** Path part for operation `updateRentalHistory()` */
  static readonly UpdateRentalHistoryPath = '/film-rental-histories/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateRentalHistory()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateRentalHistory$Response(params: UpdateRentalHistory$Params, context?: HttpContext): Observable<StrictHttpResponse<FilmRentalHistoryResponse>> {
    return updateRentalHistory(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateRentalHistory$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateRentalHistory(params: UpdateRentalHistory$Params, context?: HttpContext): Observable<FilmRentalHistoryResponse> {
    return this.updateRentalHistory$Response(params, context).pipe(
      map((r: StrictHttpResponse<FilmRentalHistoryResponse>): FilmRentalHistoryResponse => r.body)
    );
  }

  /** Path part for operation `deleteRentalHistory()` */
  static readonly DeleteRentalHistoryPath = '/film-rental-histories/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteRentalHistory()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRentalHistory$Response(params: DeleteRentalHistory$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteRentalHistory(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteRentalHistory$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRentalHistory(params: DeleteRentalHistory$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteRentalHistory$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `getAllRentalHistories()` */
  static readonly GetAllRentalHistoriesPath = '/film-rental-histories';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllRentalHistories()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllRentalHistories$Response(params?: GetAllRentalHistories$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<FilmRentalHistoryResponse>>> {
    return getAllRentalHistories(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllRentalHistories$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllRentalHistories(params?: GetAllRentalHistories$Params, context?: HttpContext): Observable<Array<FilmRentalHistoryResponse>> {
    return this.getAllRentalHistories$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<FilmRentalHistoryResponse>>): Array<FilmRentalHistoryResponse> => r.body)
    );
  }

  /** Path part for operation `createRentalHistory()` */
  static readonly CreateRentalHistoryPath = '/film-rental-histories';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createRentalHistory()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createRentalHistory$Response(params: CreateRentalHistory$Params, context?: HttpContext): Observable<StrictHttpResponse<FilmRentalHistoryResponse>> {
    return createRentalHistory(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createRentalHistory$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createRentalHistory(params: CreateRentalHistory$Params, context?: HttpContext): Observable<FilmRentalHistoryResponse> {
    return this.createRentalHistory$Response(params, context).pipe(
      map((r: StrictHttpResponse<FilmRentalHistoryResponse>): FilmRentalHistoryResponse => r.body)
    );
  }

}
