package application.service.game.rules;

import application.model.game.GameState;
import application.model.game.Player;

import java.util.Comparator;

/**
 * Rule to determinate a winner
 * <p>
 * Rule description:
 * The winner of the game is the player who has the most stones in his big pit.
 */
public class FindWinnerRule implements Rule {
    @Override
    public void apply(GameState gameState) {
        if (gameState.isFinished()) {
            //checking draw case
            if (gameState.getPlayers().stream()
                    .map(player -> gameState.getStonesNumber(player.getRole().getBigPit()))
                    .distinct()
                    .count() == 1) {
                gameState.setWinner("");
                return;
            }
            String winner = gameState.getPlayers().stream()
                    .max(Comparator.comparingInt(player -> gameState.getStonesNumber(player.getRole().getBigPit())))
                    .map(Player::getPlayerName)
                    .orElse("");
            gameState.setWinner(winner);
        }
    }
}
