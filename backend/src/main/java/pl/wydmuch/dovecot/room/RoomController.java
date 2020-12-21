package pl.wydmuch.dovecot.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameState;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeMove;


import java.util.List;
import java.util.Set;

@Controller
@CrossOrigin("*")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("/rooms")
    @ResponseBody
    public void createRoom(){
        roomService.createRoom();
    }

    @DeleteMapping("/rooms/{roomId}")
    @ResponseBody
    public void deleteRoom(@PathVariable String roomId){
        roomService.deleteRoom(roomId);
    }

//    @GetMapping("/rooms/{roomId}")
//    @ResponseBody
//    public TicTacToeGameState getGame(@PathVariable String roomId){
//
//    }
    @SubscribeMapping("/ttt/{roomId}")
    public TicTacToeGameState onSubscription(@DestinationVariable String roomId){
        System.out.println("Subbb");
        return roomService.onSubscription(roomId);
    }

    @MessageMapping("/ttt/{roomId}")
    public void makeMove(@Payload TicTacToeMove ticTacToeMove, @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor){
        roomService.makeMove(roomId, ticTacToeMove,headerAccessor);
    }

    @PostMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void addPlayer(@PathVariable String roomId, @PathVariable String playerSessionId,@RequestParam int playerNumber){
        System.out.println("added " + playerNumber );
        roomService.addPlayer(playerSessionId,roomId,"jagoda",playerNumber);
    }

    @DeleteMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void removePlayer(@PathVariable String roomId, @PathVariable String playerSessionId){
        System.out.println("removed");
        roomService.removePlayer(playerSessionId,roomId);
    }

    @GetMapping("rooms/{roomId}/players")
    @ResponseBody
    public List<TicTacToePlayer> getRoomSitPlayers(@PathVariable String roomId){
        return roomService.getSitPlayers(roomId);
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
    }

    @GetMapping("/rooms")
    @ResponseBody
    public Set<String> getGames2() {
        return roomService.getRoomsIds();
    }

    @MessageExceptionHandler
    @SendToUser("/topic/ttt/error")
    public String handleErrors(Throwable error){
        System.out.println("Error: " + error.getMessage());
        return error.getMessage();
    }
}
