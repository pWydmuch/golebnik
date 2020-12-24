import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {TicTacToeGameState} from "../components/games/tictactoe/model/tic-tac-toe-game-state";

@Injectable({
  providedIn: 'root'
})
export class TicTacToeService<C> {

  private url: string = environment.basicUrl + '/rooms';

  constructor(private http: HttpClient) { }

  resetGame(roomId : string): Observable<C> {
   return this.http.delete<C>(`${this.url}/${roomId}/game`);
  }

}
