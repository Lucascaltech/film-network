import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import { LoginComponent } from "./pages/login/login.component";
// import { RegisterComponent } from "./pages/register/register.component";
// import { ActivateAccountComponent } from "./pages/activate-account/activate-account.component";
import {authGuard} from "./auth/auth.guard";


export const routes: Routes = [
  // When the path is empty, redirect to /login (or you could choose /films)
  {
    path: '',
    redirectTo: 'films',
    pathMatch: 'full'
  },
  // {
  //   path: 'login',
  //   component: LoginComponent
  // },
  // {
  //   path: 'register',
  //   component: RegisterComponent
  // },
  // {
  //   path: 'activate-account',
  //   component: ActivateAccountComponent
  // },
  // Protected route: accessible only if authenticated.
  {
    path: 'films',
    loadChildren: () => import('./modules/film/film-routing.module').then(m => m.FilmRoutingModule),
    canActivate: [authGuard]
  },
  // Wildcard route: redirect any unknown paths to login
  // {
  //   path: '**',
  //   redirectTo: '/login'
  // }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
