package org.cis1200.minesweeper;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class Minesweeper {
    private SweeperSquare[][] board;
    private int bombsLeft;
    private int flagsLeft;
    private int bombsFlagged;
    private final int bWidth;
    private final int bHeight;
    private boolean gameOver;

    /**
     * Constructor sets up game state.
     */
    public Minesweeper(int bWidth, int bHeight) {
        this.bWidth = bWidth;
        this.bHeight = bHeight;
        board = new SweeperSquare[bWidth][bHeight];
        bombsLeft = 0;
        flagsLeft = 10;
        bombsFlagged = 0;
        gameOver = false;
    }

    /* Build the board of squares for the user to interact with */
    /* Build the board of squares for the user to interact with */
    public void buildGame() {
        bombsLeft = 0; // Reset bombs count
        for (int i = 0; i < bWidth; i++) {
            for (int j = 0; j < bHeight; j++) {
                boolean hasBomb = Math.random() < 0.15625; // Approx 12.5% bomb probability
                board[i][j] = new SweeperSquare(hasBomb, false);
                if (hasBomb) {
                    bombsLeft++;
                }
            }
        }
    }

    public SweeperSquare[][] getBoard() {
        SweeperSquare[][] encapsulate = board;
        return encapsulate;
    }

    public int getbWidth() {
        return bWidth;
    }

    public int getbHeight() {
        return bHeight;
    }

    /* Handles a left click */
    public void uncover(int x, int y) {
        SweeperSquare sQ = board[x][y];
        if (sQ.checkBomb()) {
            sQ.setCovered(false);
            gameOver = true;
            return;
        }
        if (!sQ.getCovered() || sQ.getFlagged()) {
            return; // Ignore clicks on uncovered or flagged cells
        }
        sQ.setCovered(false);
        uncoverHelper(x, y, true);
    }

    public void uncoverHelper(int x, int y, boolean firstClick) {
        int numBombs = countNearbyBombs(x, y);
        if (numBombs == 0 || firstClick) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if ((i != x || j != y) && i >= 0 && j >= 0 && i < bWidth && j < bHeight) {
                        SweeperSquare adjSquare = board[i][j];
                        if (adjSquare.getCovered() && !adjSquare.getFlagged()
                                && !adjSquare.checkBomb()) {
                            adjSquare.setCovered(false);
                            uncoverHelper(j, i, false);
                        }
                    }
                }
            }
        }
    }

    /**
     * Randomly places a specified number of bombs on the Minesweeper board.
     */
    public void placeBombs(int numBombs) {
        int placedBombs = 0;
        while (placedBombs < numBombs) {
            int x = (int) (Math.random() * bWidth);
            int y = (int) (Math.random() * bHeight);
            if (!board[x][y].checkBomb()) { // Ensure the cell does not already contain a bomb
                board[x][y].setBomb();
                placedBombs++;
            }
        }
    }

    /**
     * Counts the number of bombs in the adjacent cells around a given cell.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return the number of bombs around the cell
     */
    public int countNearbyBombs(int x, int y) {
        int count = 0;
        // Check all adjacent cells
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i != x || j != y) && i >= 0 && j >= 0 && i < bWidth && j < bHeight) {
                    if (board[j][i].checkBomb()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void changeFlag(int x, int y) {
        SweeperSquare sQ = board[y][x];
        if (!sQ.getCovered())
            return; // Avoid flagging uncovered squares

        boolean wasFlagged = sQ.getFlagged();
        sQ.setFlagged(); // Toggle flag status

        if (!wasFlagged && sQ.getFlagged()) {
            if (sQ.checkBomb()) {
                bombsFlagged++;
            }
            flagsLeft--;
        } else if (wasFlagged && !sQ.getFlagged()) {
            if (sQ.checkBomb()) {
                bombsFlagged--;
            }
            flagsLeft++;
        }
    }

    public int getFlagsLeft() {
        return flagsLeft;
    }

    public boolean hasWon() {
        if (bombsFlagged != bombsLeft)
            return false; // Ensure all bombs are flagged
        for (SweeperSquare[] row : board) {
            for (SweeperSquare sq : row) {
                if ((sq.checkBomb() && !sq.getFlagged()) || (!sq.checkBomb() && sq.getCovered())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return ((flagsLeft == 0 && (bombsFlagged != bombsLeft)) || gameOver);
    }

    public SweeperSquare getCell(int c, int r) {
        return board[r][c];
    }

    public void saveToFile() {
        List<String> lines = getStrings();

        // Path file = Paths.get("minesweeper_save.txt");
        try {
            FileWriter outFile = new FileWriter("files/Game-board.txt");
            BufferedWriter buffFileWriter = new BufferedWriter(outFile);
            for (String s : lines) {
                buffFileWriter.write(s);
                buffFileWriter.newLine();
            }
            buffFileWriter.close();
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    private List<String> getStrings() {
        List<String> lines = new ArrayList<>();

        lines.add(bWidth + " " + bHeight); // First line is the dimensions of the board

        for (int i = 0; i < bWidth; i++) {
            for (int j = 0; j < bHeight; j++) {
                int bombBool = 0;
                int coveredBool = 0;
                int flaggedBool = 0;
                SweeperSquare sq = board[i][j];
                if (sq.checkBomb()) {
                    bombBool = 1;
                } else if (!sq.getCovered()) {
                    coveredBool = 1;
                } else if (sq.getFlagged()) {
                    flaggedBool = 1;
                }

                lines.add(bombBool + " " + coveredBool + " " + flaggedBool);
            }
        }
        return lines;
    }

    public void loadFile() {
        Path file = Paths.get("files/Game-board.txt");
        try {
            List<String> lines = Files.readAllLines(file);
            String[] dimensions = lines.get(0).split(" ");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            // for debugging if lines would ever save incorrectly
            if (width != bWidth || height != bHeight) {
                System.out.println("Saved game dimensions do not match current game settings.");
                return;
            }

            int lineIndex = 1;

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    String[] cellData = lines.get(lineIndex).split(" ");
                    SweeperSquare sq = getSweeperSquare(cellData);
                    // sq.setFlagged();
                    board[i][j] = sq;
                    lineIndex++;
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load game: " + e.getMessage());
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Error parsing saved game file: " + e.getMessage());
        }
    }

    private static SweeperSquare getSweeperSquare(String[] cellData) {
        boolean hasBomb = false;
        boolean isCovered = true;
        boolean isFlagged = false;
        if (cellData[0].equals("1")) {
            hasBomb = true;
        } else if (cellData[1].equals("1")) {
            isCovered = false;
        } else if (cellData[2].equals("1")) {
            isFlagged = true;
        }

        return new SweeperSquare(hasBomb, isFlagged, isCovered);
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new SweeperSquare[bWidth][bHeight]; // Initialize the board
        for (int i = 0; i < bWidth; i++) {
            for (int j = 0; j < bHeight; j++) {
                board[i][j] = new SweeperSquare(false, false); // Initialize each cell
            }
        }
        placeBombs(10); // Adjust the number of bombs as needed
        bombsLeft = 10;
        flagsLeft = 10;
        gameOver = false;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
    }
}
