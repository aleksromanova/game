package application.service.game.rules;

import application.model.game.Constant;
import application.model.game.GameState;
import application.model.game.PlayerRole;

public class ScoreMoreThanHalfRule implements Rule {
    @Override
    public void apply(GameState gameState) {
        for (PlayerRole role : PlayerRole.values()) {
            if (gameState.getPits()[role.getBigPit()] > Constant.MIDDLE_SCORE) {
                gameState.setFinished(true);
            }
        }
    }
}
