import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {ChatComponent} from './components/chat/chat.component';
import {UserJoinFormComponent} from './components/user-join-form/user-join-form.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule} from '@angular/forms';
import { BoardComponent } from './components/tictactoe/board/board.component';
import { FieldComponent } from './components/tictactoe/field/field.component';

@NgModule({
  declarations: [
    AppComponent,
    ChatComponent,
    UserJoinFormComponent,
    BoardComponent,
    FieldComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
