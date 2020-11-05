package pl.wydmuch.dovecot.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.gameplay.Game;
import pl.wydmuch.dovecot.gameplay.Player;
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
        if (rooms.get(roomId).allPlayersArePresent()){
            System.out.println("Created game");
            TicTacToe game = new TicTacToe();
            rooms.get(roomId).setGame(game);
        }
    }

    public void makeMove(String roomId, Move move, SimpMessageHeaderAccessor headerAccessor){
        TicTacToe game =  rooms.get(roomId).getGame();
        String playerSessionId = headerAccessor.getSessionId();
        System.out.println(playerSessionId);
        if (game != null && isPlayerInGame(playerSessionId,roomId) ) {
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
        }else if(game != null){
            throw new RuntimeException("Game is not started yet");
        }
        else{
            throw new RuntimeException("You don't play");
        }
    }

    private boolean isPlayerInGame(String playerSessionId,String roomId){
        return rooms.get(roomId).getPlayers().stream()
                .map(RoomUser::getSessionId)
                .anyMatch(s->s.equals(playerSessionId));
    }

    public void createRoom() {
        Room room = new Room();
        room.setId(UUID.randomUUID().toString());
        rooms.put(room.getId(), room);
        messagingTemplate.convertAndSend("/topic/rooms", getRoomsIds());
    }

    public void addPlayer(String playerSessionId, String roomId, String playerName) {
        RoomUser player = new RoomUser();
        player.setSessionId(playerSessionId);
        player.setName(playerName);
        rooms.get(roomId).addPlayer(player);
        createGame(roomId);
    }

    public void removePlayer(String playerSessionId, String roomId){
        rooms.get(roomId).removePlayer(playerSessionId);
    }

    public Game getGame(String roomId) {
        return rooms.get(roomId).getGame();
    }

    public GameDto onSubscription(String roomId) {
       TicTacToe game =  rooms.get(roomId).getGame() != null ? rooms.get(roomId).getGame() : new TicTacToe();
       return GameToDtoConverter.convertToDto(game);
    }

    public void resetGame(String roomId) {
        rooms.get(roomId).setGame(new TicTacToe());
        messagingTemplate.convertAndSend("/topic/ttt/"+roomId,GameToDtoConverter.convertToDto(new TicTacToe()));
    }

//    public void start(String gameId){
//        TicTacToe toe = new TicTacToe();
//        toe.setId(gameId);
//        rooms.put(gameId,toe);
//    }
}
