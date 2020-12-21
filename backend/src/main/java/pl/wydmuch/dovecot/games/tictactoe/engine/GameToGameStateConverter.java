package pl.wydmuch.dovecot.games.tictactoe.engine;

class GameToGameStateConverter {

  static TicTacToeGameState convertToDto(TicTacToeGameEngine ticTacToeGame){
        TicTacToeGameState ticTacToeGameState = new TicTacToeGameState();
        ticTacToeGameState.setBoard(ticTacToeGame.getBoard());
        ticTacToeGameState.setIsWinner(ticTacToeGame.isGameWon());
        ticTacToeGameState.setIsDraw(ticTacToeGame.isDraw());
        return ticTacToeGameState;
    }

}
