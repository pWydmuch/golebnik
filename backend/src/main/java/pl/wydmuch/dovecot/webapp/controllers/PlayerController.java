package pl.wydmuch.dovecot.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wydmuch.dovecot.room.RoomService;
import pl.wydmuch.dovecot.room.RoomUser;

import java.util.List;

@Controller
@CrossOrigin("*")
public class PlayerController {


    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public PlayerController(RoomService roomService,SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }



    @PutMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void confirmRoomUser(@PathVariable String roomId, @PathVariable String playerSessionId){
        roomService.confirmRoomUser(playerSessionId,roomId);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/players/confirmations", roomService.getRoomUsers(roomId));
        if (roomService.isGameStarted(roomId)){
            messagingTemplate.convertAndSend("/topic/ttt/" + roomId, roomService.getRoomActivityState(roomId));
            messagingTemplate.convertAndSend("/topic/ttt/" + roomId + "/started", "Game has just started");
        }

    }

    @PostMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void addPlayer(@PathVariable String roomId, @PathVariable String playerSessionId, @RequestParam int playerNumber){
        System.out.println("added " + playerNumber );
        roomService.addRoomUser(playerSessionId,roomId,playerNumber);
        sendRoomUsers(roomId);
        sendRoomDtos(roomId);
    }

    @DeleteMapping("rooms/{roomId}/players/{playerSessionId}")
    @ResponseBody
    public void removePlayer(@PathVariable String roomId, @PathVariable String playerSessionId){
        System.out.println("removed");
        roomService.removeRoomUser(playerSessionId,roomId);
        sendRoomUsers(roomId);
        sendRoomDtos(roomId);
    }

    @GetMapping("rooms/{roomId}/players")
    @ResponseBody
    public List<RoomUser> getRoomSitPlayers(@PathVariable String roomId){
        return roomService.getRoomUsers(roomId);
    }

    @ExceptionHandler
    @ResponseBody
    public String handleErrors(Throwable error){
        System.out.println(error.getMessage());
        return error.getMessage();
    }

    private void sendRoomUsers(String roomId) {
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId + "/players", roomService.getRoomUsers(roomId));
    }

    private void sendRoomDtos(String roomId) {
        String gameName = roomService.getRoomActivityId(roomId);
        messagingTemplate.convertAndSend("/topic/"+gameName+"/rooms", roomService.getRoomDtos(gameName));
    }
}
