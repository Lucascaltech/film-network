import {inject} from '@angular/core';
import {KeycloakService} from "../services/keycloak/keycloak.service";
import {CanActivateFn, Router} from "@angular/router";


export const authGuard: CanActivateFn = () => {
  const tokenService = inject(KeycloakService);
  const router = inject(Router);
  if (tokenService.keycloak.isTokenExpired()) {
    router.navigate(['login']);
    return false;
  }
  return true;
};
