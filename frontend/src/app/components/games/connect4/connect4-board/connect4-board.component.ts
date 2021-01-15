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
export class Connect4BoardComponent extends GameComponent implements OnInit {
  private static readonly ROW_NR: number = 6;
  private static readonly COLUMN_NR: number = 7;
  board: Connect4FieldContentDto[][];
   roomId: string;
  gameState: Connect4GameState;
  private exceptionMessage: string;
  private gameName = "Connect4"

  playerName: string;
  constructor(private ticTacToeService: TicTacToeService<Connect4GameState>,
              toastr: ToastrService) {
    super(toastr);
  }

  ngOnInit() {
    this.board = Array.from(Array(Connect4BoardComponent.ROW_NR),
      () => Array(Connect4BoardComponent.COLUMN_NR)
        .fill({
          fieldContent: Connect4FieldContentType.EMPTY,
          inWinningLine: false
        }));
  }




  handleFieldClick(row: number, column: number) {
    console.log('clicked');
    this.stompClient.send(`/app/ttt/${this.roomId}/${this.playerName}`, {},
      JSON.stringify(new Connect4Move(column, null))
    );
  }

}
