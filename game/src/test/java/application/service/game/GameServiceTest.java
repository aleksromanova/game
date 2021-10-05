package application.service.game;

import application.model.exception.GameException;
import application.model.exception.GameNotFoundException;
import application.model.game.GameState;
import application.service.game.rules.Rule;
import application.storage.GamesStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
    @Mock
    private GamesStorage gamesStorage;

    @Spy
    private List<Rule> rules = new ArrayList<>();

    @InjectMocks
    private GameService gameService;

    @Before
    public void before() {
        //add empty rule
        rules.add(gameState -> {
        });
    }

    @Test
    public void firstPlayerStartsGameTest() throws Exception {
        String playerName = "player1";
        when(gamesStorage.getAvailableGame()).thenReturn(null);
        GameState response = gameService.startGameForPlayer(playerName);

        Assert.assertNotNull("Must return new game", response);
    }

    @Test
    public void secondPlayerStartsGameTest() throws Exception {
        String playerName1 = "player1";
        String playerName2 = "player2";

        GameState gameState = new GameState("test1");
        gameState.addPlayer(playerName1);

        when(gamesStorage.getAvailableGame()).thenReturn(gameState);

        GameState response = gameService.startGameForPlayer(playerName2);

        Assert.assertEquals("Must return available game state", gameState, response);
    }

    @Test
    public void makeTurnTest() throws Exception {
        String playerName1 = "player1";
        String playerName2 = "player2";

        String gameId = "test";
        int pitIndex = 1;

        GameState gameState = new GameState(gameId);
        gameState.addPlayer(playerName1);
        gameState.addPlayer(playerName2);

        when(gamesStorage.getGame(gameId)).thenReturn(gameState);
        GameState response = gameService.makeTurn(gameId, pitIndex);

        Assert.assertEquals("Game state must stay the same without rules", gameState, response);
    }

    @Test(expected = GameException.class)
    public void makeTurnWhenNotEnoughPlayersTest() throws Exception {
        String gameId = "test";
        GameState gameState = new GameState(gameId);
        when(gamesStorage.getGame(gameId)).thenReturn(gameState);

        gameService.makeTurn(gameId, 1);
    }

    @Test(expected = GameNotFoundException.class)
    public void makeTurnWhenNotGameNotExistsTest() throws Exception {
        String gameId = "test";
        when(gamesStorage.getGame(gameId)).thenReturn(null);

        gameService.makeTurn(gameId, 1);
    }
}
