package pl.wydmuch.dovecot.games.tictactoe;

public class GameToDtoConverter {

  public static GameDto convertToDto(TicTacToe ticTacToe){
        GameDto gameDto = new GameDto();
        gameDto.setBoard(ticTacToe.getBoard());
        gameDto.setWinner(ticTacToe.isGameWon());
        gameDto.setDraw(ticTacToe.isDraw());
        return gameDto;
    }

}
