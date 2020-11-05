import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TicTacToeGameState} from '../models/tic_tac_toe/tic-tac-toe-game-state';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TicTacToeService {

  private url: string = environment.basicUrl + '/rooms';

  constructor(private http: HttpClient) { }



  resetGame(roomId : string): Observable<TicTacToeGameState> {
   return this.http.delete<TicTacToeGameState>(`${this.url}/${roomId}/game`);
  }

}
