import {Component, OnInit} from '@angular/core';
import {AuthenticateRequest} from "../../services/models/authenticate-request";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {TokenService} from "../../services/token/token.service";
import {KeycloakService} from "../../services/keycloak/keycloak.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent  implements OnInit{
  // authRequest: AuthenticateRequest = {email: '', password: ''};
  // errorMsg: Array<string> =[];
  isLoading: boolean = false;

  constructor(
    // private readonly router: Router,
    // private readonly authService: AuthenticationService,
    // private readonly tokenService: TokenService
    private readonly keyCloakService:KeycloakService
  ) {

  }

  async  ngOnInit(): Promise<void> {
        // await  this.keyCloakService.init();
        await this.keyCloakService.login();
    }

  // login() {
  //   this.errorMsg= []
  //   this.isLoading =true
  //
  //   this.authService.authenticate({
  //     body: this.authRequest
  //   }).subscribe({
  //     next: (response) => {
  //       console.log(response);
  //       this.tokenService.token = response.token as string;
  //       this.tokenService.userId = response.id as unknown as string;
  //       this.isLoading=false;
  //       this.router.navigate(['films']);
  //
  //     }, error: (error) => {
  //       console.log(error);
  //      if (error.error.validationErrors){
  //         this.errorMsg = error.error.validationErrors;
  //      }else {
  //        this.errorMsg.push(error.error.errorMessage);
  //     }
  //
  //      this.isLoading=false;
  //   }});
  // }

  // register() {
  //   console.log("Register Button is Clicked");
  //   this.router.navigate(['register']);
  // }
}
