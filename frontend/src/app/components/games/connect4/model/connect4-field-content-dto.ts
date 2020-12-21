
export enum Connect4FieldContentType {
  RED = 'RED',
  BLACK = 'BLACK',
  EMPTY = 'EMPTY'
}

export class Connect4FieldContentDto {
  fieldContent : Connect4FieldContentType;
  inWinningLine : boolean;
}
