package application.model.dto;

import application.model.game.GameState;
import application.model.game.Player;

/**
 * Response representation for {@link GameState}
 */
public class GameStateResponse {
    private String gameId;
    private int[] pits;
    private String[] players;
    private String nextPlayer;
    private boolean finished;
    private String winner;

    public GameStateResponse(GameState gameState) {
        this.pits = gameState.getPits();
        this.players = gameState.getPlayers().stream().map(Player::getPlayerName).toArray(String[]::new);
        this.gameId = gameState.getId();
        this.nextPlayer = gameState.getNextPlayer() != null ? gameState.getNextPlayer().getPlayerName() : null;
        this.finished = gameState.isFinished();
        this.winner = gameState.getWinner();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int[] getPits() {
        return pits;
    }

    public void setPits(int[] pits) {
        this.pits = pits;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
