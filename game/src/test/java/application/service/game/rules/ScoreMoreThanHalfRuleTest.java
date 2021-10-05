package application.service.game.rules;

import application.model.game.Constant;
import application.model.game.PlayerRole;
import org.junit.Assert;
import org.junit.Test;

public class ScoreMoreThanHalfRuleTest extends RuleTest {
    private ScoreMoreThanHalfRule rule = new ScoreMoreThanHalfRule();

    @Test
    public void playerHasMoreThanHalfStonesInBigPitTest() {
        gameState.getStonesFromPit(PlayerRole.FIRST.getBigPit());
        gameState.putStonesToPit(PlayerRole.FIRST.getBigPit(), Constant.MIDDLE_SCORE + 1);
        rule.apply(gameState);

        Assert.assertTrue("Game must be finished", gameState.isFinished());
    }

    @Test
    public void playerHasLessThanHalfStonesInBigPitTest() {
        gameState.getPits()[PlayerRole.FIRST.getBigPit()] = Constant.MIDDLE_SCORE - 1;
        rule.apply(gameState);

        Assert.assertFalse("Game must be finished", gameState.isFinished());
    }
}
