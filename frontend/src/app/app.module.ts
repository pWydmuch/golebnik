import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {ChatComponent} from './components/chat/chat/chat.component';
import {UserJoinFormComponent} from './components/user-join-form/user-join-form.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from '@angular/forms';

import {HttpClientModule} from "@angular/common/http";
import {LobbyComponent} from './components/lobby/lobby.component';
import {RoomComponent} from './components/room/room.component';
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TimesPipe} from './pipes/times.pipe';

import { TicTacToeFieldComponent } from './components/games/tictactoe/tic-tac-toe-field/tic-tac-toe-field.component';
import { TicTacToeBoardComponent } from './components/games/tictactoe/tic-tac-toe-board/tic-tac-toe-board.component';
import { Connect4FieldComponent } from './components/games/connect4/connect4-field/connect4-field.component';
import {Connect4BoardComponent} from "./components/games/connect4/connect4-board/connect4-board.component";



@NgModule({
  declarations: [
    AppComponent,
    ChatComponent,
    UserJoinFormComponent,
    Connect4BoardComponent,
    Connect4FieldComponent,
    TicTacToeBoardComponent,
    TicTacToeFieldComponent,
    LobbyComponent,
    RoomComponent,
    TimesPipe,
    TicTacToeFieldComponent,
    TicTacToeBoardComponent,
    Connect4FieldComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
