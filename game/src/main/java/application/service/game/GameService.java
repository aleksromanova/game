package application.service.game;

import application.model.exception.GameException;
import application.model.exception.GameNotFoundException;
import application.model.game.GameState;
import application.service.game.rules.Rule;
import application.storage.GamesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service for ruling the game
 */
@Service
public class GameService {
    private GamesStorage storage;
    private List<Rule> rules;

    @Autowired
    public GameService(GamesStorage storage, List<Rule> rules) {
        this.storage = storage;
        this.rules = rules;
    }

    /**
     * Make turn in the game
     *
     * @param gameId id of the game where turn must be done
     * @param pitIndex index of chosen pit
     * @return result state of the game
     * @throws GameException in case of game logic problems
     * @throws GameNotFoundException in case when game is not found in the storage
     */
    public GameState makeTurn(String gameId, int pitIndex) throws GameException, GameNotFoundException {
        var gameState = storage.getGame(gameId);
        if (gameState == null) {
            throw new GameNotFoundException("Game with id: " + gameId + " is not found!");
        }
        if (!gameState.isFull()) {
            throw new GameException("Not enough players in this game yet!");
        }
        gameState.setChosenPit(pitIndex);
        for (Rule rule : rules) {
            rule.apply(gameState);
        }
        return gameState;
    }

    /**
     * Add player to existing game or create new one
     *
     * @param playerName name of the player who wants to start
     * @return state of the game where player is added
     * @throws GameException in case of game logic problems
     */
    public synchronized GameState startGameForPlayer(String playerName) throws GameException {
        var gameState = storage.getAvailableGame();
        if (gameState == null) {
            gameState = new GameState(UUID.randomUUID().toString());
        }
        gameState.addPlayer(playerName);
        storage.saveGame(gameState);
        return gameState;
    }
}
