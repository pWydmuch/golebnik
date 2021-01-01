import { NgModule } from '@angular/core';
import { Routes, RouterModule } from "@angular/router";
import { UserJoinFormComponent } from './components/user-join-form/user-join-form.component';

import {LobbyComponent} from "./components/lobby/lobby.component";
import {RoomComponent} from "./components/room/room-comp/room.component";
import {ChatComponent} from "./components/chat/chat-comp/chat.component";
import {GameChooserComponent} from "./components/game-chooser/game-chooser.component";

const routes: Routes = [
  { path: "", redirectTo: 'home', pathMatch: 'full' },
  { path: "home", component: GameChooserComponent },
  { path: "lobby", component: LobbyComponent },
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
