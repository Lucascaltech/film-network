import {Inject, Injectable} from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import {TokenService} from "../services/token/token.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router,private  tokenService: TokenService) {}

  canActivate(
    _route: ActivatedRouteSnapshot,
    _state: RouterStateSnapshot
  ): boolean {

    // if (this.tokenService.isValidToken()){
    //     this.router.navigate(['/login']);
    //     return false;
    // }
    const token = localStorage.getItem('token');
    if (token) {
      return true;
    } else {
      // If no token, navigate to login and prevent access
      this.router.navigate(['/login']);
      return false;
    }
  }
}
