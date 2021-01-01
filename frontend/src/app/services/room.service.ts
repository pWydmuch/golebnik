import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {RoomDto} from "../components/room/room-dto";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private url: string = environment.basicUrl + '/rooms';

  constructor(private http: HttpClient) {
  }

  getRoomsId(gameName: string): Observable<RoomDto[]> {
    return this.http.get<RoomDto[]>(`${environment.basicUrl}/${gameName}/rooms`);
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

  confirmPlayer(roomId: string, playerSessionId: string): Observable<any> {
    return this.http.put(`${this.url}/${roomId}/players/${playerSessionId}`, null);
  }

  removePlayer(roomId: string, playerSessionId: string) {
    return this.http.delete(`${this.url}/${roomId}/players/${playerSessionId}`);
  }

  getSitPlayers(roomId: string): Observable<any> {
    return this.http.get(`${this.url}/${roomId}/players`);
  }
}
