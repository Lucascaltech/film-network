import { Injectable } from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  set token(token: string) {
      localStorage.setItem('token', token);
  }

  get token() {
    return localStorage.getItem('token') as string;
  }

  set userId(userId: string) {
      localStorage.setItem('userId', userId);
  }

  get userId() {
    return localStorage.getItem('userId') as string;
  }

  logoutUser():boolean {
    localStorage.removeItem('token')
    localStorage.removeItem('userId')
    if (localStorage.getItem('token') || localStorage.getItem('userId')) return false;
    else return true;
  }

  isValidToken():boolean{
    let token = localStorage.getItem('token')
    if(!token) return false;

    const jwtHelper = new JwtHelperService();
    const isTokenExpire =jwtHelper.isTokenExpired(token);
    if (isTokenExpire){
      localStorage.clear();
      return false;
    }
    return true;
}
}
