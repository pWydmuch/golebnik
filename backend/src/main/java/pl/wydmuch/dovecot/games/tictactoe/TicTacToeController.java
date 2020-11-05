package pl.wydmuch.dovecot.games.tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.wydmuch.dovecot.games.tictactoe.Field.FieldContent;
import pl.wydmuch.dovecot.room.RoomService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@CrossOrigin("*")
public class TicTacToeController {

//    private TicTacToe game;




    private final SimpMessagingTemplate simpMessagingTemplate;


    public TicTacToeController(SimpMessagingTemplate simpMessagingTemplate) {
//        game = new TicTacToe();
        this.simpMessagingTemplate = simpMessagingTemplate;
    }



//    @MessageMapping("/ttt/{gameId}")
//    @SendTo("/topic/ttt/{gameId}")
//    public GameDto makeMove(@Payload Move move,@DestinationVariable String gameId, SimpMessageHeaderAccessor  headerAccessor){
//        TicTacToe game = gameService.getGame(gameId);
//        System.out.println("Last: "+ game.getLastAddedSign());
//        System.out.println(headerAccessor.getSessionId());
//        FieldContent  playerSign = (FieldContent) headerAccessor.getSessionAttributes().get("sign");
//        if (Objects.isNull(playerSign)){
//            List<FieldContent> availablePlayerSigns = game.getAvailableSigns();
//            if(availablePlayerSigns.size()>0) {
//                playerSign = availablePlayerSigns.get(0);
//                availablePlayerSigns.remove(0);
//            }
//            System.out.println("Room is null");
//
//            headerAccessor
//                    .getSessionAttributes()
//                    .put("sign",playerSign);
//        }
//        move.setPlayerSign(playerSign);
//        game.makeMove(move);
//        System.out.println("Room sent " + game);
//        GameDto gameDto = GameToDtoConverter.convertToDto(game);
//        return gameDto;
//    }



//    @DeleteMapping("/game")
//    @ResponseBody
//    public GameDto resetGame(){
//        game = new TicTacToe();
//        return GameToDtoConverter.convertToDto(game);
//    }

//
    @MessageExceptionHandler
    @SendTo("/topic/ttt/error")
    public String handleErrors(Throwable error){
        System.out.println("Error: " + error.getMessage());
        return error.getMessage();
    }
}
