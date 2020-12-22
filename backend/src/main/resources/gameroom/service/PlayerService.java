package pl.wydmuch.dovecot.gameroom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.gameroom.model.AbstractGame;
import pl.wydmuch.dovecot.gameroom.model.Player;
import pl.wydmuch.dovecot.gameroom.repository.GameRepository;
import pl.wydmuch.dovecot.gameroom.repository.PlayerRepository;
import pl.wydmuch.dovecot.gameroom.utils.PlayerUtils;

import java.util.Map;

@Service
public class PlayerService extends AbstractWebSocketService {

	private Logger logger = LoggerFactory.getLogger(PlayerService.class);

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private GameRepository<AbstractGame> gameRepository;

//	@Autowired
	private GameService<AbstractGame> gameService;

	@Autowired
	private PlayerUtils playerUtils;

	@Value(value = "${websocket.gameroom.config.numof.player}")
	private int numOfPlayer;

	public String addPlayer(String sessionId, String name) {
		logger.info("addRoomUser, sessionId: ", sessionId);
		Map<String, AbstractGame> waitingGameMap = gameRepository.getWaitingGameMap();
		AbstractGame abstractGame = null;
		abstractGame = waitingGameMap.values().stream().filter(g -> g.getJoinCount().getAndIncrement() <= numOfPlayer).findFirst().orElse(null);
		// create new game, if no available waiting game exists
		if (abstractGame == null) {
			abstractGame = gameService.newGame();
			waitingGameMap.put(abstractGame.getId(), abstractGame);
		}
		return addPlayer(new Player(name, sessionId), abstractGame);
	}

	public String addPlayer(String sessionId, String gameId, String name) {
		logger.info("addRoomUser, sessionId: ", sessionId, ", gameId: ", gameId, ", name: ", name);
		AbstractGame abstractGame = gameRepository.getWaitingGame(gameId);
		if (abstractGame != null && abstractGame.getJoinCount().incrementAndGet() <= numOfPlayer)
			synchronized (abstractGame) {
				return addPlayer(new Player(name, sessionId), abstractGame);
			}
		else
			sendTo("joined", sessionId, "error", true);
		return null;
	}

	private String addPlayer(Player player, AbstractGame abstractGame) {
		String gameId = null;
		int i = 0;
		logger.info("addRoomUser, player: ", player, ", game: ", abstractGame);
		if (abstractGame == null)
			return null;
		Player[] players = abstractGame.getPlayers();

		for (; i < players.length; i++) {
			if (players[i] == null) {
				players[i] = player;
				break;
			}
		}
		gameId = abstractGame.getId();
		playerRepository.addPlayer(player.getSessionId(), gameId);
		sendTo("joined", player.getSessionId(), new String[] { "error", "game-id", "index" }, new Object[] { false, gameId, i });
		return gameId;
	}

	public void removePlayer(String sessionId) {
		logger.info("removeRoomUser, sessionId: ", sessionId);
		Map<String, String> playerGameMap = playerRepository.getPlayerGameMap();
		if (!playerGameMap.containsKey(sessionId))
			return;
		String gameId = playerGameMap.get(sessionId);
		AbstractGame abstractGame = gameRepository.getWaitingGame(gameId);
		boolean isWaiting = true;
		if (abstractGame == null) {
			abstractGame = gameRepository.getPlayingGame(gameId);
			isWaiting = false;
		}
		synchronized (abstractGame) {
			removePlayer(sessionId, isWaiting, abstractGame);
		}
	}

	private void removePlayer(String sessionId, Boolean isWaiting, AbstractGame abstractGame) {
		logger.info("removeRoomUser, sessionId: ", sessionId, ", isWaiting: ", isWaiting, ", abstractGame: ", abstractGame);
		Player[] players;
		String gameId = abstractGame.getId();
		playerRepository.removePlayer(sessionId);
		players = abstractGame.getPlayers();
		int i;
		for (i = 0; i < players.length; i++) {
			if (players[i] != null && players[i].getSessionId().equals(sessionId)) {
				players[i] = null;
				break;
			}
		}
		abstractGame.removeReadyPlayer(sessionId);
		if (!isWaiting) {
			if (abstractGame.getReadyPlayersSize() == 1) {
				gameService.playerWin(gameId, playerUtils.getLastPlayerIndex(players));
				return;
			}
			gameService.playerLeaved(abstractGame, i);
		}
		send("player-list", gameId, "players", players);
	}

	public void ready(String sessionId, String gameId) {
		logger.info("ready, sessionId: ", sessionId, " , gameId: ", gameId);
		AbstractGame abstractGame = gameRepository.getWaitingGame(gameId);
		if (abstractGame == null)
			return;
		send("player-list", gameId, "players", abstractGame.getPlayers());
		synchronized (abstractGame) {
			abstractGame.addReadyPlayer(sessionId);
			if (abstractGame.getReadyPlayersSize() == numOfPlayer)
				gameService.start(gameId);
		}
	}

}
