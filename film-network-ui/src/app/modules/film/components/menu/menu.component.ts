import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {TokenService} from "../../../../services/token/token.service";
import {KeycloakService} from "../../../../services/keycloak/keycloak.service";
import {async} from "rxjs";
import {UserProfile} from "../../../../services/keycloak/user-profile";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{
  constructor(
    private  readonly tokenService: TokenService,
    private readonly router: Router,
    private readonly keyCloakService: KeycloakService
  ) {
  }

  firstName:string =  '';
  lastName: string ='';
  // user: UserResponse = {}
  async ngOnInit() {
      const linkColor = document.querySelectorAll('.nav-link');
      linkColor.forEach(link => {
        if (window.location.href.endsWith(link.getAttribute('href') || '')){
            link.classList.add('active');
        }
        link.addEventListener('click', ()=>{
          linkColor.forEach(l => l.classList.remove('active'));
          link.classList.add('active');
        });
      });

      this.firstName = this.keyCloakService.profile?.firstName!
      this.lastName = this.keyCloakService.profile?.lastName!;

  }

  getUserInfo():string{
    // return this.keyCloakService.keycloak.subject
    return this.firstName + " "+ this.lastName;
  }

  async logout() {
   this.keyCloakService.logout();
  }
}
