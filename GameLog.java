/**
 * Class - GameLog
 *
 * Logs the results of the Battleship game
 * @author Darren Wu
 * @version October 31, 2021
 */
public class GameLog {
    private int winningPlayer;
    private int losingPlayerHits;
    private int numTurns;
    private String boardPatternOne;
    private String boardPatternTwo;

    public GameLog(int winningPlayer, int losingPlayerHits, int numTurns, String boardPatternOne,
                   String boardPatternTwo) {
        this.winningPlayer = winningPlayer;
        this.losingPlayerHits = losingPlayerHits;
        this.numTurns = numTurns;
        this.boardPatternOne = boardPatternOne;
        this.boardPatternTwo = boardPatternTwo;
    }

    public String toString() {
        String str;

        str = String.format("Battleship Game Log:\nWinning Player: Player %d\n", winningPlayer);
        if (winningPlayer == 1) {
            str = str + String.format("Hits: 17 - %d\n", losingPlayerHits);
        } else if (winningPlayer == 2) {
            str = str + String.format("Hits: %d - 17\n", losingPlayerHits);
        }
        str = str + String.format("Number of Turns To Win: %d\n", numTurns);
        str = str + String.format("Player 1 Board Pattern: %s\n", boardPatternOne);
        str = str + String.format("Player 2 Board Pattern: %s\n", boardPatternTwo);

        return str;
    }

}