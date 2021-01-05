import {Client} from "stompjs";
import {EventEmitter, Input, Output} from "@angular/core";
import {ToastrService} from "ngx-toastr";

export abstract class GameComponent {

  @Input() stompClient: Client;
  @Output() endGameEmitter: EventEmitter<string> = new EventEmitter<string>();
  @Output() nextTurnPlayerNameEmitter: EventEmitter<number> = new EventEmitter<number>();

  gameState: any;
  board: any
  private playerName: string;
  private roomId: string;

  constructor(private toastr: ToastrService){}

  onConnected(roomId: string, playerName: string) {
    console.log('child connected');
    this.playerName = playerName;
    this.roomId = roomId;
    this.stompClient.subscribe(`/app/ttt/${this.roomId}`, payload => this.onMessageReceived(payload));
    this.stompClient.subscribe(`/topic/ttt/${this.roomId}`, payload => this.onMessageReceived(payload));
  }

  onMessageReceived(payload) {
    console.log(payload);
    const message = JSON.parse(payload.body);
    console.log(message);
    this.gameState = message;
    this.board = message.board;
    if (this.gameState.isWinner) {
      this.toastr.info('There\'s a winner', '', {
        positionClass: 'toast-top-center',
      });
      this.endGameEmitter.emit("Winner");

    } else if (this.gameState.isDraw) {
      this.toastr.info('It\'s draw', '', {
        positionClass: 'toast-top-center',
      });

      this.endGameEmitter.emit("Draw");

    }
    this.nextTurnPlayerNameEmitter.emit(this.gameState.nextTurnPlayerNumber);

  }
}
