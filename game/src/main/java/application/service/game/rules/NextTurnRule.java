package application.service.game.rules;

import application.model.game.GameState;

/**
 * Rule to define next player for turn
 * <p>
 * Rule description:
 * If the player's last stone lands in his own big pit, he gets another turn. This can be repeated several times before it's the other player's turn.
 */
public class NextTurnRule implements Rule {
    @Override
    public void apply(GameState gameState) {
        if (gameState.getLastFilledPit() != gameState.getNextPlayer().getRole().getBigPit()) {
            gameState.updateNextPlayer();
        }
    }
}
