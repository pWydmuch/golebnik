export enum PlayerSign {
  X = 'X',
  O = 'O'
}

export enum FieldContent {
  X = 'X',
  O = 'O',
  EMPTY = 'EMPTY'
}

export class TicTacToeMove {
  row: number;
  column: number;
  playerSign: PlayerSign;


  constructor(row: number, column: number, playerSign: PlayerSign) {
    this.row = row;
    this.column = column;
    this.playerSign = playerSign;
  }
}
