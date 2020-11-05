package pl.wydmuch.dovecot.gameroom.service;

import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.gameroom.builder.AbstractGameBuilder;
import pl.wydmuch.dovecot.gameroom.model.AbstractGame;

@Service
public interface GameService<T extends AbstractGame> {

	/**
	 * return the new object extends abstractGame. better use
	 * {@link AbstractGameBuilder} build method to init the gameId and number of
	 * players.
	 * 
	 * @return object extends abstractGame
	 */
	T newGame();

	/**
	 * handle a player has won, and the game is ended.
	 * 
	 * @param gameId
	 *            gameId of the game
	 * @param playerIndex
	 *            index of the player who won the game
	 */
	void playerWin(String gameId, int playerIndex);

	/**
	 * handle a player leaved the game
	 * 
	 * @param t
	 *            object extends abstractGame
	 * @param i
	 *            index of the player who leaved the game
	 */
	void playerLeaved(T t, int i);

	/**
	 * handle the game is start
	 * 
	 * @param gameId
	 *            gameId of the game needed to start
	 */
	void start(String gameId);

}
