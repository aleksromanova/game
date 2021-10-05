package application.service.game.rules;

import application.model.exception.GameException;
import application.model.game.GameState;
import application.model.game.PlayerRole;

public class CheckTurnRule implements Rule {
    @Override
    public void apply(GameState gameState) throws GameException {
        PlayerRole nextPlayerRole = gameState.getNextPlayer().getRole();
        int chosenPitIndex = gameState.getChosenPit();
        if(!nextPlayerRole.checkPitBelongsToPlayer(chosenPitIndex)){
            throw new GameException("Wrong turn! Please check pit that belongs to you and not the big pit!");
        }
    }
}
