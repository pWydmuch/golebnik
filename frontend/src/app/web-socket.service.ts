import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';



@Injectable()
export class WebSocketService {

    // Open connection with the back-end socket
    public connect() {

        let socket = new SockJS(`http://localhost:8080/socket`);

        let stompClient = Stomp.over(socket);

        return stompClient;
    }
}