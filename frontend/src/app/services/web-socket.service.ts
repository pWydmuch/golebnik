import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
// import * as SockJS from 'sockjs-client';
import * as SockJS from '../../assets/sockjs.min.js';
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  constructor() { }

  public connect() {

    const socket = SockJS(`${environment.basicUrl}/ws`);
    // console.log(socket.url)
    // const sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
    // const sessionId = socket.sessionId;
    console.log(socket);
    const stg  = JSON.parse(JSON.stringify(socket));
    console.log(stg._transport);
    // console.log(socket._transport)
    const stompClient = Stomp.over(socket);

    console.log(stompClient);
    return stompClient;
}
}

