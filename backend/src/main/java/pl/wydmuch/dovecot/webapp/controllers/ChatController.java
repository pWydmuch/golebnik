package pl.wydmuch.dovecot.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.wydmuch.dovecot.chat.ChatMessage;
import pl.wydmuch.dovecot.chat.ChatService;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;

    @MessageMapping("/chat/{id}/send")
    @SendTo("/topic/chat/{id}")
    public List<ChatMessage> sendMessage(@Payload ChatMessage chatMessage,
                                         @DestinationVariable String id){
        System.out.println("received send message: " + chatMessage);
        chatService.addMessage(id,chatMessage);
        return chatService.getMessages(id);
    }

//    @MessageMapping("/chat/{id}/adduser")
//    @SendTo("/topic/chat/{id}")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage,
//                               SimpMessageHeaderAccessor headerAccessor) {
//        return chatMessage;
//    }

}
