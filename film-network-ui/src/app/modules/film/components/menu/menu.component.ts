import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { TokenService } from "../../../../services/token/token.service";
import { KeycloakService } from "../../../../services/keycloak/keycloak.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule
  ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  firstName: string = '';
  lastName: string = '';
  searchQuery: string = ''; // Bind search input

  constructor(
    private readonly tokenService: TokenService,
    private readonly router: Router,
    private readonly keyCloakService: KeycloakService
  ) {}

  async ngOnInit() {
    // Set active link
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach(link => {
      if (window.location.href.endsWith(link.getAttribute('href') || '')){
        link.classList.add('active');
      }
      link.addEventListener('click', () => {
        linkColor.forEach(l => l.classList.remove('active'));
        link.classList.add('active');
      });
    });

    // Fetch user details
    this.firstName = this.keyCloakService.profile?.firstName || '';
    this.lastName = this.keyCloakService.profile?.lastName || '';
  }

  getUserInfo(): string {
    return this.firstName + " " + this.lastName;
  }

  async logout() {
    this.keyCloakService.logout();
  }

  // 🔎 Search function
  onSearch() {
    if (this.searchQuery.trim()) {
      this.router.navigate(['/films/search'], { queryParams: { q: this.searchQuery } });
    }
    console.log("empty")
  }

  // 🔄 Handle Enter key press
  onEnter(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.onSearch();
    }
  }
}
