package pl.wydmuch.golebnik.games.tictactoe;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
public class TicTacToeController {

    private TicTacToe game;

    public TicTacToeController() {
        game = new TicTacToe();
    }

    @MessageMapping("/ttt")
    @SendTo("/topic/ttt")
    public FieldContent[][] makeMove(@Payload Move move, SimpMessageHeaderAccessor  headerAccessor){
        System.out.println(headerAccessor.getSessionId());
        FieldContent  playerSign = (FieldContent) headerAccessor.getSessionAttributes().get("sign");
        if (Objects.isNull(playerSign)){
            List<FieldContent> availablePlayerSigns = game.getAvailableSigns();
            if(availablePlayerSigns.size()>0) {
                playerSign = availablePlayerSigns.get(0);
                availablePlayerSigns.remove(0);
            }
            System.out.println("Game is null");
            headerAccessor.getSessionAttributes().put("sign",playerSign);
        }
        move.setPlayerSign(playerSign);
        game.makeMove(move);
        System.out.println("Game sent " + game);
        return game.getBoard();
    }

    @DeleteMapping("/game")
    @ResponseBody
    public FieldContent[][] resetGame(){
        game = new TicTacToe();
        return game.getBoard();
    }
//
    @MessageExceptionHandler
    @SendTo("/topic/ttt/error")
    public String handleErrors(Throwable error){
        System.out.println("Error: " + error.getMessage());
        return error.getMessage();
    }
}
