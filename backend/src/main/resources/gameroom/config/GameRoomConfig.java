package pl.wydmuch.dovecot.gameroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wydmuch.dovecot.gameroom.model.AbstractGame;
import pl.wydmuch.dovecot.gameroom.utils.PlayerUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GameRoomConfig {

	@Bean(name = "playerGameMap")
	public Map<String, String> getPlayerGameMap() {
		return new ConcurrentHashMap<>();
	}

	@Bean(name = "playingGameMap")
	public Map<String, AbstractGame> getPlayingGameMap() {
		return new ConcurrentHashMap<>();
	}

	@Bean(name = "waitingGameMap")
	public Map<String, AbstractGame> getWaitingGameMap() {
		return new ConcurrentHashMap<>();
	}

	@Bean
	public PlayerUtils getPlayerUtils() {
		return new PlayerUtils();
	}

}
