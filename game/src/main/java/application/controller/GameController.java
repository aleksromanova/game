package application.controller;

import application.model.dto.GameRequest;
import application.model.dto.GameStateResponse;
import application.model.dto.MakeTurnRequest;
import application.model.exception.GameException;
import application.model.exception.GameNotFoundException;
import application.service.game.GameService;
import application.service.state.StateUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Game API
 */
@RestController
@RequestMapping("/api/game")
public class GameController {
    private GameService gameService;
    private StateUpdateService stateUpdateService;

    @Autowired
    public GameController(GameService gameService, StateUpdateService stateUpdateService) {
        this.gameService = gameService;
        this.stateUpdateService = stateUpdateService;
    }

    /**
     * POST method to start a new game for a player
     *
     * @param player who wants to start a game
     * @return game state of a new game
     */
    @PostMapping("/player")
    public ResponseEntity startGameForPlayer(@RequestBody GameRequest player) {
        GameStateResponse response;
        try {
            response = new GameStateResponse(gameService.startGameForPlayer(player.getPlayerName()));
            stateUpdateService.notifyPlayers(response);
            return ResponseEntity.ok().body(response);
        } catch (GameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * POST method to make turn in the game
     *
     * @param makeTurnRequest clients request for a turn
     * @return game state info
     */
    @PostMapping("/turn")
    public ResponseEntity makeTurn(@RequestBody MakeTurnRequest makeTurnRequest) {
        try {
            var gameState = gameService.makeTurn(makeTurnRequest.getGameId(), makeTurnRequest.getPitIndex());
            var response = new GameStateResponse(gameState);
            stateUpdateService.notifyPlayers(response);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (GameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Async request for game state update. Response would be sent as soon as game stand would change by another request.
     *
     * @param gameId     id of the game
     * @param playerName player who asks for update
     * @return {@link DeferredResult} for game state
     */
    @GetMapping("/state")
    public DeferredResult<GameStateResponse> getGameState(@RequestParam String gameId, @RequestParam String playerName) {
        DeferredResult<GameStateResponse> stateResult = new DeferredResult<>(60000L);
        stateUpdateService.updateWaitingResponse(gameId, playerName, stateResult);
        return stateResult;
    }
}
