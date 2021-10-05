package application.storage;

import application.model.game.GameState;

/**
 * Storage for game states
 */
public interface GamesStorage {
    /**
     * Returns game that has empty lots for player if any
     *
     * @return initial game state
     */
    GameState getAvailableGame();

    /**
     * Save game state to storage
     *
     * @param gameState
     */
    void saveGame(GameState gameState);

    /**
     * Get game from storage by the gameId
     *
     * @param gameId id of the game
     * @return State of the game
     */
    GameState getGame(String gameId);
}
