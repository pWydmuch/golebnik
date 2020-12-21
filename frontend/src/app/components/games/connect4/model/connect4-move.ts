export enum PlayerSign {
  RED = 'RED',
  BLACK = 'BLACK'
}

export class Connect4Move {
  column: number;
  playerSign: PlayerSign;

  constructor(column: number, playerSign: PlayerSign) {
    this.column = column;
    this.playerSign = playerSign;
  }
}
