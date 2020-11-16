import {FieldContentDto} from './field-content-type';

export class TicTacToeGameState {
  board: FieldContentDto[][];
  isWinner: boolean;
  isDraw: boolean;
}
