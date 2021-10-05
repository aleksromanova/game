package application.controller;

import application.model.dto.GameRequest;
import application.model.dto.GameStateResponse;
import application.model.dto.MakeTurnRequest;
import application.model.exception.GameException;
import application.model.exception.GameNotFoundException;
import application.model.game.GameState;
import application.service.game.GameService;
import application.service.state.StateUpdateService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    @Mock
    private GameService gameService;
    @Mock
    private StateUpdateService stateUpdateService;
    @InjectMocks
    private GameController controller;

    @Test
    public void startGameForPlayerTest() throws Exception {
        var playerName = "player1";
        var gameId = "test";
        var request = new GameRequest();
        request.setPlayerName(playerName);
        var gameState = new GameState(gameId);
        when(gameService.startGameForPlayer(playerName)).thenReturn(gameState);

        ResponseEntity response = controller.startGameForPlayer(request);
        GameStateResponse gameStateResponse = (GameStateResponse) response.getBody();

        Assert.assertEquals("Must return OK result", HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Response must have same gameId", gameState.getId(), gameStateResponse.getGameId());
        verify(stateUpdateService).notifyPlayers(gameStateResponse);
    }

    @Test
    public void startGameForPlayerGameExceptionTest() throws Exception {
        var playerName = "player1";
        var gameId = "test";
        var request = new GameRequest();
        request.setPlayerName(playerName);
        when(gameService.startGameForPlayer(playerName)).thenThrow(new GameException("Some game exception"));

        ResponseEntity response = controller.startGameForPlayer(request);

        Assert.assertEquals("Must return BAD_REQUEST result", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void makeTurnTest() throws Exception {
        var gameId = "test";
        var request = new MakeTurnRequest();
        request.setPitIndex(0);
        request.setGameId(gameId);
        var gameState = new GameState(gameId);
        when(gameService.makeTurn(gameId, 0)).thenReturn(gameState);

        ResponseEntity response = controller.makeTurn(request);
        GameStateResponse gameStateResponse = (GameStateResponse) response.getBody();

        Assert.assertEquals("Must return OK result", HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Response must have same gameId", gameState.getId(), gameStateResponse.getGameId());
        verify(stateUpdateService).notifyPlayers(gameStateResponse);
    }

    @Test
    public void makeTurnGameExceptionTest() throws Exception {
        var gameId = "test";
        var request = new MakeTurnRequest();
        request.setPitIndex(0);
        request.setGameId(gameId);
        when(gameService.makeTurn(gameId, 0)).thenThrow(new GameException("Exception message"));

        ResponseEntity response = controller.makeTurn(request);

        Assert.assertEquals("Must return BAD_REQUEST result", HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void makeTurnGameNotFoundTest() throws Exception {
        var gameId = "test";
        var request = new MakeTurnRequest();
        request.setPitIndex(0);
        request.setGameId(gameId);
        when(gameService.makeTurn(gameId, 0)).thenThrow(new GameNotFoundException("Exception message"));

        ResponseEntity response = controller.makeTurn(request);

        Assert.assertEquals("Must return NOT_FOUND result", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getGameStateTest() {
        var gameId = "test";
        var playerName = "player1";
        controller.getGameState(gameId, playerName);

        verify(stateUpdateService).updateWaitingResponse(eq(gameId), eq(playerName), any(DeferredResult.class));
    }
}
