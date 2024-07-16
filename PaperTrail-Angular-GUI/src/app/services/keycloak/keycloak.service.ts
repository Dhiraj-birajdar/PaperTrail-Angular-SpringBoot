import { Injectable } from '@angular/core';
import Keycloak from "keycloak-js";
import {UserProfile} from "./user-profile";

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

  get profile(){
    return this._profile;
  }

  get keycloak(){
    if(!this._keycloak){
      this._keycloak = new Keycloak({
        url: "http://localhost:9090",
        realm: "papertrail",
        clientId: "papertrail"
      });
    }
    return this._keycloak;
  }
  constructor() { }

  async init() {

    const authenticated = await this.keycloak?.init({
      onLoad: "login-required"
    });

    if(authenticated){
      this._profile = (await this._keycloak?.loadUserProfile()) as UserProfile;
      this._profile.token = this.keycloak?.token;
    }
  }

  login(){
    this.keycloak?.login();
  }

  logout(){
    // this.keycloak.accountManagement();
    this.keycloak?.logout({
      redirectUri: 'http://localhost:4200'
    });
  }
}
