package pl.wydmuch.dovecot.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wydmuch.dovecot.room.RoomService;
import pl.wydmuch.dovecot.room.RoomDto;
import pl.wydmuch.dovecot.room.RoomUser;


import java.security.Principal;
import java.util.List;


@Controller
@CrossOrigin("*")
public class RoomController {

    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    //TODO te wszystkie convertAndSend powinny raczej isc do serwisu aplikacyjnego
    @Autowired
    public RoomController(RoomService roomService,SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/rooms/{gameName}")
    @ResponseBody
    public void createRoom(@PathVariable String gameName, Principal principal){
        System.out.println("Principal: " + principal.getName());
        roomService.createRoom(gameName);
        messagingTemplate.convertAndSend("/topic/"+gameName+"/rooms", roomService.getRoomDtos(gameName));
    }

    @DeleteMapping("/rooms/{roomId}")
    @ResponseBody
    public void deleteRoom(@PathVariable String roomId){
        String gameName = roomService.getRoomActivityId(roomId);
        roomService.deleteRoom(roomId);
        messagingTemplate.convertAndSend("/topic/"+gameName+"/rooms", roomService.getRoomDtos(gameName));
    }

    @SubscribeMapping("/ttt/{roomId}")
    public String onSubscription(@DestinationVariable String roomId){
        System.out.println("Subbb");
        return roomService.onSubscription(roomId);
    }

    @MessageMapping("/ttt/{roomId}/{playerName}")
    public void makeMove(@Payload String gameMove, @DestinationVariable String roomId , @DestinationVariable String playerName ){
        roomService.doAction(roomId, gameMove,playerName);
        messagingTemplate.convertAndSend("/topic/ttt/" + roomId, roomService.getRoomActivityState(roomId));
    }


//    @MessageMapping("/rooms")
//    @SendTo("/topic/rooms")
//    public List<RoomDto> getRooms(){
//        return roomService.getRoomDtos();
//    }

    @DeleteMapping("/rooms/{roomId}/game")
    @ResponseBody
    public void resetGame(@PathVariable String roomId){
        roomService.resetRoomActivity(roomId);
        messagingTemplate.convertAndSend("/topic/ttt/" + roomId, roomService.getRoomActivityState(roomId));
    }

    @GetMapping("/{gameName}/rooms")
    @ResponseBody
    public List<RoomDto> getRooms2(@PathVariable String gameName) {
        return roomService.getRoomDtos(gameName);
    }

    @MessageExceptionHandler
    @SendToUser("/topic/ttt/error")
    public String handleErrors(Throwable error){
        System.out.println(error.getMessage());
        return error.getMessage();
    }
}
