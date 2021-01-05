import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {WebSocketService} from 'src/app/services/web-socket.service';
import {ActivatedRoute} from '@angular/router';
import {ChatMessage, MessageType} from '../model/chat-message';
import {Client} from 'stompjs';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  @ViewChild('messageArea', {static: false}) messageArea: ElementRef;
  @ViewChild('messageInput', {static: false}) messageInput: ElementRef;
  @Input() private stompClient: Client;
  private playerSessionId: string;
  private roomId: string;
  private messages: ChatMessage[] = [];
  private messageToSend: ChatMessage = new ChatMessage();
  private readonly colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
  ];

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    // this.username = history.state.data;
    // this.onConnected();
  }

  onConnected(roomId: string, playerSessionId: string) {
    this.roomId = roomId;
    this.playerSessionId = playerSessionId;
    this.stompClient.subscribe(`/app/chat/${this.roomId}`, (payload) => this.onMessageReceived(payload));
    this.stompClient.subscribe(`/topic/chat/${this.roomId}`, (payload) => this.onMessageReceived(payload));

    // this.stompClient.send(`/app/chat/${this.roomId}/send`, {},
    //   JSON.stringify({sender: this.playerSessionId, type: MessageType.JOIN})
    // );
  }

  async onMessageReceived(payload) {
    const messages = JSON.parse(payload.body);
    this.messages = messages;
    await this.delay(75);
    this.messageArea.nativeElement.scrollTop = this.messageArea.nativeElement.scrollHeight;
  }

  private delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }


  onError(error) {
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
  }


  sendMessage(event) {
    if (this.messageToSend.content && this.stompClient) {
      this.messageToSend.type = MessageType.CHAT;
      this.messageToSend.sender = this.playerSessionId;
      this.stompClient.send(`/app/chat/${this.roomId}/send`, {}, JSON.stringify(this.messageToSend));
      this.messageToSend.content = '';
      this.messageInput.nativeElement.focus();
    }
  }

  getMessageContent(message: ChatMessage) {
    console.log(message)
    switch (message.type) {
      case MessageType.CHAT:
        return message.content;
      // case MessageType.JOIN:
      //   return message.sender + ' joined!'
      // case MessageType.LEAVE:
      //   message.content = message.sender + ' left!';
    }
  }

  getMessageClass(message: ChatMessage) {
    return {
      // 'event-message': message.type !== MessageType.CHAT,
      'chat-message': message.type === MessageType.CHAT
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
