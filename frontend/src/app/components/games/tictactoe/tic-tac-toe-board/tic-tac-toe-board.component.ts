import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Client, Message} from "stompjs";
import {TicTacToeFieldContentDto, TicTacToeFieldContentType} from "../model/tic-tac-toe-field-content-type";
import {TicTacToeGameState} from "../model/tic-tac-toe-game-state";
import {GameService} from "../../../../services/game.service";
import {TicTacToeService} from "../../../../services/tic-tac-toe.service";
import {ToastrService} from "ngx-toastr";
import {PlayerSign, TicTacToeMove} from "../model/tic-tac-toe-move";
import {GameComponent} from "../../game-component";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {PlayingConfirmationDialogComponent} from "../../../room/playing-confirmation-dialog/playing-confirmation-dialog.component";

@Component({
  selector: 'app-tic-tac-toe-board',
  templateUrl: './tic-tac-toe-board.component.html',
  styleUrls: ['./tic-tac-toe-board.component.css']
})
export class TicTacToeBoardComponent implements OnInit, GameComponent {

  private static readonly ROW_NR: number = 3;
  private static readonly COLUMN_NR: number = 3;
  @Input() stompClient: Client;
  @Output() endGameEmitter: EventEmitter<string> = new EventEmitter<string>();
  @Output() nextTurnPlayerNameEmitter: EventEmitter<number> = new EventEmitter<number>();
  private roomId: string;
  private board: TicTacToeFieldContentDto[][];
  private gameState: TicTacToeGameState;
  private exceptionMessage: string;

  constructor(private ticTacToeService: TicTacToeService<TicTacToeGameState>,
              private toastr: ToastrService,
              public dialog: MatDialog) {

  }

  ngOnInit() {
    this.board = Array.from(Array(TicTacToeBoardComponent.ROW_NR),
      () => Array(TicTacToeBoardComponent.COLUMN_NR)
        .fill({
          fieldContent: TicTacToeFieldContentType.EMPTY,
          inWinningLine: false
        }));
  }

  onConnected(roomId: string, playerSessionId: string) {
    console.log('child connected');
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
      this.toastr.info('There\'s winner', '', {
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

  handleFieldClick(row: number, column: number) {
    console.log('clicked');
    this.stompClient.send(`/app/ttt/${this.roomId}`, {},
      JSON.stringify(new TicTacToeMove(row, column, PlayerSign.O))
    );
  }

  handleReset() {
    this.ticTacToeService.resetGame(this.roomId).subscribe(gameState => {
      this.gameState = gameState;
      this.board = this.gameState.board;
    });
  }


}
