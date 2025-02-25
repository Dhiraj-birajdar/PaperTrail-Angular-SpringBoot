import { Injectable } from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set token(token: string) {
    localStorage.setItem('token', token);
  }

  get token(){
    return localStorage.getItem('token') as string;
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  private isTokenValid() {
    const token: string =this. token;
    if(!token){
      return false;
    }
    // todo decode token
    const jwtHlper = new JwtHelperService();
    // check expiry
    const isTokenExpired=jwtHlper.isTokenExpired(token);
    if(isTokenExpired){
      localStorage.clear();
      return false;
    }
    return true;
  }
}
