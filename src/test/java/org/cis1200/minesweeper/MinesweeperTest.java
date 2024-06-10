package org.cis1200.minesweeper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class MinesweeperTest {

    @Test
    public void test() {
        assertNotEquals("CIS 1200", "CIS 1600");
    }

    @Test
    void testInitialSetup() {
        Minesweeper game = new Minesweeper(9, 9);
        game.buildGame();
        int bombCount = 0;

        for (int i = 0; i < game.getbWidth(); i++) {
            for (int j = 0; j < game.getbHeight(); j++) {
                if (game.getCell(i, j).checkBomb()) {
                    bombCount++;
                }
            }
        }
        assertTrue(bombCount > 0, "There should be bombs on the board.");
    }

    @Test
    void testReset() {
        Minesweeper game = new Minesweeper(9, 9);
        game.buildGame();
        game.uncover(1, 1);
        game.reset();

        assertTrue(game.getCell(1, 1).getCovered(), "Should be covered.");
    }

    @Test
    void testUncover() {
        Minesweeper game = new Minesweeper(9, 9);
        game.reset();
        game.getCell(0, 0).setBomb();
        game.uncover(1, 1);
        assertFalse(game.getCell(1, 1).getCovered(), "The cell should be uncovered.");
        assertFalse(game.isGameOver(), "Game should not be over as no bomb is hit.");
    }

    @Test
    void testFlag() {
        Minesweeper game = new Minesweeper(9, 9);
        game.buildGame();
        game.changeFlag(0, 0); // Flagging
        assertTrue(game.getCell(0, 0).getFlagged(), "Cell should be flagged.");

        game.changeFlag(0, 0); // Unflagging
        assertFalse(game.getCell(0, 0).getFlagged(), "Cell should not be flagged anymore.");
    }

    @Test
    void testGameOverByBomb() {
        Minesweeper game = new Minesweeper(10, 10);
        game.reset();
        game.getCell(0, 0).setBomb();
        game.uncover(0, 0);

        assertTrue(game.isGameOver(), "Game should be over after hitting a bomb.");
    }

    @Test
    void testSaveAndLoad() {
        Minesweeper game = new Minesweeper(9, 9);
        game.buildGame();
        game.saveToFile();
        Minesweeper loadedGame = new Minesweeper(9, 9);
        loadedGame.loadFile();

        for (int i = 0; i < game.getbWidth(); i++) {
            for (int j = 0; j < game.getbHeight(); j++) {
                SweeperSquare original = game.getCell(i, j);
                SweeperSquare loaded = loadedGame.getCell(i, j);
                assertEquals(
                        original.checkBomb(), loaded.checkBomb(),
                        "Bomb state should match after loading."
                );
                assertEquals(
                        original.getCovered(), loaded.getCovered(),
                        "Covered state should match after loading."
                );
                assertEquals(
                        original.getFlagged(), loaded.getFlagged(),
                        "Flagged state should match after loading."
                );
            }
        }
    }
}
