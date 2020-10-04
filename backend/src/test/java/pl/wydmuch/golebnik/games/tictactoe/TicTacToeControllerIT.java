package pl.wydmuch.golebnik.games.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicTacToeControllerIT {

    private static final String SEND_MESSAGE_ENDPOINT = "/app/ttt";
    private static final String SUBSCRIBE_TTT = "/topic/ttt";
    private static final String SUBSCRIBE_TTT_ERROR = "/topic/ttt/error";

    @LocalServerPort
    private int port;
    private CompletableFuture<TicTacToe> completableFuture;
    WebSocketStompClient stompClient;
    String URL = "ws://localhost:" + port + "/ws";

    @BeforeEach
    public void setup() {
        completableFuture = new CompletableFuture<>();
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void smokeTest() throws InterruptedException, ExecutionException, TimeoutException {
        StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {}).get(10, SECONDS);
        Move move = new Move(1,1,FieldContent.O);

        stompSession.subscribe(SUBSCRIBE_TTT, new TicTacToeStompFrameHandler());
        stompSession.send(SEND_MESSAGE_ENDPOINT, move);

        TicTacToe expectedGameState = new TicTacToe();
        expectedGameState.makeMove(move);
        TicTacToe gameAfterMove = completableFuture.get(10, SECONDS);

        assertThat(gameAfterMove).isEqualTo(expectedGameState);

    }

    @Test
    public void smokeTest_2_users() throws InterruptedException, ExecutionException, TimeoutException {
        StompSession userSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {}).get(10, SECONDS);
        StompSession otherUserSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {}).get(10, SECONDS);
        Move move = new Move(1,1,FieldContent.O);

        userSession.subscribe(SUBSCRIBE_TTT, new TicTacToeStompFrameHandler());
        userSession.send(SEND_MESSAGE_ENDPOINT, move);

        TicTacToe expectedGameState = new TicTacToe();
        expectedGameState.makeMove(move);
        TicTacToe gameAfterMove = completableFuture.get(10, SECONDS);

        assertThat(gameAfterMove).isEqualTo(expectedGameState);

        Move move2 = new Move(1,1,FieldContent.X);
        userSession.send(SEND_MESSAGE_ENDPOINT, move2);
        TicTacToe gameAfterMov2e = completableFuture.get(10, SECONDS);
        System.out.println(gameAfterMov2e);
    }


    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class TicTacToeStompFrameHandler implements StompFrameHandler {

        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            System.out.println(stompHeaders.toString());
            return TicTacToe.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            System.out.println((TicTacToe) o);
            completableFuture.complete((TicTacToe) o);
        }
    }
}