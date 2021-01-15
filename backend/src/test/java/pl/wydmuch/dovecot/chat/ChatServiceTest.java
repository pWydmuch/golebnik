package pl.wydmuch.dovecot.chat;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


class ChatServiceTest {

    @Test
    void getMessages_shouldReturnChatMessagesFromChat_whenChatIdIsGiven() {
        Map<String, List<ChatMessage>> chats = new HashMap<>();
        String chat1 = "chat1";
        List<ChatMessage> chatMessages = getChatMessages();
        chats.put(chat1, chatMessages);
        chats.put("chat2", getChatMessages());
        ChatService chatService = new ChatService(chats);
        List<ChatMessage> messages = chatService.getMessages(chat1);
        assertThat(messages).isEqualTo(chatMessages);
    }

    @Test
    void getMessages_shouldReturnEmptyList_whenGivenChatIdIsNotKnown() {
        Map<String, List<ChatMessage>> chats = new HashMap<>();
        String chat1 = "chat1";
        List<ChatMessage> chatMessages = getChatMessages();
        chats.put(chat1, chatMessages);
        chats.put("chat2", getChatMessages());
        ChatService chatService = new ChatService(chats);
        List<ChatMessage> messages = chatService.getMessages("NOT KNOWN ID");
        assertThat(messages).isEmpty();
    }

    @Test
    void addMessage_shouldAddMessageToChatMessages_whenChatIdGiven() {
        Map<String, List<ChatMessage>> chats = new HashMap<>();
        String chat1 = "chat1";
        List<ChatMessage> chatMessages = getChatMessages();
        chats.put(chat1, chatMessages);
        chats.put("chat2", getChatMessages());
        ChatService chatService = new ChatService(chats);
        ChatMessage newMessage = new ChatMessage();
        chatService.addMessage(chat1, newMessage);
        List<ChatMessage> messages = chatService.getMessages(chat1);
        assertThat(messages).containsAll(chatMessages)
                .contains(newMessage);
    }

    @Test
    void removeMessage_shouldRemoveMessagesFromChat_whenChatIdGiven() {
        Map<String, List<ChatMessage>> chats = new HashMap<>();
        String chat1 = "chat1";
        List<ChatMessage> chatMessages = getChatMessages();
        chats.put(chat1, chatMessages);
        chats.put("chat2", getChatMessages());
        ChatService chatService = new ChatService(chats);
        assertThat(chatService.getMessages(chat1)).isNotEmpty();
        chatService.removeMessages(chat1);
        assertThat(chatService.getMessages(chat1)).isEmpty();
    }

    
    private ArrayList<ChatMessage> getChatMessages() {
        ChatMessage cm1= new ChatMessage(ChatMessage.MessageType.CHAT,"HI","JoeDoe");
        ChatMessage cm2 = new ChatMessage();
        return new ArrayList<>(Arrays.asList(cm1, cm2));
    }


}