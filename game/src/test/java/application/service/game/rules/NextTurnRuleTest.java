package application.service.game.rules;

import application.model.game.PlayerRole;
import org.junit.Assert;
import org.junit.Test;

public class NextTurnRuleTest extends RuleTest {
    private NextTurnRule rule = new NextTurnRule();

    @Test
    public void turnEndedInOwnPitTest() {
        PlayerRole role = gameState.getNextPlayer().getRole();
        gameState.setLastFilledPit(3);
        rule.apply(gameState);
        Assert.assertNotEquals("Role have to change", role, gameState.getNextPlayer().getRole());
    }

    @Test
    public void turnEndedInOpponentsPitTest() {
        PlayerRole role = gameState.getNextPlayer().getRole();
        gameState.setLastFilledPit(10);
        rule.apply(gameState);
        Assert.assertNotEquals("Role have to change", role, gameState.getNextPlayer().getRole());
    }

    @Test
    public void turnEndedInOwnBigPitTest() {
        PlayerRole role = gameState.getNextPlayer().getRole();
        gameState.setLastFilledPit(6);
        rule.apply(gameState);
        Assert.assertEquals("Role have to stay the same", role, gameState.getNextPlayer().getRole());
    }
}
