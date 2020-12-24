import {Client} from "stompjs";

export interface GameComponent {
  stompClient: Client;
  onConnected(roomId: string, playerSessionId: string);
}
