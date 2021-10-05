package application.service.game.rules;

import application.model.exception.GameException;
import org.junit.Assert;
import org.junit.Test;

public class SowStonesRuleTest extends RuleTest {
    private SowStonesRule rule = new SowStonesRule();

    @Test
    public void correctTurnTest() throws Exception {
        gameState.setChosenPit(2);
        rule.apply(gameState);
        Assert.assertArrayEquals("Turn result doesn't math expected", new int[]{6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0}, gameState.getPits());
    }

    @Test
    public void skippingOpponentsBigPitTest() throws Exception {
        gameState.putStonesToPit(5, 10);
        gameState.setChosenPit(5);
        rule.apply(gameState);
        Assert.assertEquals("Opponents big pit must be skipped", 0, gameState.getPits()[13]);
    }

    @Test(expected = GameException.class)
    public void choosingEmptyPitTest() throws Exception {
        gameState.getStonesFromPit(2);
        gameState.setChosenPit(2);
        rule.apply(gameState);
    }
}
