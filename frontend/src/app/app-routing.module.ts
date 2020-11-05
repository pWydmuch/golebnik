import { NgModule } from '@angular/core';
import { Routes, RouterModule } from "@angular/router";
import { UserJoinFormComponent } from './components/user-join-form/user-join-form.component';
import { ChatComponent } from './components/chat/chat.component';
import {BoardComponent} from "./components/tictactoe/board/board.component";
import {LobbyComponent} from "./components/lobby/lobby.component";
import {RoomComponent} from "./components/room/room.component";

const routes: Routes = [
  { path: "", redirectTo: 'home', pathMatch: 'full' },
  { path: "home", component: LobbyComponent },
  // { path: "home", component: UserJoinFormComponent },
  { path: "chat", component: ChatComponent },
  { path: "room", component: RoomComponent },
  // { path: "ttt", component: BoardComponent },
];


@NgModule({
  declarations: [],

  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],

})
export class AppRoutingModule { }
