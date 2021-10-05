package application.service.game.rules;

import application.model.game.GameState;
import application.model.game.PlayerRole;

/**
 * Rule for capturing the stones
 * <p>
 * Rule description:
 * During the game the pits are emptied on both sides.
 * Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player’s pit) and puts them in his own big pit.
 */
public class CaptureStonesRule implements Rule {
    @Override
    public void apply(GameState gameState) {
        PlayerRole playerRole = gameState.getNextPlayer().getRole();
        if (gameState.getStonesNumber(gameState.getLastFilledPit()) == 1
                && playerRole.checkPitBelongsToPlayer(gameState.getLastFilledPit())) {
            int lastStone = gameState.getStonesFromPit(gameState.getLastFilledPit());
            int stonesFromOppositePit = gameState.getStonesFromPit(gameState.getOppositePitIndex(gameState.getLastFilledPit()));
            gameState.putStonesToPit(playerRole.getBigPit(), lastStone + stonesFromOppositePit);
        }
    }
}
