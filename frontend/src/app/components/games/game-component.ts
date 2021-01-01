import {Client} from "stompjs";
import {EventEmitter, Output} from "@angular/core";

export interface GameComponent {
  stompClient: Client;
  endGameEmitter: EventEmitter<string>;
  nextTurnPlayerNameEmitter: EventEmitter<number>;
  onConnected(roomId: string, playerSessionId: string);
}
