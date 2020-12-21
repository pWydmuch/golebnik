import { Component, OnInit } from '@angular/core';
import {Connect4FieldContentDto, Connect4FieldContentType} from "../model/connect4-field-content-dto";

@Component({
  selector: 'app-connect4-board',
  templateUrl: './connect4-board.component.html',
  styleUrls: ['./connect4-board.component.css']
})
export class Connect4BoardComponent implements OnInit {
  private static readonly ROW_NR: number = 6;
  private static readonly COLUMN_NR: number = 7;
  private board: Connect4FieldContentDto[][];

  constructor() { }

  ngOnInit() {
    this.board = Array.from(Array(Connect4BoardComponent.ROW_NR),
      () => Array(Connect4BoardComponent.COLUMN_NR)
        .fill({
          fieldContent: Connect4FieldContentType.EMPTY,
          inWinningLine: false
        }));
  }
}
