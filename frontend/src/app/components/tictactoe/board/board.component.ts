import {Component, Input, OnInit} from '@angular/core';
import {Client, Message} from 'stompjs';
import {PlayerSign, TicTacToeMove} from '../../../models/tic_tac_toe/tic-tac-toe-move';
import {WebSocketService} from '../../../services/web-socket.service';
import {FieldContentDto, FieldContentType} from '../../../models/tic_tac_toe/field-content-type';
import {TicTacToeGameState} from '../../../models/tic_tac_toe/tic-tac-toe-game-state';
import {TicTacToeService} from '../../../services/tic-tac-toe.service';
import {ActivatedRoute} from '@angular/router';
import {RoomService} from '../../../services/room.service';
import {GameService} from '../../../services/game.service';
import {ToastrService} from 'ngx-toastr';


@Component({
  selector: 'app-tic-tac-toe-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  private static readonly ROW_NR: number = 3;
  private static readonly COLUMN_NR: number = 3;
  @Input() private stompClient: Client;
  private roomId: string;
  private board: FieldContentDto[][];
  private gameState: TicTacToeGameState;
  private exceptionMessage: string;

  constructor(private gamesService: GameService,
              private ticTacToeService: TicTacToeService,
              private toastr: ToastrService) {

  }

  ngOnInit() {
    this.board = Array.from(Array(BoardComponent.ROW_NR),
      () => Array(BoardComponent.COLUMN_NR)
        .fill({
          fieldContent: FieldContentType.EMPTY,
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
    console.log('received');
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
      JSON.stringify(new TicTacToeMove(row, column, PlayerSign.O))
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
