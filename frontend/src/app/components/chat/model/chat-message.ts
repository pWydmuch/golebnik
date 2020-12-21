export enum  MessageType {
  CHAT = 'CHAT',
  JOIN = 'JOIN',
  LEAVE = 'LEAVE'
}

export class ChatMessage {
  type: MessageType;
  content: string;
  sender: string;
}
