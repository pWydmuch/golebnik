import {Component, OnInit, ViewChild} from '@angular/core';
import {RoomService} from '../../services/room.service';
import {ActivatedRoute} from '@angular/router';
import {WebSocketService} from '../../services/web-socket.service';
import {Client, Message} from 'stompjs';
import {ChatComponent} from '../chat/chat/chat.component';
import { Location } from '@angular/common';
import {TicTacToeBoardComponent} from "../games/tictactoe/tic-tac-toe-board/tic-tac-toe-board.component";
import {Connect4BoardComponent} from "../games/connect4/connect4-board/connect4-board.component";


@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {
  private roomId: string;
  private stompClient: Client;
  private playerSessionId: string;
  private players: any[];
  private playersNumber: number;
  @ViewChild(Connect4BoardComponent,  {static: false}) boardComp: Connect4BoardComponent;
  @ViewChild(ChatComponent,  {static: false}) chatComp: ChatComponent;
  playerButtonTexts: string[];



  constructor(private webSocketService: WebSocketService,
              private roomService: RoomService,
              private route: ActivatedRoute,
              private location: Location) {
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
    this.roomId = history.state.roomId;
    this.playersNumber = history.state.playersNumber;
    this.playerButtonTexts = new Array(this.playersNumber).fill('sit down');
    this.connect();
    this.roomService.getSitPlayers(this.roomId).subscribe(players => this.changePlayersButtonText(players));
  }

  connect() {
    this.stompClient.connect({}, () => {
    this.playerSessionId = /\/([^\/]+)\/websocket/.exec((this.stompClient.ws as any)._transport.url)[1];
    this.boardComp.onConnected(this.roomId, this.playerSessionId);
    this.chatComp.onConnected(this.roomId, this.playerSessionId);
    this.subscribeToPlayers();
    }, this.onError);
  }


  addPlayer(playerNumber: string) {
    console.log('add player');
    this.roomService.addPlayer(this.roomId, this.playerSessionId, playerNumber)
      .subscribe(() => this.playerButtonTexts[playerNumber] = this.playerSessionId);
  }

  removePlayer() {
    this.roomService.removePlayer(this.roomId, this.playerSessionId).subscribe();
  }

  onError(error) {
    console.log('Something went wrong');
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
  }




  goBack() {
    this.location.back();
    
  }

  private subscribeToPlayers() {
    this.stompClient.subscribe(`/topic/rooms/${this.roomId}/players`,
        payload => this.changePlayersButtonText(JSON.parse(payload.body)));
  }

  private changePlayersButtonText(players: any[]) {
    this.players = players
    console.log(this.players);
    for (let i = 0; i < this.playersNumber; i++) {
      console.log("players sessino"+this.players[i])
      this.playerButtonTexts[i] = this.players[i] ? this.players[i].name : "sit down";
    }
  }
}
