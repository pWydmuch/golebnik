export enum TicTacToeFieldContentType {
  X = 'X',
  O = 'O',
  EMPTY = 'EMPTY'
}

export class TicTacToeFieldContentDto {
  fieldContent : TicTacToeFieldContentType;
  inWinningLine : boolean;
}

