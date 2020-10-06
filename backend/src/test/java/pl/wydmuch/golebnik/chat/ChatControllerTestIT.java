package pl.wydmuch.golebnik.chat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerTestIT {

    private static final String SEND_MESSAGE_ENDPOINT = "/app/chat.sendMessage";
    private static final String ADD_USER_ENDPOINT = "/app/chat.addUser";
    private static final String SUBSCRIBE_PUBLIC = "/topic/public";

    @LocalServerPort
    private int port;
    private CompletableFuture<ChatMessage> completableFuture;
    private StompSession stompSession;

    @BeforeEach
    public void setup() throws InterruptedException, ExecutionException, TimeoutException {
        completableFuture = new CompletableFuture<>();
        String URL = "ws://localhost:" + port + "/ws";
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {}).get(10, SECONDS);
    }

    @Test
    public void message_shouldBeDeliveredToSubscriber_whenSent() throws InterruptedException, ExecutionException, TimeoutException {
        ChatMessage expectedMessage = new ChatMessage(ChatMessage.MessageType.CHAT,"hello","john");

        stompSession.subscribe(SUBSCRIBE_PUBLIC , new ChatMessageStompFrameHandler());
        stompSession.send(SEND_MESSAGE_ENDPOINT, expectedMessage);
        ChatMessage actualMessage = completableFuture.get(10, SECONDS);

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void testMakeMoveEndpoint() throws InterruptedException, ExecutionException, TimeoutException {
        String sender = "john";
        ChatMessage expectedMessage = new ChatMessage(ChatMessage.MessageType.CHAT,"hello", sender);

        stompSession.subscribe(SUBSCRIBE_PUBLIC , new ChatMessageStompFrameHandler());
        stompSession.send(ADD_USER_ENDPOINT, expectedMessage);
        ChatMessage actualMessage = completableFuture.get(10, SECONDS);

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class ChatMessageStompFrameHandler implements StompFrameHandler {

        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            System.out.println(stompHeaders.toString());
            return ChatMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            System.out.println((ChatMessage) o);
            completableFuture.complete((ChatMessage) o);
        }
    }
}