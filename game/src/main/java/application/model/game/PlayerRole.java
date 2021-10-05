package application.model.game;

import static application.model.game.Constant.PITS_NUMBER;
import static application.model.game.Constant.PLAYERS_NUMBER;

/**
 * Players roles and their parameters
 */
public enum PlayerRole {
    FIRST(0, PITS_NUMBER/PLAYERS_NUMBER-2, PITS_NUMBER/PLAYERS_NUMBER-1, PITS_NUMBER-1),
    SECOND(PITS_NUMBER/PLAYERS_NUMBER, PITS_NUMBER-2, PITS_NUMBER-1, PITS_NUMBER/PLAYERS_NUMBER-1);

    private int firstPit;
    private int lastPit;
    private int bigPit;
    private int enemiesBigPit;

    PlayerRole(int firstPit, int lastPit, int bigPit, int enemiesBigPit) {
        this.firstPit = firstPit;
        this.lastPit = lastPit;
        this.bigPit = bigPit;
        this.enemiesBigPit = enemiesBigPit;
    }

    public boolean checkPitBelongsToPlayer(int pitIndex) {
        return pitIndex >= getFirstPit() && pitIndex <= getLastPit();
    }

    public int getFirstPit() {
        return firstPit;
    }

    public int getLastPit() {
        return lastPit;
    }

    public int getBigPit() {
        return bigPit;
    }

    public int getEnemiesBigPit() {
        return enemiesBigPit;
    }
}
