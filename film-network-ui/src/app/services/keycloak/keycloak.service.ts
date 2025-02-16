import {Injectable} from '@angular/core';
import Keycloak from "keycloak-js";
import {UserProfile} from "./user-profile";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

  constructor() {
  }

  get keycloak() {
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        url: 'http://localhost:9090',
        realm: 'my-realm',
        clientId: 'fsn'
      })
    }
    return this._keycloak
  }

  get profile(): UserProfile | undefined {
    return this._profile;
  }

  async init() {
    try {
      console.log("Initializing Keycloak");
      const keycloakInstance = this.keycloak;
      const authenticated = await keycloakInstance.init({
        onLoad: 'login-required',
      });
      if (authenticated) {
        console.log("Authenticated")
        this._profile = (await this._keycloak?.loadUserProfile()) as UserProfile
        this._profile.token = this._keycloak?.token;
      }
    } catch (error) {
      console.error("Keycloak initialization failed", error);
    }
  }

  login(){
    return this._keycloak?.login();
  }

  logout(){
    return this._keycloak?.logout({redirectUri:'http://localhost:4200'});
  }
}
