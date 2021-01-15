package pl.wydmuch.dovecot.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatService {

    private Map<String, List<ChatMessage>> chats = new HashMap<>();

    public ChatService() {
    }

    public ChatService(Map<String, List<ChatMessage>> chats) {
        this.chats = chats;
    }

    public List<ChatMessage> getMessages(String chatId) {
        chats.putIfAbsent(chatId,new ArrayList<>());
        return chats.get(chatId);
    }

    public void removeMessages(String chatId){
        chats.remove(chatId);
    }

    public void addMessage(String chatId,ChatMessage message) {
        List<ChatMessage> chatMessages = chats.get(chatId);
        if (chatMessages == null) {
            chatMessages = new ArrayList<>();
            chatMessages.add(message);
            chats.put(chatId, chatMessages);
        }else{
            chatMessages.add(message);
        }
    }
}
