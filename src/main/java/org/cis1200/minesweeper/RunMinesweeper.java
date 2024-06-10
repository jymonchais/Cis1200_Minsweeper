package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;

public class RunMinesweeper implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Welcome to MineSweeper!");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // instructions
        final JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> showInstructions(frame));
        control_panel.add(instructionsButton);

        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(e -> board.getmSweep().saveToFile());
        control_panel.add(saveButton);

        JButton loadButton = new JButton("Load Game");
        loadButton.addActionListener(e -> board.getmSweep().loadFile());
        control_panel.add(loadButton);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }

    private void showInstructions(JFrame parent) {
        String instructionsText = "<html><body style='width: 200px;'>" +
                "<h1>Minesweeper Instructions</h1>" +
                "<p> Welcome to my game, Minesweeper! This is a grid game where you " +
                "mark the locations of mines from the clues given by neighnors. " +
                "Avoid detonating any mines!</p>"
                +
                "<h2>Controls:</h2>" +
                "<ul>" +
                "<li><b>Left Click:</b> Reveal the cell at the given position.</li>" +
                "<li><b>Right Click:</b> Toggle the flag marker on a given cell.</li>" +
                "</ul>" +
                "<h2>Objective:</h2>" +
                "<p>Reveal the cells without mines to win.</p>" +
                "</body></html>";
        JOptionPane.showMessageDialog(
                parent, instructionsText, "Game Instructions", JOptionPane.INFORMATION_MESSAGE
        );
    }
}