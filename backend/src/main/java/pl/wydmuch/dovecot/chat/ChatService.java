package pl.wydmuch.dovecot.chat;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private Map<String, List<ChatMessage>> roomsChats = new HashMap<>();

    public List<ChatMessage> getMessages(String roomId) {
        List<ChatMessage> chatMessages = roomsChats.get(roomId);
        if (chatMessages == null) {
            roomsChats.put(roomId, new ArrayList<>());
        }
        return chatMessages;
    }

    public void addMessage(String roomId,ChatMessage message) {
        List<ChatMessage> chatMessages = roomsChats.get(roomId);
        if (chatMessages == null) {
            chatMessages = new ArrayList<>();
            chatMessages.add(message);
            roomsChats.put(roomId, chatMessages);
        }else{
            chatMessages.add(message);
        }

    }
}
