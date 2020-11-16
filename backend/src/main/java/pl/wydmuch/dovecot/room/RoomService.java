package pl.wydmuch.dovecot.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.gameplay.Game;
import pl.wydmuch.dovecot.games.tictactoe.*;

import java.util.*;

@Service
public class RoomService {

    private Map<String, Room> rooms = new HashMap<>();
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Set<String> getRoomsIds() {
        return rooms.keySet();
    }

    public void createGame(String roomId) {
        if (rooms.get(roomId).allPlayersArePresent() && rooms.get(roomId).getGame() == null) {
            System.out.println("Created game");
            TicTacToe game = new TicTacToe();
            rooms.get(roomId).setGame(game);
        }
    }

    public void makeMove(String roomId, Move move, SimpMessageHeaderAccessor headerAccessor) {
        Room room = rooms.get(roomId);
        TicTacToe game = room.getGame();
        String playerSessionId = headerAccessor.getSessionId();
        if (game != null && isPlayerInGame(playerSessionId, roomId)) {
            if (!game.firstMoveWasMade()) {
                int startingPlayerIndex = room.getStartingPlayerIndex();
                if (!room.getPlayers().get(startingPlayerIndex).getSessionId().equals(playerSessionId)) {
                    throw new RuntimeException("It's not your turn to start");
                }
            }
            Field.FieldContent playerSign = (Field.FieldContent) headerAccessor.getSessionAttributes().get("sign");
            if (Objects.isNull(playerSign)) {
                List<Field.FieldContent> availablePlayerSigns = game.getAvailableSigns();
                if (availablePlayerSigns.size() > 0) {
                    playerSign = availablePlayerSigns.get(0);
                    availablePlayerSigns.remove(0);
                }
                System.out.println("Room is null");

                headerAccessor
                        .getSessionAttributes()
                        .put("sign", playerSign);
            }
            move.setPlayerSign(playerSign);
            game.makeMove(move);

            System.out.println("Room sent " + game);
            GameDto gameDto = GameToDtoConverter.convertToDto(game);
            messagingTemplate.convertAndSend("/topic/ttt/" + roomId, gameDto);
            if (game.isGameEnded()) {
                room.changeStaringPlayerIndex();
                room.setGame(null);
            }

        } else if (game != null) {
            throw new RuntimeException("Game is not started yet");
        } else if (!isPlayerInGame(playerSessionId, roomId)) {
            throw new RuntimeException("You don't play");
        }
    }

    private boolean isPlayerInGame(String playerSessionId, String roomId) {
        return rooms.get(roomId).getPlayers().stream()
                .map(RoomUser::getSessionId)
                .anyMatch(s -> s.equals(playerSessionId));
    }

    public void createRoom() {
        Room room = new Room();
        room.setId(UUID.randomUUID().toString());
        rooms.put(room.getId(), room);
        messagingTemplate.convertAndSend("/topic/rooms", getRoomsIds());
    }

    public void deleteRoom(String roomId) {
        rooms.remove(roomId);
        messagingTemplate.convertAndSend("/topic/rooms", getRoomsIds());
    }

    public void addPlayer(String playerSessionId, String roomId, String playerName, int playerNumber) {
        TicTacToePlayer player = new TicTacToePlayer();
        player.setSessionId(playerSessionId);
        player.setName(playerName);
        Field.FieldContent playerSign = playerNumber == 0 ? Field.FieldContent.X : Field.FieldContent.O;
        player.setPlayerSign(playerSign);
        Room room = rooms.get(roomId);
        room.addPlayer(player, playerNumber);
        createGame(roomId);
        messagingTemplate.convertAndSend("/topic/rooms/"+roomId+"/players",room.getPlayers());
    }

    public void removePlayer(String playerSessionId, String roomId) {
        Room room = rooms.get(roomId);
        room.removePlayer(playerSessionId);
        messagingTemplate.convertAndSend("/topic/rooms/"+roomId+"/players",room.getPlayers() );
    }

    public Game getGame(String roomId) {
        return rooms.get(roomId).getGame();
    }

    public GameDto onSubscription(String roomId) {
        TicTacToe game = rooms.get(roomId).getGame() != null ? rooms.get(roomId).getGame() : new TicTacToe();
        return GameToDtoConverter.convertToDto(game);
    }

    public void resetGame(String roomId) {
        rooms.get(roomId).setGame(new TicTacToe());
        messagingTemplate.convertAndSend("/topic/ttt/" + roomId, GameToDtoConverter.convertToDto(new TicTacToe()));
    }

    public List<TicTacToePlayer> getSitPlayers(String roomId) {
        return rooms.get(roomId).getPlayers();
    }


//    public void start(String gameId){
//        TicTacToe toe = new TicTacToe();
//        toe.setId(gameId);
//        rooms.put(gameId,toe);
//    }
}
