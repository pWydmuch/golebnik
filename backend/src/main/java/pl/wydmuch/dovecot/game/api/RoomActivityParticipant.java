package pl.wydmuch.dovecot.game.api;

public abstract class RoomActivityParticipant {
    private String playerName;
    private int playerNumber;

    public RoomActivityParticipant(String playerName, int playerNumber) {
        this.playerName = playerName;
        this.playerNumber = playerNumber;
    }

    public RoomActivityParticipant() {
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
}
