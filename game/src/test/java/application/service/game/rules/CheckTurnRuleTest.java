package application.service.game.rules;

import application.model.exception.GameException;
import application.model.game.PlayerRole;
import org.junit.Assert;
import org.junit.Test;

public class CheckTurnRuleTest extends RuleTest {
    private CheckTurnRule rule = new CheckTurnRule();

    @Test(expected = GameException.class)
    public void wrongTurnTest() throws Exception {
        gameState.setChosenPit(PlayerRole.SECOND.getFirstPit());
        rule.apply(gameState);
    }

    @Test
    public void correctTurnTest() throws Exception {
        try {
            gameState.setChosenPit(PlayerRole.FIRST.getFirstPit());
            rule.apply(gameState);
        } catch (GameException e) {
            Assert.fail();
        }
    }
}
