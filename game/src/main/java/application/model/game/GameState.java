package application.model.game;

import application.model.exception.GameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Current state of the game
 */
public class GameState {
    private String id;
    private List<Player> players = new ArrayList<>();
    private int[] pits = new int[Constant.PITS_NUMBER];
    private Player nextPlayer;
    private int chosenPit;
    private int lastFilledPit;
    private boolean finished;
    private String winner;

    public GameState(String id) {
        this.id = id;
        Set<Integer> bigPitsIndexes = Set.of(Constant.PITS_NUMBER / 2 - 1, Constant.PITS_NUMBER - 1);
        //filing the board for a new game
        for (int i = 0; i < Constant.PITS_NUMBER; i++) {
            if (!bigPitsIndexes.contains(i)) {
                pits[i] = Constant.STONES_NUMBER;
            }
        }
    }

    public void addPlayer(String playerName) throws GameException{
        if (nextPlayer == null) {
            Player player = new Player(playerName, PlayerRole.FIRST);
            nextPlayer = player;
            players.add(player);
        } else {
            if(players.stream().anyMatch(player -> player.getPlayerName().equals(playerName))){
                throw new GameException("Player with such name already exists in the game! Please chose another name.");
            }
            players.add(new Player(playerName, PlayerRole.SECOND));
        }
    }

    public boolean isFull() {
        return players.size() == Constant.PLAYERS_NUMBER;
    }

    public String getId() {
        return id;
    }

    public int[] getPits() {
        return Arrays.copyOf(pits, pits.length);
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public void updateNextPlayer() {
        nextPlayer = players.stream()
                .filter(player -> !player.equals(nextPlayer))
                .findFirst().orElse(nextPlayer);
    }

    public int getChosenPit() {
        return chosenPit;
    }

    public void setChosenPit(int chosenPit) {
        this.chosenPit = chosenPit;
    }

    public int getLastFilledPit() {
        return lastFilledPit;
    }

    public void setLastFilledPit(int lastFilledPit) {
        this.lastFilledPit = lastFilledPit;
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

    public int getStonesFromPit(int pitIndex) {
        int result = pits[pitIndex];
        pits[pitIndex] = 0;
        return result;
    }

    public void putStonesToPit(int pitIndex, int stoneNumber) {
        pits[pitIndex] += stoneNumber;
    }

    public int getNextPitIndex(int pitIndex) {
        if (pitIndex == Constant.PITS_NUMBER - 1) {
            return 0;
        } else return ++pitIndex;
    }

    public int getStonesNumber(int pitIndex) {
        return pits[pitIndex];
    }

    public int getOppositePitIndex(int pitIndex) {
        return Constant.PITS_NUMBER - pitIndex - Constant.PLAYERS_NUMBER; //skipping big pits
    }
}
