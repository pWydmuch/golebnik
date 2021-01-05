package pl.wydmuch.dovecot.webapp;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wydmuch.dovecot.room.RoomService;

import java.util.HashSet;
import java.util.Set;

@Component
public class AbandonRoomsRemoverScheduler {

    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    public AbandonRoomsRemoverScheduler(RoomService roomService, SimpMessagingTemplate messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleFixedDelayTask() {
        Set<String> gameNames = new HashSet<>();
        roomService.getRoomsWithoutUsersOlderThanMinutes(1).forEach(r->{
            roomService.deleteRoom(r.getId());
            gameNames.add(r.getRoomActivityId());
        });

        gameNames.forEach(n->
            messagingTemplate.convertAndSend("/topic/"+n+"/rooms", roomService.getRoomDtos(n))
        );


        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 5000);
    }
}
