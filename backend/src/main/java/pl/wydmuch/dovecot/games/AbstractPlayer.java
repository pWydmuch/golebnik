package pl.wydmuch.dovecot.games;

public abstract class AbstractPlayer{
    private String playerName;
    private int playerNumber;

    public AbstractPlayer(String playerName, int playerNumber) {
        this.playerName = playerName;
        this.playerNumber = playerNumber;
    }

    public AbstractPlayer() {
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    protected abstract void makeMovePlayerSpecific(Move move);



}
