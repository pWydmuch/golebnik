import {TicTacToeFieldContentDto} from './tic-tac-toe-field-content-type';

export class TicTacToeGameState {
  board: TicTacToeFieldContentDto[][];
  isWinner: boolean;
  isDraw: boolean;
  nextTurnPlayerNumber: number;
}
