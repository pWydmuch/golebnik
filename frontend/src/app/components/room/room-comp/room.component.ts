import {Component, ComponentFactoryResolver, OnInit, Type, ViewChild} from '@angular/core';
import {RoomService} from '../../../services/room.service';
import {ActivatedRoute, Router} from '@angular/router';
import {WebSocketService} from '../../../services/web-socket.service';
import {Client, Message} from 'stompjs';
import {ChatComponent} from '../../chat/chat-comp/chat.component';
import {Location} from '@angular/common';
import {GameDirective} from "../../games/game.directive";
import {GameComponent} from "../../games/game-component";
import {TicTacToeBoardComponent} from "../../games/tictactoe/tic-tac-toe-board/tic-tac-toe-board.component";
import {Connect4BoardComponent} from "../../games/connect4/connect4-board/connect4-board.component";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {PlayingConfirmationDialogComponent} from "../playing-confirmation-dialog/playing-confirmation-dialog.component";
import {PlayerDto} from "../player-dto";
import {ToastrService} from "ngx-toastr";
import {compileModuleFactory} from "@angular/compiler/src/render3/r3_module_factory_compiler";
import {TokenStorageService} from "../../../services/auth/token-storage.service";


@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {
  private roomId: string;
  private stompClient: Client;
  private userName: string;
  private players: PlayerDto[];
  private playersNumber: number;
  private nextTurnPlayerName: string;
  @ViewChild(GameDirective, {static: true}) gameDirective: GameDirective;
  @ViewChild(ChatComponent, {static: false}) chatComp: ChatComponent;
  playerButtonTexts: string[];
  private gameName: string;

  constructor(private componentFactoryResolver: ComponentFactoryResolver,
              private webSocketService: WebSocketService,
              private roomService: RoomService,
              private route: ActivatedRoute,
              public dialog: MatDialog,
              private toastr: ToastrService,
              public tokenService: TokenStorageService,
              private router: Router) {
    this.stompClient = this.webSocketService.connect();
    this.userName = this.tokenService.getUser();
  }

  ngOnInit() {
    this.roomId = history.state.roomId;
    this.playersNumber = history.state.playersNumber;
    const activityManagerId = history.state.gameName;
    let gameType;
    this.gameName = activityManagerId;
    if (activityManagerId === "TicTacToe") gameType = TicTacToeBoardComponent;
    if (activityManagerId === "Connect4") gameType = Connect4BoardComponent;
    this.playerButtonTexts = new Array(this.playersNumber).fill('sit down');
    this.connect(gameType);
    this.roomService.getSitPlayers(this.roomId).subscribe(players => {
      this.players = players
      this.changePlayersButtonText()
    });
  }

  connect(gameType: Type<any>) {
    this.stompClient.connect({}, () => {
      // this.userName = /\/([^\/]+)\/websocket/.exec((this.stompClient.ws as any)._transport.url)[1];
      this.loadComponent(gameType)
      this.chatComp.onConnected(this.roomId, this.userName);
      this.subscribeToPlayers();
      this.stompClient.subscribe('/user/topic/ttt/error', payload => this.onExceptionReceived(payload));
      this.stompClient.subscribe(`/topic/ttt/${this.roomId}/started`, payload => this.toastr.info(payload.body, '', {
        positionClass: 'toast-top-center',
      }));
    }, this.onError);
  }

  loadComponent(gameComponent: Type<any>) {
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(gameComponent);
    const viewContainerRef = this.gameDirective.viewContainerRef;
    viewContainerRef.clear();
    const componentRef = viewContainerRef.createComponent<GameComponent>(componentFactory);
    componentRef.instance.stompClient = this.stompClient;
    componentRef.instance.onConnected(this.roomId, this.userName);
    componentRef.instance.endGameEmitter.subscribe(() => {
      if (this.userInGame()) {
        this.openModal()
      }
    });
    componentRef.instance.nextTurnPlayerNameEmitter.subscribe(nextPlayerName => {
      console.log(nextPlayerName);
      this.nextTurnPlayerName = nextPlayerName;
    });
  }

  addPlayer(playerNumber: string) {
    console.log('add player');
    if (!this.userInGame()) {
      this.roomService.addPlayer(this.roomId, this.userName, playerNumber)
        .subscribe(res => {
            console.log(res)
          },
          err => {
            console.log(err)
            this.toastr.error(err.error.text, '', {
              positionClass: 'toast-top-center',
            });
          }
        );
    } else {
      this.toastr.info("You are currently sitting", '', {
        positionClass: 'toast-top-center',
      });
    }
  }

  removePlayer(event) {
    event.stopPropagation();
    this.roomService.removePlayer(this.roomId, this.userName).subscribe(
      res => {
        console.log(res)
      },
      err => {
        console.log(err)
        this.toastr.error(err.error.text, '', {
            positionClass: 'toast-top-center',
          }
        );
      }
    );
  }

  onError(error) {
    console.log('Something went wrong');
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
  }


  goBack() {
    if (this.userInGame()) {
      this.toastr.error("You can't leave if you've taken a sit", '', {
        positionClass: 'toast-top-center',
      });
    } else {
      const gameName = this.gameName;
      this.router.navigateByUrl('/lobby', {state: {gameName, playersNumber: 2}});
    }
  }

  showRemove(playerNumber: number) {
    return this.playerButtonTexts[playerNumber] === this.userName;
  }

  private userInGame() {
    return this.players.filter(p => p !== null)
      .map(p => p.name)
      .includes(this.userName);
  }

  private changePlayersButtonText() {
    console.log(this.players);
    for (let i = 0; i < this.playersNumber; i++) {
      console.log("players sessino" + this.players[i])
      this.playerButtonTexts[i] = this.players[i] ? this.players[i].name : "sit down";
    }
  }

  private subscribeToPlayers() {
    this.stompClient.subscribe(`/topic/rooms/${this.roomId}/players`,
      payload => {
        this.players = JSON.parse(payload.body);
        this.changePlayersButtonText()
        if (this.allPlayersSatDown() && this.userInGame()) this.openModal();
        else this.dialog.closeAll();
      });
  }

  private allPlayersSatDown() {
    return this.players.filter(p => p !== null).length === this.playersNumber;
  }

  private openModal() {
    const dialogConfig = new MatDialogConfig();
    // The user can't close the dialog by clicking outside its body
    dialogConfig.disableClose = true;
    // dialogConfig.id = "modal-component";
    dialogConfig.height = "150px";
    dialogConfig.width = "400px";
    const roomId = this.roomId;
    const playerSessionId = this.userName;
    const playersNumber = this.playersNumber;
    dialogConfig.data = {
      roomId,
      playerSessionId,
      playersNumber
    }
    this.dialog.open(PlayingConfirmationDialogComponent, dialogConfig);
  }

  private onExceptionReceived(err: Message) {
    console.log(err);
    this.toastr.error(err.body, '', {
      positionClass: 'toast-top-center',
    });
  }
}
