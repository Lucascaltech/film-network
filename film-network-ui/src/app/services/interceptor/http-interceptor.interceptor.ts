import { Injectable} from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpHeaders
} from "@angular/common/http";
import { Observable } from "rxjs";
import {KeycloakService} from "../keycloak/keycloak.service";

@Injectable()
export class HttpInterceptorInterceptor implements HttpInterceptor {

  constructor(
    private readonly keycloakService: KeycloakService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token:string | undefined = this.keycloakService.keycloak?.token;
    console.log("Toke is : " , token)
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
