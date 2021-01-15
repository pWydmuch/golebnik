import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Client} from "stompjs";
import {TicTacToeFieldContentDto, TicTacToeFieldContentType} from "../model/tic-tac-toe-field-content-type";
import {TicTacToeGameState} from "../model/tic-tac-toe-game-state";
import {TicTacToeService} from "../../../../services/tic-tac-toe.service";
import {ToastrService} from "ngx-toastr";
import {TicTacToeMove} from "../model/tic-tac-toe-move";
import {GameComponent} from "../../game-component";
import {MatDialog} from "@angular/material";

@Component({
  selector: 'app-tic-tac-toe-board',
  templateUrl: './tic-tac-toe-board.component.html',
  styleUrls: ['./tic-tac-toe-board.component.css']
})
export class TicTacToeBoardComponent extends GameComponent implements OnInit  {

  private static readonly ROW_NR: number = 3;
  private static readonly COLUMN_NR: number = 3;
  roomId: string;
  board: TicTacToeFieldContentDto[][];
  gameState: TicTacToeGameState;
  private exceptionMessage: string;
 playerName: string;

  constructor(private ticTacToeService: TicTacToeService<TicTacToeGameState>,
              toastr: ToastrService,
              public dialog: MatDialog) {

    super(toastr);

  }

  ngOnInit() {
    this.board = Array.from(Array(TicTacToeBoardComponent.ROW_NR),
      () => Array(TicTacToeBoardComponent.COLUMN_NR)
        .fill({
          fieldContent: TicTacToeFieldContentType.EMPTY,
          inWinningLine: false
        }));
  }

  // onConnected(roomId: string, playerName: string) {
  //   this.playerName = playerName;
  //   console.log('child connected');
  //   this.roomId = roomId;
  //   this.stompClient.subscribe(`/app/ttt/${this.roomId}`, payload => super.onMessageReceived(payload));
  //   this.stompClient.subscribe(`/topic/ttt/${this.roomId}`, payload => super.onMessageReceived(payload));
  //
  // }



  handleFieldClick(row: number, column: number) {
    this.stompClient.send(`/app/ttt/${this.roomId}/${this.playerName}`, {},
      JSON.stringify(new TicTacToeMove(row, column,null))
    );
  }


}
