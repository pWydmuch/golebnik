import {FieldContentDto} from './field-content-type';

export class TicTacToeGameState {
  board: FieldContentDto[][];
  winner: boolean;
  draw: boolean;
}
