package application.service.game;

import application.model.dto.GameStateResponse;
import application.model.game.GameState;
import application.service.state.StateUpdateService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.context.request.async.DeferredResult;

public class StateUpdateServiceTest {
    private StateUpdateService stateUpdateService = new StateUpdateService();
    private String gameId = "test";
    private String playerName = "player1";

    @Test
    public void updateStateForExistingGameTest() {
        stateUpdateService.updateWaitingResponse(gameId, playerName, new DeferredResult<>());
        Assert.assertEquals("Must be one entry for this game", 1, stateUpdateService.getStateMap().get("test").size());
    }

    @Test
    public void updateStateForNotExistingGameTest() {
        stateUpdateService.updateWaitingResponse(gameId, playerName, new DeferredResult<>());
        Assert.assertEquals("Must be one entry for this game", 1, stateUpdateService.getStateMap().get("test").size());
    }

    @Test
    public void notifyPlayersTest() {
        stateUpdateService.updateWaitingResponse(gameId, playerName, new DeferredResult<>());

        stateUpdateService.notifyPlayers(new GameStateResponse(new GameState(gameId)));
        Assert.assertTrue(stateUpdateService.getStateMap().get(gameId).get(playerName).hasResult());
    }
}
