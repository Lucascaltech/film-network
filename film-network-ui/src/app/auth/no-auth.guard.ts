import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NoAuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    _route: ActivatedRouteSnapshot,
    _state: RouterStateSnapshot
  ): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      // If token exists, redirect to films and prevent navigation to public pages
      this.router.navigate(['/films']);
      return false;
    }
    return true;
  }
}
