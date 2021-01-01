import {Component, OnInit} from '@angular/core';
import {RoomService} from '../../services/room.service';
import {WebSocketService} from '../../services/web-socket.service';
import {Client} from 'stompjs';
import {Router} from "@angular/router";
import {TicTacToeBoardComponent} from "../games/tictactoe/tic-tac-toe-board/tic-tac-toe-board.component";
import {RoomDto} from "../room/room-dto";
import {Connect4BoardComponent} from "../games/connect4/connect4-board/connect4-board.component";
import {Location} from "@angular/common";

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.css']
})
export class LobbyComponent implements OnInit {

  roomsDtos: RoomDto[];
  private gameName: string;
  private playersNumber: number;
  private readonly stompClient: Client;


  constructor(private webSocketService: WebSocketService,
              private gamesService: RoomService,
              private router: Router,
              private location: Location) {
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
    this.playersNumber = history.state.playersNumber;
    this.gameName  = history.state.gameName;
    console.log(this.gameName)
    this.gamesService.getRoomsId(this.gameName).subscribe(resp => this.roomsDtos = resp);
    this.onConnected();
  }

  onConnected() {
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(`/topic/${this.gameName}/rooms`, payload => this.roomsDtos = JSON.parse(payload.body));
    });
  }

  createRoom() {
    this.gamesService.createRoom(this.gameName).subscribe();
  }

  goToRoom(roomId: string) {
    const gameName = this.gameName;
    const playersNumber = this.playersNumber;
    this.router.navigateByUrl('/room', {state: {roomId, gameName, playersNumber}});
  }

  removeRoom(roomId: string) {
    this.gamesService.removeRoom(roomId).subscribe();
  }

  goBack() {
      this.location.back();
  }
}
