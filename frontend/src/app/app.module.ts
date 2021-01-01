import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {ChatComponent} from './components/chat/chat-comp/chat.component';
import {UserJoinFormComponent} from './components/user-join-form/user-join-form.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from '@angular/forms';

import {HttpClientModule} from "@angular/common/http";
import {LobbyComponent} from './components/lobby/lobby.component';
import {RoomComponent} from './components/room/room-comp/room.component';
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TimesPipe} from './pipes/times.pipe';

import { TicTacToeFieldComponent } from './components/games/tictactoe/tic-tac-toe-field/tic-tac-toe-field.component';
import { TicTacToeBoardComponent } from './components/games/tictactoe/tic-tac-toe-board/tic-tac-toe-board.component';
import { Connect4FieldComponent } from './components/games/connect4/connect4-field/connect4-field.component';
import {Connect4BoardComponent} from "./components/games/connect4/connect4-board/connect4-board.component";
import { GameDirective } from './components/games/game.directive';
import {MatDialogModule} from "@angular/material";
import { PlayingConfirmationDialogComponent } from './components/room/playing-confirmation-dialog/playing-confirmation-dialog.component';
import { GameChooserComponent } from './components/game-chooser/game-chooser.component';
import { NavbarComponent } from './components/navbar/navbar-comp/navbar.component';
import { UserSettingsComponent } from './components/navbar/user-settings/user-settings.component';



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
    Connect4FieldComponent,
    GameDirective,
    PlayingConfirmationDialogComponent,
    GameChooserComponent,
    NavbarComponent,
    UserSettingsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    ToastrModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    Connect4BoardComponent,
    TicTacToeBoardComponent,
    PlayingConfirmationDialogComponent
  ]
})
export class AppModule {
}
