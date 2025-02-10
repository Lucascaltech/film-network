import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {AllFilmsComponent} from "./pages/all-films/all-films.component";
import {MyFilmsComponent} from "./pages/my-films/my-films.component";
import {BorrowedFilmsComponent} from "./pages/borrowed-films/borrowed-films.component";
import {ReturnedFilmsComponent} from "./pages/returned-films/returned-films.component";
import {WishlistComponent} from "./pages/wishlist/wishlist.component";

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
      },
      {
        path:'borrowed-films',
        component:BorrowedFilmsComponent
      },
      {
        path:'returned-films',
        component:ReturnedFilmsComponent
      },
      {
        path:'my-wishlist',
        component:WishlistComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FilmRoutingModule { }
