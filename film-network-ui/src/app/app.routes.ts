import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from "./pages/login/login.component";
import { RegisterComponent } from "./pages/register/register.component";
import { ActivateAccountComponent } from "./pages/activate-account/activate-account.component";
import {NoAuthGuard} from "./auth/no-auth.guard";
import {AuthGuard} from "./auth/auth.guard";


export const routes: Routes = [
  // When the path is empty, redirect to /login (or you could choose /films)
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  // Public routes: if authenticated, the NoAuthGuard will redirect to /films.
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [NoAuthGuard]
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [NoAuthGuard]
  },
  {
    path: 'activate',
    component: ActivateAccountComponent
  },
  // Protected route: accessible only if authenticated.
  {
    path: 'films',
    loadChildren: () => import('./modules/film/film-routing.module').then(m => m.FilmRoutingModule),
    canActivate: [AuthGuard]
  },
  // Wildcard route: redirect any unknown paths to login
  {
    path: '**',
    redirectTo: '/login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
