package application.service.game.rules;

import application.model.game.GameState;
import org.junit.Before;

public abstract class RuleTest {
    protected GameState gameState;

    @Before
    public void before() throws Exception{
        gameState = new GameState("test");
        gameState.addPlayer("player1");
        gameState.addPlayer("player2");
    }
}
