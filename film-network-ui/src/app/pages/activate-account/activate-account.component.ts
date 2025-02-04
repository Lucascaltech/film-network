import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {NgIf} from "@angular/common";
import {CodeInputModule} from "angular-code-input";

@Component({
  selector: 'app-activate-account',
  standalone: true,
  imports: [
    NgIf,
    CodeInputModule
  ],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {
  message: string = '';
  isOkay: boolean = false;
  submitted: boolean = false;
  constructor(
    private readonly router: Router,
    private readonly authService: AuthenticationService
  ) {}

  private activateAccount(toke:string){
    this.authService.confirmAccount({
      token: toke
    }).subscribe({
      next: (response) => {
        this.message ="You account has been activated successfully\nYou can login now";
        this.isOkay = true;
        this.submitted=true;
      }, error: (error) => {
        this.message ="Token is invalid or expired";
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  onCodeCompleted(token: string) {
    this.activateAccount(token);
  }

  // protected readonly skipUntil = skipUntil;

}
