package application.service.state;

import application.model.dto.GameStateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Service to manage game state update
 */
@Service
public class StateUpdateService {
    private Map<String, Map<String, DeferredResult<GameStateResponse>>> stateMap = new HashMap<>();

    /**
     * Save result for async request to storage
     *
     * @param gameId id of the game
     * @param playerName player who requested update
     * @param result deferred result for a player
     */
    public void updateWaitingResponse(String gameId, String playerName, DeferredResult<GameStateResponse> result) {
        stateMap.merge(gameId, new HashMap<>(), (v1, v2) -> v1).put(playerName, result);
    }

    /**
     * Notify all players about changed game state
     *
     * @param response game state response
     */
    public void notifyPlayers(GameStateResponse response) {
        stateMap.merge(response.getGameId(), new HashMap<>(), (v1, v2) -> v1).forEach((key, value) -> value.setResult(response));
    }

    public Map<String, Map<String, DeferredResult<GameStateResponse>>> getStateMap() {
        return Collections.unmodifiableMap(stateMap);
    }
}
