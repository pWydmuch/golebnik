import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {ChatComponent} from './components/chat/chat-comp/chat.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

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
import {
  MatButtonModule, MatCardModule, MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule, MatExpansionModule,
  MatFormFieldModule, MatIconModule, MatInputModule, MatNativeDateModule, MatOptionModule, MatProgressSpinnerModule,
  MatRadioModule, MatSelectModule
} from "@angular/material";
import { MatMenuModule} from '@angular/material/menu';
import { PlayingConfirmationDialogComponent } from './components/room/playing-confirmation-dialog/playing-confirmation-dialog.component';
import { GameChooserComponent } from './components/game-chooser/game-chooser-container/game-chooser.component';
import { NavbarComponent } from './components/navbar-user-managament/navbar-comp/navbar.component';
import { UserSettingsComponent } from './components/navbar-user-managament/user/user-settings-dialog/user-settings.component';
import { UserPasswordComponent } from './components/navbar-user-managament/user/user-password-dialog/user-password.component';
import { LoginFormComponent } from './components/navbar-user-managament/user/login-form/login-form.component';
import { RegisterFormComponent } from './components/navbar-user-managament/user/register-form/register-form.component';
import {authInterceptorProviders} from "./services/auth/auth-interceptor.service";
import { StartPageComponent } from './components/start-page/start-page.component';
import { Connect4CardComponent } from './components/game-chooser/connect4-card/connect4-card.component';
import { TicTacToeCardComponent } from './components/game-chooser/tic-tac-toe-card/tic-tac-toe-card.component';




@NgModule({
  declarations: [
    AppComponent,
    ChatComponent,
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
    UserSettingsComponent,
    UserPasswordComponent,
    LoginFormComponent,
    RegisterFormComponent,
    StartPageComponent,
    Connect4CardComponent,
    TicTacToeCardComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    HttpClientModule,
    MatDialogModule,
    MatMenuModule,
    MatFormFieldModule,
    MatButtonModule,
    MatRadioModule,
    MatCheckboxModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatOptionModule,
    MatIconModule,
    MatExpansionModule,
    MatInputModule,
    MatCardModule,
    MatNativeDateModule,
    ToastrModule.forRoot(),
  ],
  providers: [
    authInterceptorProviders,
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    Connect4BoardComponent,
    TicTacToeBoardComponent,
    PlayingConfirmationDialogComponent,
    UserSettingsComponent,
    UserPasswordComponent
  ]
})
export class AppModule {
}
