import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TicTacToeMove} from '../models/tic_tac_toe/tic-tac-toe-move';
import {TicTacToeGameState} from '../models/tic_tac_toe/tic-tac-toe-game-state';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private url: string = environment.basicUrl + '/rooms';

  constructor(private http: HttpClient) { }

  getRoomsId(): Observable<string[]> {
    return this.http.get<string[]>(this.url);
  }

  createRoom(): Observable<any> {
    return this.http.post(this.url, null);
  }

  addPlayer(roomId: string, playerSessionId: string): Observable<any>{

    return this.http.post(`${this.url}/${roomId}/players/${playerSessionId}`,null);
  }

  removePlayer(roomId: string, playerSessionId: string) {
    return this.http.delete(`${this.url}/${roomId}/players/${playerSessionId}`);
  }
}
