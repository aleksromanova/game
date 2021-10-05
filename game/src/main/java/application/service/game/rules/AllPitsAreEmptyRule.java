package application.service.game.rules;

import application.model.game.GameState;
import application.model.game.Player;
import application.model.game.PlayerRole;

import java.util.Optional;

/**
 * Rule to process the situation when player doesn't have any stones on his side
 * <p>
 * Rule description:
 * The game is over as soon as one of the sides runs out of stones. The player who still has stones in his pits keeps them and puts them in his big pit.
 */
public class AllPitsAreEmptyRule implements Rule {

    @Override
    public void apply(GameState gameState) {
        Optional<Player> emptyPlayer = findEmptyPlayer(gameState);
        if (emptyPlayer.isPresent()) {
            gameState.getPlayers().stream().filter(player -> !player.equals(emptyPlayer.get())).forEach(player -> {
                PlayerRole role = player.getRole();
                for (int i = role.getFirstPit(); i <= role.getLastPit(); i++) {
                    int stones = gameState.getStonesFromPit(i);
                    gameState.putStonesToPit(role.getBigPit(), stones);
                }
            });
            gameState.setFinished(true);
        }
    }

    private Optional<Player> findEmptyPlayer(GameState gameState) {
        for (Player player : gameState.getPlayers()) {
            PlayerRole role = player.getRole();
            boolean allPitsEmpty = true;
            for (int i = role.getFirstPit(); i <= role.getLastPit(); i++) {
                if (gameState.getStonesNumber(i) != 0) {
                    allPitsEmpty = false;
                    break;
                }
            }
            if (allPitsEmpty) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }
}
