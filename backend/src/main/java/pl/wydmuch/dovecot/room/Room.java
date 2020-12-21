package pl.wydmuch.dovecot.room;


import pl.wydmuch.dovecot.games.Game;

import java.util.*;

public class Room {
  private String id;
  private TicTacToe game;
  private List<TicTacToePlayer> players;
  private int neededPlayersNumber = 2;
  private int currentPlayerIndex;
  private int startingPlayerIndex;

    public Room() {
        players = new ArrayList<>();
        startingPlayerIndex = 0;
        for(int i =0;i<neededPlayersNumber;i++){
            players.add(null);
        }
    }

    public TicTacToePlayer getPlayer(String playerId){
        return players.stream().filter(p->p.getSessionId().equals(playerId))
                .findFirst()
                .orElseThrow(()->new RuntimeException("There's no such a player"));
    }

    public String getId() {
        return id;
    }

    public int getStartingPlayerIndex() {
        return startingPlayerIndex;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean allPlayersArePresent(){
        return players.size() == neededPlayersNumber && players.stream().noneMatch(Objects::isNull);
    }

    public void addPlayer(TicTacToePlayer player, int playerNumber){
        if (playerCanJoinGame(player,playerNumber))
            players.set(playerNumber,player);
    }


    public void removePlayer(String playerSessionId){
        for (int i = 0; i < players.size(); i++) {
            TicTacToePlayer player = players.get(i);
            if (player !=null && player.getSessionId().equals(playerSessionId)){
                players.set(i,null);
            }
        }
    }

    public void removePlayer(RoomUser player){
        players.remove(player);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<TicTacToePlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<TicTacToePlayer> players) {
        this.players = players;
    }

    public void changeStaringPlayerIndex(){
        startingPlayerIndex = (startingPlayerIndex + 1)% neededPlayersNumber;
    }

    private boolean playerCanJoinGame(TicTacToePlayer player, int playerNumber) {
        return players.get(playerNumber) == null &&
                players.stream()
                        .filter(Objects::nonNull)
                        .map(RoomUser::getSessionId)
                        .noneMatch(id->player.getSessionId().equals(id));
    }


}
