import { Component } from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {FormsModule} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    NgForOf,
    NgIf
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerRequest: RegistrationRequest = {
    email: '',
    password: '',
    firstName: '',
    lastName: ''
  };
  errorMsg: Array<string> = [];

  constructor(
    private readonly  authService: AuthenticationService,
    private readonly router: Router
  ) {
  }
  register() {
    this.errorMsg = [];
    console.log("Register Button is Clicked");
    this.authService.register({
      body: this.registerRequest
    }).subscribe({
      next: (response) => {
        console.log(response);
        this.router.navigate(['activate']);
      }, error: (error) => {
        console.log(error);
        if (error.error.validationErrors) {
          this.errorMsg = error.error.validationErrors;
        } else {
          this.errorMsg.push(error.error.errorMessage);
        }
      }
    })
  }
}
