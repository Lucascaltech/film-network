import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routes';
import {LoginComponent} from "./pages/login/login.component";
import {FormsModule} from "@angular/forms";
import {HttpInterceptorInterceptor} from "./services/interceptor/http-interceptor.interceptor";
import {LoaderComponent} from "./common/components/loader/loader.component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        LoaderComponent
    ],
  providers: [
    HttpClient,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
