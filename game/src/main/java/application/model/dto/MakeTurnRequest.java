package application.model.dto;

/**
 * Request for making a turn
 */
public class MakeTurnRequest {
    private String gameId;
    private int pitIndex;

    public String getGameId() {
        return gameId;
    }

    public int getPitIndex() {
        return pitIndex;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setPitIndex(int pitIndex) {
        this.pitIndex = pitIndex;
    }
}
