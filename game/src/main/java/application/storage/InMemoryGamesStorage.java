package application.storage;

import application.model.game.GameState;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryGamesStorage implements GamesStorage {
    private Map<String, GameState> games = new ConcurrentHashMap<>();
    private GameState availableGame;

    /**
     * {@inheritDoc}
     */
    public GameState getAvailableGame() {
        return availableGame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGame(GameState gameState) {
        games.put(gameState.getId(), gameState);
        //to optimize process while using in memory storage we can save last saved non full game. in case of real DB usage it could be implemented different way
        if (!gameState.isFull()) {
            availableGame = gameState;
        } else {
            availableGame = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public GameState getGame(String gameId) {
        return games.get(gameId);
    }

    public Map<String, GameState> getGames() {
        return Collections.unmodifiableMap(games);
    }
}
