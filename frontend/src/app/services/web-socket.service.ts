import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from '../../assets/sockjs.min.js';
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  constructor() { }

  public connect() {
    const socket = SockJS(`${environment.basicUrl}/ws`);
    return Stomp.over(socket);
}
}

