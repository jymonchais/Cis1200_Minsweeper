package org.cis1200.minesweeper;

import javax.swing.*;

public class SweeperSquare extends JComponent {
    private boolean isBomb;
    private boolean isCovered;
    private boolean isFlagged;
    private int bombDist;;

    public SweeperSquare(boolean isBomb, boolean isFlagged) {
        this.isBomb = isBomb;
        this.isFlagged = isFlagged;
        isCovered = true;

    }

    public SweeperSquare(boolean isBomb, boolean isFlagged, boolean isCovered) {
        this.isBomb = isBomb;
        this.isFlagged = isFlagged;
        this.isCovered = isCovered;

    }

    public boolean getCovered() {
        return isCovered;
    }

    public boolean checkBomb() {
        return isBomb;
    }

    public boolean getFlagged() {
        return isFlagged;
    }

    public int getBombDist() {
        return bombDist;
    }

    public void calcBombDist() {
        bombDist++;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    public void setBomb() {
        isBomb = true;
    }

    public void setFlagged() {
        isFlagged = !isFlagged;
    }
}
