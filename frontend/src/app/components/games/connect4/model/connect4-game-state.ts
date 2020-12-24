import {Connect4FieldContentDto} from "./connect4-field-content-dto";


export class Connect4GameState {
  board: Connect4FieldContentDto[][];
  isWinner: boolean;
  isDraw: boolean;
}
