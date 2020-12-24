import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {RoomDto} from "../components/room-dto";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private url: string = environment.basicUrl + '/rooms';

  constructor(private http: HttpClient) {
  }

  getRoomsId(): Observable<RoomDto[]> {
    return this.http.get<RoomDto[]>(this.url);
  }

  createRoom(gameName: string): Observable<any> {
    return this.http.post(`${this.url}/${gameName}`, null);
  }

  removeRoom(roomId: string): Observable<any> {
    return this.http.delete(`${this.url}/${roomId}`);
  }

  addPlayer(roomId: string, playerSessionId: string, playerNumber: string): Observable<any> {

    return this.http.post(`${this.url}/${roomId}/players/${playerSessionId}`, null, {params: {playerNumber}});
  }

  removePlayer(roomId: string, playerSessionId: string) {
    return this.http.delete(`${this.url}/${roomId}/players/${playerSessionId}`);
  }

  getSitPlayers(roomId: string): Observable<any> {
    return this.http.get(`${this.url}/${roomId}/players`);
  }
}
