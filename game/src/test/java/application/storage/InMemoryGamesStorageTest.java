package application.storage;

import application.model.game.GameState;
import org.junit.Assert;
import org.junit.Test;

public class InMemoryGamesStorageTest {
    private InMemoryGamesStorage storage = new InMemoryGamesStorage();

    @Test
    public void saveGameTest() {
        storage.saveGame(new GameState("test"));

        Assert.assertFalse("Must contain a game", storage.getGames().isEmpty());
    }

    @Test
    public void getExistingGameTest() throws Exception{
        var gameId = "test";
        var gameState = new GameState(gameId);
        storage.saveGame(gameState);
        var result = storage.getGame(gameId);

        Assert.assertEquals("Must find a game", result, gameState);
    }

    @Test
    public void getNotExistingGameTest() throws Exception{
        var gameState = storage.getGame("test");

        Assert.assertNull("Game state doesn't exist so null must be returned", gameState);
    }

    @Test
    public void getAvailableGameEmptyStorageTest() {
        var result = storage.getAvailableGame();

        Assert.assertNull("No available game in storage yet", result);
    }

    @Test
    public void getAvailableGameNonEmptyStorageTest() {
        var gameState = new GameState("test");
        storage.saveGame(gameState);
        var result = storage.getAvailableGame();

        Assert.assertEquals("Must return available game", gameState, result);
    }

    @Test
    public void getAvailableGameAllGamesAreFullTest() throws Exception{
        var gameState = new GameState("test");
        gameState.addPlayer("player1");
        gameState.addPlayer("player2");
        storage.saveGame(gameState);
        var result = storage.getAvailableGame();

        Assert.assertNull("No available game in storage now", result);
    }
}
