package pl.wydmuch.dovecot.games.tictactoe;

public class GameToDtoConverter {

  public static GameDto convertToDto(TicTacToe ticTacToe){
        GameDto gameDto = new GameDto();
        gameDto.setBoard(ticTacToe.getBoard());
        gameDto.setIsWinner(ticTacToe.isGameWon());
        gameDto.setIsDraw(ticTacToe.isDraw());
        return gameDto;
    }

}
