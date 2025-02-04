import { Component } from '@angular/core';
import {AuthenticateRequest} from "../../services/models/authenticate-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  authRequest: AuthenticateRequest = {email: '', password: ''};
  errorMsg: Array<string> =[];

  constructor(
    private readonly router: Router,
    private readonly authService: AuthenticationService,
    private readonly tokenService: TokenService
  ) {

  }

  login() {
    this.errorMsg= []
    console.log("Login Button is Clicked");
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (response) => {
        console.log(response);
        this.tokenService.token = response.token as string;
        this.router.navigate(['films']);
      }, error: (error) => {
        console.log(error);
       if (error.error.validationErrors){
          this.errorMsg = error.error.validationErrors;
       }else {
         this.errorMsg.push(error.error.errorMessage);
      }
    }});
  }

  register() {
    console.log("Register Button is Clicked");
    this.router.navigate(['register']);
  }
}
