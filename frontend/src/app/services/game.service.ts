import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {TicTacToeGameState} from "../models/tic_tac_toe/tic-tac-toe-game-state";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

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
