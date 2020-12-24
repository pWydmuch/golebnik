import {Component, ComponentFactoryResolver, OnInit, Type, ViewChild} from '@angular/core';
import {RoomService} from '../../services/room.service';
import {ActivatedRoute} from '@angular/router';
import {WebSocketService} from '../../services/web-socket.service';
import {Client} from 'stompjs';
import {ChatComponent} from '../chat/chat/chat.component';
import {Location} from '@angular/common';
import {GameDirective} from "../games/game.directive";
import {GameComponent} from "../games/game-component";
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
  @ViewChild(GameDirective, {static: true}) gameDirective: GameDirective;
  @ViewChild(ChatComponent, {static: false}) chatComp: ChatComponent;
  playerButtonTexts: string[];

  constructor(private componentFactoryResolver: ComponentFactoryResolver,
              private webSocketService: WebSocketService,
              private roomService: RoomService,
              private route: ActivatedRoute,
              private location: Location) {
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
    this.roomId = history.state.roomId;
    this.playersNumber = history.state.playersNumber;
    const  activityManagerId = history.state.activityManagerId;
    let gameType;
    if (activityManagerId === "TicTacToe") gameType = TicTacToeBoardComponent;
    if (activityManagerId === "Connect4") gameType = Connect4BoardComponent;
    this.playerButtonTexts = new Array(this.playersNumber).fill('sit down');
    this.connect(gameType);
    this.roomService.getSitPlayers(this.roomId).subscribe(players => this.changePlayersButtonText(players));
  }

  connect(gameType: Type<any>) {
    this.stompClient.connect({}, () => {
      this.playerSessionId = /\/([^\/]+)\/websocket/.exec((this.stompClient.ws as any)._transport.url)[1];
      this.loadComponent(gameType)
      this.chatComp.onConnected(this.roomId, this.playerSessionId);
      this.subscribeToPlayers();
    }, this.onError);
  }

  loadComponent(gameComponent: Type<any>) {
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(gameComponent);
    const viewContainerRef = this.gameDirective.viewContainerRef;
    viewContainerRef.clear();
    const componentRef = viewContainerRef.createComponent<GameComponent>(componentFactory);
    componentRef.instance.stompClient = this.stompClient;
    componentRef.instance.onConnected(this.roomId, this.playerSessionId);
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
