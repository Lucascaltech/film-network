/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseFilmResponse } from '../../models/page-response-film-response';

export interface GetAllFilmsYouOwn$Params {
  page?: number;
  size?: number;
}

export function getAllFilmsYouOwn(http: HttpClient, rootUrl: string, params?: GetAllFilmsYouOwn$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFilmResponse>> {
  const rb = new RequestBuilder(rootUrl, getAllFilmsYouOwn.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseFilmResponse>;
    })
  );
}

getAllFilmsYouOwn.PATH = '/films/own';
