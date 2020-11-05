export enum FieldContentType {
  X = 'X',
  O = 'O',
  EMPTY = 'EMPTY'
}

export class FieldContentDto {
  fieldContent : FieldContentType;
  inWinningLine : boolean;
}

