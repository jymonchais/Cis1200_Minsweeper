package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBoard extends JPanel {

    private Minesweeper mSweep; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 450;
    public static final int BOARD_HEIGHT = 450;
    public static final int CELL_SIZE = 50;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // Set layout and background properties
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);
        setFocusable(true);

        int ratio = BOARD_WIDTH / CELL_SIZE;

        this.mSweep = new Minesweeper(ratio, ratio); // correct board dimensions
        status = statusInit;
        mSweep.buildGame();

        // Mouse listener to handle user clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() * 9 / BOARD_WIDTH;
                int y = e.getY() * 9 / BOARD_HEIGHT;
                if (SwingUtilities.isLeftMouseButton(e) && mSweep.getCell(x, y).getCovered()) {
                    mSweep.uncover(y, x);
                    repaint();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    mSweep.changeFlag(x, y);
                    repaint();
                }
                updateStatus();
                repaint();
            }
        });
    }

    public Minesweeper getmSweep() {
        return mSweep;
    }

    /**
     * Updates the status JLabel based on the game state.
     */
    private void updateStatus() {
        if (mSweep.isGameOver()) {
            status.setText("Game Over, You Lose :(");
        } else if (mSweep.hasWon()) {
            status.setText("You win!");
        } else {
            status.setText(
                    "Mark all 10 bombs before running out! " +
                            "Flag Count: " + mSweep.getFlagsLeft()
            );
        }
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        mSweep.reset();
        status.setText(
                "Mark all 10 bombs before running out! " +
                        "Flag Count: " + mSweep.getFlagsLeft()
        );
        repaint();
        requestFocusInWindow();
    }

    /**
     * Draws the game board, cell by cell.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SweeperSquare sq = mSweep.getCell(i, j);

                int bombs = mSweep.countNearbyBombs(i, j);
                if (bombs > 0) {
                    g.setColor(Color.RED);
                    g.drawString(
                            Integer.toString(bombs), i * CELL_SIZE + 20,
                            j * CELL_SIZE + 30
                    );
                }

                if (sq.getFlagged()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else if (!sq.getCovered()) {
                    if (sq.checkBomb()) {
                        g.setColor(Color.RED);
                        g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    } else {
                        g.drawRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                } else {
                    g.setColor(Color.GRAY);
                    g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        // create lines for board
        for (int i = 0; i <= 9; i++) {
            int x = i * CELL_SIZE;
            g.setColor(Color.BLUE);
            g.drawLine(x, 0, x, BOARD_HEIGHT);
        }

        for (int i = 0; i <= 9; i++) {
            int y = i * CELL_SIZE;
            g.setColor(Color.BLUE);
            g.drawLine(0, y, BOARD_WIDTH, y);
        }
    }

    /**
     * Returns the preferred size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
