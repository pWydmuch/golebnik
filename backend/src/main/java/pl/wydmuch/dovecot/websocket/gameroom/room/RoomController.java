package pl.wydmuch.dovecot.websocket.gameroom.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wydmuch.dovecot.games.connect4.engine.Connect4Move;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameState;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeMove;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;

import java.util.List;
import java.util.Set;

@Controller
@CrossOrigin("*")
public class RoomController {

    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public RoomController(RoomService roomService,SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/rooms/{gameName}")
    @ResponseBody
    public void createRoom(@PathVariable String gameName){
        roomService.createRoom(gameName);
        messagingTemplate.convertAndSend("/topic/rooms", roomService.getRoomsIds());
    }

    @DeleteMapping("/rooms/{roomId}")
    @ResponseBody
    public void deleteRoom(@PathVariable String roomId){
        roomService.deleteRoom(roomId);
        messagingTemplate.convertAndSend("/topic/rooms", roomService.getRoomsIds());
    }

//    @GetMapping("/rooms/{roomId}")
//    @ResponseBody
//    public TicTacToeGameState getGameManager(@PathVariable String roomId){
//
//    }
    @SubscribeMapping("/ttt/{roomId}")
    public GameState onSubscription(@DestinationVariable String roomId){
        System.out.println("Subbb");
        return roomService.onSubscription(roomId);
    }

    @MessageMapping("/ttt/{roomId}")
    public void makeMove(@Payload Connect4Move   ticTacToeMove, @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor){
        roomService.makeMove(roomId, ticTacToeMove,headerAccessor);
        messagingTemplate.convertAndSend("/topic/ttt/" + roomId, roomService.getGameState(roomId));
    }

    @PostMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void addPlayer(@PathVariable String roomId, @PathVariable String playerSessionId,@RequestParam int playerNumber){
        System.out.println("added " + playerNumber );
        roomService.addPlayer(playerSessionId,roomId,playerNumber);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/players", roomService.getRoomUsers(roomId));
    }

    @DeleteMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void removePlayer(@PathVariable String roomId, @PathVariable String playerSessionId){
        System.out.println("removed");
        roomService.removePlayer(playerSessionId,roomId);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/players", roomService.getRoomUsers(roomId));
    }

    @GetMapping("rooms/{roomId}/players")
    @ResponseBody
    public List<RoomUser> getRoomSitPlayers(@PathVariable String roomId){
        return roomService.getRoomUsers(roomId);
    }

    @MessageMapping("/rooms")
    @SendTo("/topic/rooms")
    public Set<String> getGames(){
        return roomService.getRoomsIds();
    }

    @DeleteMapping("/rooms/{roomId}/game")
    @ResponseBody
    public void resetGame(@PathVariable String roomId){
        roomService.resetGame(roomId);
        messagingTemplate.convertAndSend("/topic/ttt/" + roomId, roomService.getGameState(roomId));
    }

    @GetMapping("/rooms")
    @ResponseBody
    public Set<String> getGames2() {
        return roomService.getRoomsIds();
    }

    @MessageExceptionHandler
    @SendToUser("/topic/ttt/error")
    public String handleErrors(Throwable error){
        System.out.println(error.getStackTrace());
        return error.getMessage();
    }
}
