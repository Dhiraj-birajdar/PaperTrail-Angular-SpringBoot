import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpHeaders
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {TokenService} from "../token/token.service";
import {KeycloakService} from "../../keycloak/keycloak.service";

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  constructor(
    private keycloakService: KeycloakService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.keycloakService.keycloak.token;
    if(token){
      const updatedRequest = request.clone({
        headers: new HttpHeaders({
          Authorization: `Bearer ${token}`
        })
      });
      return next.handle(updatedRequest);
    }
    return next.handle(request);
  }
}
