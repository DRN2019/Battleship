import java.io.*;
import java.util.Scanner;

/**
 * Class - PlayGame
 *
 * Contains the main method and all the functions to play the game of Battleship
 * @author Darren Wu
 * @version October 31, 2021
 */
public class PlayGame {
    private char[][] playerOneBoard;
    private char[][] playerTwoBoard;
    private String playerOneFileName;
    private String playerTwoFileName;
    private String playerOneBoardPattern;
    private String playerTwoBoardPattern;
    private int turn;
    private int p1Hits;
    private int p2Hits;
    private int winner;

    public static void main(String[] args) {
        PlayGame pg = new PlayGame("ShipPositionsPlayerOne.txt", "ShipPositionsPlayerTwo.txt");
        int winner;
        int loserHits;

        pg.initBoards();

        try {
            File f1 = new File(pg.getPlayerOneFileName());
            FileReader fr1 = new FileReader(f1);
            BufferedReader br1 = new BufferedReader(fr1);
            File f2 = new File(pg.getPlayerTwoFileName());
            FileReader fr2 = new FileReader(f2);
            BufferedReader br2 = new BufferedReader(fr2);

            Scanner sc = new Scanner(System.in);

            char p1Shot;
            char p2Shot;

            pg.initBoards();
            pg.getBoards(br1, br2);
            pg.setPlayerOneBoardPattern(pg.calcBoardPattern(pg.getPlayerOneBoard()));
            pg.setPlayerTwoBoardPattern(pg.calcBoardPattern(pg.getPlayerTwoBoard()));

            do {
                pg.incTurn();
                p1Shot = pg.shoot(1, sc);
                System.out.println("Value: " + p1Shot);
                if (pg.getP1Hits() < 17) {
                    p2Shot = pg.shoot(2, sc);
                    System.out.println("Value: " + p2Shot);
                }
            } while (pg.getP1Hits() < 17 && pg.getP2Hits() < 17);

            winner = -1;
            loserHits = -1;
            if (pg.getP1Hits() == 17) {
                winner = 1;
                pg.setWinner(1);
                loserHits = pg.getP2Hits();
            } else {
                winner = 2;
                pg.setWinner(2);
                loserHits = pg.getP1Hits();
            }

            pg.exit(winner);
            GameLog log = new GameLog(winner, loserHits, pg.getTurn(), pg.getPlayerOneBoardPattern(),
                    pg.getPlayerTwoBoardPattern());

            // Clear file
            new FileWriter("GameLog.txt", false).close();

            File f3 = new File("GameLog.txt");
            FileWriter fw1 = new FileWriter(f3, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);

            bw1.write(log.toString());
            bw1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public PlayGame(String playerOneFileName, String playerTwoFileName) {
        this.playerOneFileName = playerOneFileName;
        this.playerTwoFileName = playerTwoFileName;
    }

    public void initBoards() {
        playerOneBoard = new char[10][14];
        playerTwoBoard = new char[10][14];
    }

    public void getBoards(BufferedReader br1, BufferedReader br2) throws Exception {
        String currentLine;

        try {
            for (int i = 0; i < 10; i++) {
                currentLine = br1.readLine();
                playerOneBoard[i] = currentLine.toCharArray();
                currentLine = br2.readLine();
                playerTwoBoard[i] = currentLine.toCharArray();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String calcBoardPattern(char[][] board) {
        int count;
        String pattern;

        count = 0;
        pattern = "Scattered";
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 14; c++) {
                if (board[r][c] == 'H') {
                    count++;
                }
            }
        }
        if (count >= 9) {
            pattern = "Top Heavy";
        }

        count = 0;
        for (int r = 3; r < 7; r++) {
            for (int c = 0; c < 14; c++) {
                if (board[r][c] == 'H') {
                    count++;
                }
            }
        }
        if (count >= 9) {
            pattern = "Middle Heavy";
        }

        count = 0;
        for (int r = 7; r < 10; r++) {
            for (int c = 0; c < 14; c++) {
                if (board[r][c] == 'H') {
                    count++;
                }
            }
        }
        if (count >= 9) {
            pattern = "Bottom Heavy";
        }

        return pattern;
    }

    /**
     * Method - welcome
     *
     * Displays the welcome message
     */
    public void welcome() {
        System.out.println("Hello, Welcome to Battleship!");
    }

    /**
     * Method - exit
     *
     * Displays the exit message and winner
     * @param winner
     */
    public void exit(int win) {
        String output;

        output = String.format("Enemy fleet destroyed. Congratulations player %d!", win);

        System.out.println(output);
    }

    /**
     * Method - shoot
     *
     * Allows a single player to shoot at a coordinate on the other player's board
     * @param player - player number
     * @param sc - Scanner
     * @return - Returns whether the shot hit or missed
     */
    public char shoot(int player, Scanner sc) {
        int r;
        int c;
        char result;

        System.out.println(String.format("Player %d - Enter a row letter from A - J", player));
        r = (int) sc.next().charAt(0) - 65;
        sc.nextLine();
        System.out.println(String.format("Player %d - Enter a column number from 1 - 14", player));
        c = sc.nextInt() - 1;
        sc.nextLine();
        result = 'A';

        if (player == 2) {
            result = playerOneBoard[r][c];
            if (result == 'H') {
                p2Hits++;
                playerOneBoard[r][c] = 'M';
            }
        } else if (player == 1) {
            result = playerTwoBoard[r][c];
            if (result == 'H') {
                p1Hits++;
                playerTwoBoard[r][c] = 'M';
            }
        }

        return result;
    }

    /**
     * Method - incTurn
     *
     * Increments the turn instance variable
     */
    public void incTurn() {
        turn++;
    }

    /**
     * Method - getTurn
     *
     * Returns the turn number
     * @return turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Method - incP1Hits
     *
     * Increments the p1Hits instance variable
     */
    public void incP1Hits() {
        p1Hits++;
    }

    /**
     * Method - incP2Hits
     *
     * Increments the p2Hits instance variable
     */
    public void incP2Hits() {
        p2Hits++;
    }

    /**
     * Method getP1Hits
     *
     * Returns the p1Hits instance variable
     * @return p1Hits
     */
    public int getP1Hits() {
        return p1Hits;
    }

    /**
     * Method - getP2Hits
     *
     * Returns the p2Hits instance variable
     * @return p2Hits
     */
    public int getP2Hits() {
        return p2Hits;
    }

    public char[][] getPlayerOneBoard() {
        return playerOneBoard;
    }

    public char[][] getPlayerTwoBoard() {
        return playerTwoBoard;
    }

    public String getPlayerOneBoardPattern() {
        return playerOneBoardPattern;
    }

    public void setPlayerOneBoardPattern(String playerOneBoardPattern) {
        this.playerOneBoardPattern = playerOneBoardPattern;
    }

    public String getPlayerTwoBoardPattern() {
        return playerTwoBoardPattern;
    }

    public void setPlayerTwoBoardPattern(String playerTwoBoardPattern) {
        this.playerTwoBoardPattern = playerTwoBoardPattern;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getWinner() {
        return winner;
    }

    public String getPlayerOneFileName() {
        return playerOneFileName;
    }

    public String getPlayerTwoFileName() {
        return playerTwoFileName;
    }
}