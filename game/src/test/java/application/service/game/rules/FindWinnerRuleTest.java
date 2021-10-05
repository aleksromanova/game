package application.service.game.rules;

import application.model.game.Constant;
import org.junit.Assert;
import org.junit.Test;

import static application.model.game.PlayerRole.FIRST;
import static application.model.game.PlayerRole.SECOND;
import static application.model.game.Constant.MIDDLE_SCORE;

public class FindWinnerRuleTest extends RuleTest {
    private FindWinnerRule rule = new FindWinnerRule();

    @Test
    public void drawTest() {
        gameState.getStonesFromPit(FIRST.getBigPit());
        gameState.putStonesToPit(FIRST.getBigPit(), MIDDLE_SCORE);
        gameState.getStonesFromPit(SECOND.getBigPit());
        gameState.putStonesToPit(SECOND.getBigPit(), MIDDLE_SCORE);
        gameState.setFinished(true);
        rule.apply(gameState);

        Assert.assertEquals("In case of a draw player name must be empty", "", gameState.getWinner());
    }

    @Test
    public void winnerExistsTest() {
        gameState.getStonesFromPit(FIRST.getBigPit());
        gameState.putStonesToPit(FIRST.getBigPit(), MIDDLE_SCORE + 1);
        gameState.getStonesFromPit(SECOND.getBigPit());
        gameState.putStonesToPit(SECOND.getBigPit(), MIDDLE_SCORE - 1);
        gameState.setFinished(true);
        rule.apply(gameState);

        Assert.assertEquals("Player1 must be the winner", "player1", gameState.getWinner());
    }
}
