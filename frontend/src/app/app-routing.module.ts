import { NgModule } from '@angular/core';
import { Routes, RouterModule } from "@angular/router";
import { UserJoinFormComponent } from './components/user-join-form/user-join-form.component';
import { ChatComponent } from './components/chat/chat.component';

const routes: Routes = [
  { path: "", redirectTo: 'home', pathMatch: 'full' },
  { path: "home", component: UserJoinFormComponent },
  { path: "chat", component: ChatComponent },
];


@NgModule({
  declarations: [],

  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],

})
export class AppRoutingModule { }
