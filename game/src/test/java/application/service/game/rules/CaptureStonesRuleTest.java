package application.service.game.rules;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CaptureStonesRuleTest extends RuleTest {
    private CaptureStonesRule rule = new CaptureStonesRule();

    @Test
    public void noReasonToCaptureStonesTest() {
        int[] originalState = Arrays.copyOf(gameState.getPits(), gameState.getPits().length);
        gameState.setLastFilledPit(2);
        rule.apply(gameState);

        Assert.assertArrayEquals("Pits state shouldn't change", originalState, gameState.getPits());
    }

    @Test
    public void lastPitOwnedByOpponentTest() {
        gameState.setLastFilledPit(10);
        gameState.getPits()[10] = 1;
        int[] originalState = Arrays.copyOf(gameState.getPits(), gameState.getPits().length);
        rule.apply(gameState);

        Assert.assertArrayEquals("Pits state shouldn't change", originalState, gameState.getPits());
    }

    @Test
    public void lastPitOwnedByPlayerTest() {
        gameState.setLastFilledPit(2);
        gameState.getStonesFromPit(2);
        gameState.putStonesToPit(2, 1);
        rule.apply(gameState);

        Assert.assertEquals("Big pit must contain 7 stones: 1 last stone and 6 captured stones", 7, gameState.getPits()[6]);
        Assert.assertEquals("Last pit of a turn is empty", 0, gameState.getStonesNumber(2));
        Assert.assertEquals("Opposite pit of a turn is empty", 0, gameState.getStonesNumber(10));
    }
}
