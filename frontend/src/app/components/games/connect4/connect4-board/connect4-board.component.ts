import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Connect4FieldContentDto, Connect4FieldContentType} from "../model/connect4-field-content-dto";
import {Client, Message} from "stompjs";
import {TicTacToeGameState} from "../../tictactoe/model/tic-tac-toe-game-state";
import {TicTacToeService} from "../../../../services/tic-tac-toe.service";
import {ToastrService} from "ngx-toastr";
import {Connect4GameState} from "../model/connect4-game-state";
import {Connect4Move, PlayerSign} from "../model/connect4-move";
import {GameComponent} from "../../game-component";

@Component({
  selector: 'app-connect4-board',
  templateUrl: './connect4-board.component.html',
  styleUrls: ['./connect4-board.component.css']
})
export class Connect4BoardComponent implements OnInit, GameComponent {
  private static readonly ROW_NR: number = 6;
  private static readonly COLUMN_NR: number = 7;
  private board: Connect4FieldContentDto[][];
  @Input() stompClient: Client;
  @Output() endGameEmitter: EventEmitter<string> = new EventEmitter<string>();
  private roomId: string;
  private gameState: Connect4GameState;
  private exceptionMessage: string;
  private gameName = "Connect4"
  nextTurnPlayerNameEmitter: EventEmitter<number>;
  constructor(private ticTacToeService: TicTacToeService<Connect4GameState>,
              private toastr: ToastrService) { }

  ngOnInit() {
    this.board = Array.from(Array(Connect4BoardComponent.ROW_NR),
      () => Array(Connect4BoardComponent.COLUMN_NR)
        .fill({
          fieldContent: Connect4FieldContentType.EMPTY,
          inWinningLine: false
        }));
  }

  onConnected(roomId: string, playerSessionId: string) {
    console.log('child connected');
    this.roomId = roomId;
    this.stompClient.subscribe(`/app/ttt/${this.roomId}`, payload => this.onMessageReceived(payload));
    this.stompClient.subscribe(`/topic/ttt/${this.roomId}`, payload => this.onMessageReceived(payload));
    this.stompClient.subscribe('/user/topic/ttt/error', payload => this.onExceptionReceived(payload));
  }

  onMessageReceived(payload) {
    console.log(payload);
    const message = JSON.parse(payload.body);
    console.log(message);
    this.gameState = message;
    this.board = message.board;
    if (this.gameState.isWinner) {
      this.toastr.info('There\'s winner', '', {
        positionClass: 'toast-top-center',
      });
    } else if (this.gameState.isDraw) {
      this.toastr.info('It\'s draw', '', {
        positionClass: 'toast-top-center',
      });
    }
  }

  handleFieldClick(row: number, column: number) {
    console.log('clicked');
    this.stompClient.send(`/app/ttt/${this.roomId}`, {},
      JSON.stringify(new Connect4Move(column, PlayerSign.RED))
    );
  }

  handleReset() {
    this.ticTacToeService.resetGame(this.roomId).subscribe(gameState => {
      this.gameState = gameState;
      this.board = this.gameState.board;
    });
  }

  private onExceptionReceived(err: Message) {
    console.log(err);
    this.toastr.error(err.body, '', {
      positionClass: 'toast-top-center',
    });
  }


}
