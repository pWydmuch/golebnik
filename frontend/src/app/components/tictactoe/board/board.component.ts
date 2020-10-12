import {Component, OnInit} from '@angular/core';
import {Client} from 'stompjs';
import {FieldContent, PlayerSign, TicTacToeMove} from '../../../models/tic-tac-toe-move';
import {WebSocketService} from '../../../services/web-socket.service';


@Component({
  selector: 'app-tic-tac-toe-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {


  private static readonly ROW_NR: number = 3;
  private static readonly COLUMN_NR: number = 3;
  private readonly stompClient: Client;
  private board: FieldContent[][];

  constructor(private webSocketService: WebSocketService) {
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
    this.board = new Array(BoardComponent.ROW_NR)
        .fill(FieldContent.EMPTY)
        .map(() => new Array(BoardComponent.COLUMN_NR)
        .fill(FieldContent.EMPTY));
    console.log(this.board);
    this.onConnected();
  }

  onConnected() {
    this.stompClient.connect({}, () => {
        this.stompClient.subscribe('/topic/ttt', (payload) => this.onMessageReceived(payload));
      },
      this.onError);
  }

  onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    this.board = message;
    console.log(message);
  }

  onError(error) {
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
  }

  handleFieldClick(row: number, column: number) {
    this.stompClient.send('/app/ttt', {},
      JSON.stringify(new TicTacToeMove(row, column, PlayerSign.O))
    );
  }
}
