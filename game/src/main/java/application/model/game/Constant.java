package application.model.game;


public class Constant {
    public static final int PLAYERS_NUMBER = 2;
    public static final int PITS_NUMBER = 14;
    public static final int STONES_NUMBER = 6;
    public static final int MIDDLE_SCORE = (PITS_NUMBER - PLAYERS_NUMBER) / 2 * STONES_NUMBER;

    private Constant() {
    }
}
