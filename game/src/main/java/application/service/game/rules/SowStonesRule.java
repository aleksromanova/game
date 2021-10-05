package application.service.game.rules;

import application.model.exception.GameException;
import application.model.game.GameState;
import application.model.game.PlayerRole;

/**
 * Rule to sow stones from chosen pit
 * <p>
 * Rule description:
 * The player who begins with the first move picks up all the stones in any of his own six pits, and sows the stones on to the right, one in each of the following pits, including his own big pit.
 * No stones are put in the opponents' big pit.
 */
public class SowStonesRule implements Rule {
    @Override
    public void apply(GameState gameState) throws GameException {
        PlayerRole nextPlayerRole = gameState.getNextPlayer().getRole();
        int stonesNumber = gameState.getStonesFromPit(gameState.getChosenPit());
        if (stonesNumber == 0) {
            throw new GameException("Chosen pit is empty! Please chose another one");
        }
        int nextIndex = gameState.getChosenPit();
        while (stonesNumber > 0) {
            nextIndex = gameState.getNextPitIndex(nextIndex);
            if (nextIndex != nextPlayerRole.getEnemiesBigPit()) {
                gameState.putStonesToPit(nextIndex, 1);
                stonesNumber--;
            }
        }
        gameState.setLastFilledPit(nextIndex);
    }
}
