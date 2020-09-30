import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  constructor() { }

  public connect() {

    const socket = new SockJS(`http://localhost:8080/ws`);

    const stompClient = Stomp.over(socket);
    console.log(stompClient)
    return stompClient;
}
}

