package application.service.game.rules;

import org.junit.Assert;
import org.junit.Test;

public class AllPitsAreEmptyRuleTest extends RuleTest {
    private AllPitsAreEmptyRule rule = new AllPitsAreEmptyRule();

    @Test
    public void fistPlayerPitsAreEmptyTest() {
        emptyPits(0, 6);
        rule.apply(gameState);

        Assert.assertTrue("Game must be finished", gameState.isFinished());
        Assert.assertEquals("Player 2 big pit should collect all his stones", 36, gameState.getPits()[13]);
    }

    @Test
    public void allPitsAreEmptyTest() {
        emptyPits(0, 14);
        rule.apply(gameState);

        Assert.assertTrue("Game must be finished", gameState.isFinished());
    }

    @Test
    public void notAllPitsAreEmptyTest() {
        rule.apply(gameState);

        Assert.assertFalse("Game mustn't be finished yet", gameState.isFinished());
    }

    private void emptyPits(int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            gameState.getStonesFromPit(i);
        }
    }
}
