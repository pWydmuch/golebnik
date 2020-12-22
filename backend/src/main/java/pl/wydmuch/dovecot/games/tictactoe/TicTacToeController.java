package pl.wydmuch.dovecot.games.tictactoe;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("*")
public class TicTacToeController {

//    private TicTacToeGameEngine game;




    private final SimpMessagingTemplate simpMessagingTemplate;


    public TicTacToeController(SimpMessagingTemplate simpMessagingTemplate) {
//        game = new TicTacToeGameEngine();
        this.simpMessagingTemplate = simpMessagingTemplate;
    }



//    @MessageMapping("/ttt/{gameId}")
//    @SendTo("/topic/ttt/{gameId}")
//    public TicTacToeGameState makeMove(@Payload TicTacToeMove move,@DestinationVariable String gameId, SimpMessageHeaderAccessor  headerAccessor){
//        TicTacToeGameEngine game = gameService.getGameManager(gameId);
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
//        TicTacToeGameState gameDto = GameToGameStateConverter.convertToDto(game);
//        return gameDto;
//    }



//    @DeleteMapping("/game")
//    @ResponseBody
//    public TicTacToeGameState resetGame(){
//        game = new TicTacToeGameEngine();
//        return GameToGameStateConverter.convertToDto(game);
//    }

//
    @MessageExceptionHandler
    @SendTo("/topic/ttt/error")
    public String handleErrors(Throwable error){
        System.out.println("Error: " + error.getMessage());
        return error.getMessage();
    }
}
