package pl.wydmuch.dovecot.websocket.gameroom.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.RoomActivityState;

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
        messagingTemplate.convertAndSend("/topic/rooms", roomService.getRoomDtos());
    }

    @DeleteMapping("/rooms/{roomId}")
    @ResponseBody
    public void deleteRoom(@PathVariable String roomId){
        roomService.deleteRoom(roomId);
        messagingTemplate.convertAndSend("/topic/rooms", roomService.getRoomDtos());
    }

    @SubscribeMapping("/ttt/{roomId}")
    public RoomActivityState onSubscription(@DestinationVariable String roomId){
        System.out.println("Subbb");
        return roomService.onSubscription(roomId);
    }

    @MessageMapping("/ttt/{roomId}")
    public void makeMove(@Payload String gameMove, @DestinationVariable String roomId, SimpMessageHeaderAccessor headerAccessor){
        roomService.doAction(roomId, gameMove,headerAccessor);
        messagingTemplate.convertAndSend("/topic/ttt/" + roomId, roomService.getRoomActivityState(roomId));
    }

    @PostMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void addPlayer(@PathVariable String roomId, @PathVariable String playerSessionId,@RequestParam int playerNumber){
        System.out.println("added " + playerNumber );
        roomService.addRoomUser(playerSessionId,roomId,playerNumber);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/players", roomService.getRoomUsers(roomId));
    }

    @DeleteMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void removePlayer(@PathVariable String roomId, @PathVariable String playerSessionId){
        System.out.println("removed");
        roomService.removeRoomUser(playerSessionId,roomId);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/players", roomService.getRoomUsers(roomId));
    }

    @GetMapping("rooms/{roomId}/players")
    @ResponseBody
    public List<RoomUser> getRoomSitPlayers(@PathVariable String roomId){
        return roomService.getRoomUsers(roomId);
    }

    @MessageMapping("/rooms")
    @SendTo("/topic/rooms")
    public List<RoomDto> getRooms(){
        return roomService.getRoomDtos();
    }

    @DeleteMapping("/rooms/{roomId}/game")
    @ResponseBody
    public void resetGame(@PathVariable String roomId){
        roomService.resetRoomActivity(roomId);
        messagingTemplate.convertAndSend("/topic/ttt/" + roomId, roomService.getRoomActivityState(roomId));
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<RoomDto> getGames2() {
        return roomService.getRoomDtos();
    }

    @MessageExceptionHandler
    @SendToUser("/topic/ttt/error")
    public String handleErrors(Throwable error){
        System.out.println(error.getStackTrace());
        return error.getMessage();
    }
}
