import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {AllFilmsComponent} from "./pages/all-films/all-films.component";
import {MyFilmsComponent} from "./pages/my-films/my-films.component";

const routes: Routes = [
  {
    path: '',
    component:MainComponent,
    children:[
      {
        path:'',
        component:AllFilmsComponent
      },
      {
        path:'my-films',
        component: MyFilmsComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FilmRoutingModule { }
