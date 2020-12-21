import { Injectable } from '@angular/core';
import {Observable} from "rxjs";

import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {TicTacToeGameState} from "../components/games/tictactoe/model/tic-tac-toe-game-state";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private url: string = environment.basicUrl + '/rooms';
  constructor(private http: HttpClient) { }

  getGame(gameId: string): Observable<TicTacToeGameState> {
    return this.http.get<TicTacToeGameState>(`${this.url}/${gameId}`);
  }
}
