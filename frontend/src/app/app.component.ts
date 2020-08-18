import { Component } from '@angular/core';
import { WebSocketService } from './web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public notifications = 0;

    constructor(private webSocketService: WebSocketService) {

		// Open connection with server socket
        const stompClient = this.webSocketService.connect();
        stompClient.connect({}, frame => {

			// Subscribe to notification topic
            stompClient.subscribe('/topic/notification', notifications => {

				// Update notifications attribute with the recent messsage sent from the server
                this.notifications = JSON.parse(notifications.body).count;
            })
        });
}
}
