import {Component, OnInit} from '@angular/core';
import {RoomService} from '../../services/room.service';
import {WebSocketService} from '../../services/web-socket.service';
import {Client} from 'stompjs';
import {Router} from "@angular/router";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css']
})
export class LobbyComponent implements OnInit {

  roomsIds: string[];
  private readonly stompClient: Client;

  constructor(private webSocketService: WebSocketService,
              private gamesService: RoomService,
              private router: Router) {
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
   this.gamesService.getRoomsId().subscribe(resp => this.roomsIds = resp);
   this.onConnected();
  }

  onConnected() {
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/rooms', payload => this.roomsIds = JSON.parse(payload.body));
    });
  }

  createRoom() {
    this.gamesService.createRoom().subscribe();
  }


  goToRoom(roomId: string) {
  this.router.navigateByUrl('/room', {state: {data: roomId}});
  }

}
