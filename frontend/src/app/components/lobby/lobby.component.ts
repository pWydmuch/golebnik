import {Component, OnInit} from '@angular/core';
import {RoomService} from '../../services/room.service';
import {WebSocketService} from '../../services/web-socket.service';
import {Client} from 'stompjs';
import {Router} from "@angular/router";
import {TicTacToeBoardComponent} from "../games/tictactoe/tic-tac-toe-board/tic-tac-toe-board.component";
import {RoomDto} from "../room-dto";
import {Connect4BoardComponent} from "../games/connect4/connect4-board/connect4-board.component";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css']
})
export class LobbyComponent implements OnInit {

  roomsDtos: RoomDto[];
  private readonly stompClient: Client;


  constructor(private webSocketService: WebSocketService,
              private gamesService: RoomService,
              private router: Router) {
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
    this.gamesService.getRoomsId().subscribe(resp => this.roomsDtos = resp);
    this.onConnected();
  }

  onConnected() {
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/rooms', payload => this.roomsDtos = JSON.parse(payload.body));
    });
  }

  createRoom(gameType: string) {
    this.gamesService.createRoom(gameType).subscribe();
  }

  goToRoom(roomId: string) {
    const activityManagerId = this.roomsDtos.find(r => r.roomId === roomId).activityManagerId;


    this.router.navigateByUrl('/room', {state: {roomId, activityManagerId, playersNumber: 2}});
  }

  removeRoom(roomId: string) {
    this.gamesService.removeRoom(roomId).subscribe();
  }
}
