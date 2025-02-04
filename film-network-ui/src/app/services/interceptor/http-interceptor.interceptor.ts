import { Injectable} from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpHeaders
} from "@angular/common/http";
import { Observable } from "rxjs";
import {TokenService} from "../token/token.service";

@Injectable()
export class HttpInterceptorInterceptor implements HttpInterceptor {

  constructor(
    private readonly tokenService: TokenService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token:string = this.tokenService.token;
    if (token){
      const clonedRequest = request.clone({
        headers:new HttpHeaders({
          Authorization: `Bearer ${token}`
        })
      });
      return next.handle(clonedRequest);
    }
    return next.handle(request);
  }
}
