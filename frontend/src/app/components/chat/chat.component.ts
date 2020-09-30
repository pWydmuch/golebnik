import {Component, OnInit} from '@angular/core';
import {WebSocketService} from 'src/app/services/web-socket.service';
import {ActivatedRoute} from '@angular/router';
import {ChatMessage, MessageType} from '../../models/chat-message';
import {Client} from 'stompjs';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  private readonly stompClient: Client;
  private username: string;
  private messages: ChatMessage[] = [];
  private messageToSend: ChatMessage = new ChatMessage();
  private readonly colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
  ];

  constructor(private webSocketService: WebSocketService, private route: ActivatedRoute) {
    this.stompClient = this.webSocketService.connect();
  }

  ngOnInit() {
    this.username = history.state.data;
    this.onConnected();
  }

  onConnected() {
    this.stompClient.connect({}, () => {
        this.stompClient.subscribe('/topic/public', (payload) => this.onMessageReceived(payload));
        this.stompClient.send('/app/chat.addUser', {},
          JSON.stringify({sender: this.username, type: MessageType.JOIN})
        );
      },
      this.onError);
  }

  onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    this.messages.push(message);
  }


  onError(error) {
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
  }


  sendMessage(event) {
    if (this.messageToSend.content && this.stompClient) {
      this.messageToSend.type = MessageType.CHAT;
      this.messageToSend.sender = this.username;
      this.stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(this.messageToSend));
      this.messageToSend.content = '';
    }
  }

  getMessageContent(message: ChatMessage){
    switch (message.type) {
      case MessageType.CHAT:
        return message.content;
      case MessageType.JOIN:
        return message.sender + ' joined!'
      case MessageType.LEAVE:
        message.content = message.sender + ' left!';
    }
  }

  getMessageClass(message: ChatMessage) {
    return {
      'event-message' : message.type !== MessageType.CHAT,
      'chat-message' : message.type === MessageType.CHAT
    };
  }


  getAvatarColor(message: ChatMessage) {
    const messageSender = message.sender;

    if (messageSender) {
      let hash = 0;
      for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
      }
      const index = Math.abs(hash % this.colors.length);
      return this.colors[index];
    }
  }


}
