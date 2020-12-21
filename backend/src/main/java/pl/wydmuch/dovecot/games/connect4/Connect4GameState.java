package pl.wydmuch.dovecot.games.connect4;

import pl.wydmuch.dovecot.games.GameState;

public class Connect4GameState implements GameState {
    private String[][] grid;
    private int checkIfWinner;
    private String winner;


    public Connect4GameState() {
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    public int getCheckIfWinner() {
        return checkIfWinner;
    }

    public void setCheckIfWinner(int checkIfWinner) {
        this.checkIfWinner = checkIfWinner;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }


}
