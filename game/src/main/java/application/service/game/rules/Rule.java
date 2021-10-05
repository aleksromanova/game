package application.service.game.rules;

import application.model.exception.GameException;
import application.model.game.GameState;

/**
 * Rule of the game
 */
public interface Rule {
    /**
     * Applies rule to provided game state
     *
     * @param gameState initial game state
     */
    void apply(GameState gameState) throws GameException;
}
