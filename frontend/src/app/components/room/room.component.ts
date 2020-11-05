import {Component, OnInit, ViewChild} from '@angular/core';
import {RoomService} from '../../services/room.service';
import {ActivatedRoute} from '@angular/router';
import {WebSocketService} from '../../services/web-socket.service';
import {Client} from 'stompjs';
import {BoardComponent} from "../tictactoe/board/board.component";
import {ChatComponent} from "../chat/chat.component";

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {
  private roomId: string;
  private stompClient: Client;
  private playerSessionId: string;
  @ViewChild(BoardComponent,  {static: false}) boardComp: BoardComponent;
  @ViewChild(ChatComponent,  {static: false}) chatComp: ChatComponent;

  constructor(private webSocketService: WebSocketService,
              private roomService: RoomService,
              private route: ActivatedRoute) {
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
    this.roomId = history.state.data;
    this.connect();
  }

  connect() {
    this.stompClient.connect({}, () => {
    this.playerSessionId = /\/([^\/]+)\/websocket/.exec(this.stompClient.ws._transport.url)[1];
    this.boardComp.onConnected(this.roomId, this.playerSessionId);
    this.chatComp.onConnected(this.roomId, this.playerSessionId);
    }, this.onError);
  }

  addPlayer() {
    console.log("add player")
    this.roomService.addPlayer(this.roomId, this.playerSessionId).subscribe();
  }

  onError(error) {
    console.log("Something went wrong")
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
  }


  removePlayer() {
    this.roomService.removePlayer(this.roomId, this.playerSessionId).subscribe();
  }
}
