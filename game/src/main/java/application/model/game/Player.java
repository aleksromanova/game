package application.model.game;

public class Player {
    private String playerName;
    private PlayerRole role;

    public Player(String playerName, PlayerRole role) {
        this.playerName = playerName;
        this.role = role;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerRole getRole() {
        return role;
    }
}
