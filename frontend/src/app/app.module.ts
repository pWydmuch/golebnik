import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {ChatComponent} from './components/chat/chat.component';
import {UserJoinFormComponent} from './components/user-join-form/user-join-form.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from '@angular/forms';
import { BoardComponent } from './components/tictactoe/board/board.component';
import { FieldComponent } from './components/tictactoe/field/field.component';
import {HttpClientModule} from "@angular/common/http";
import { LobbyComponent } from './components/lobby/lobby.component';
import { RoomComponent } from './components/room/room.component';
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { TimesPipe } from './pipes/times.pipe';

@NgModule({
  declarations: [
    AppComponent,
    ChatComponent,
    UserJoinFormComponent,
    BoardComponent,
    FieldComponent,
    LobbyComponent,
    RoomComponent,
    TimesPipe
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
