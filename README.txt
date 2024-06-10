=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 88641152
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays - I used a 2D compiled of the game pieces called SweeperSquares to compile
  the actual board. This was an appropriate use as I not only built the game by searching
  through this 2D array, but for almost all the main functions I was looping through this 2D
  array and making changes to each square. It made it very easy to target a specific
  point on the screen without effecting an unwanted square.

  2. File I/O - I used this concept to implement a save and load feature. From this, users are
  able to save a game at any given point and make sure they can load up the game from where
  they started. This is an appropriate use as it uses the same classes at the TwitterBot homework
  and shares many of the same ideas.

  3. JUnit Testable Component - I used this to test the main feature of the Minesweeper game state.
  This was important as I need to make sure that under the hood everything is working correctly
  without use of the GUI. This was an appropriate use of the concept as it tests for not just
  passing but also failure.

  4. Recursion - I was able to use recursion to clear the board. This was a vital part of the game
  as it's basically the main function of mine sweeper. This part of the implementation was
  rather difficult, but rewarding in the end. I ended up creating a helper to avoid unnecessary
  checks that every square didn't need to go through. I got help from a few TA's in office hours
  and finally got it to work after so many iterations. This was a correct implementation as there
  is the necessary base cases and points when the recursion is able to escape and 'climb' back up.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
    GameBoard - This is the game baord which interacts mainly with Minesweeper class.
    This class combines the logic of the game with the swing library and makes use
    of the Mouse interaction that the user creates. It also houses the creation of
    the sweeper board for each opening of th game.

    Minesweeper - This is the game logic. In this file, the 2D array of the
    SweeperSquares is created and holds the main functions which interacts with these
    squares. Its the most important file, as without the user cant actually use any logic
    of the game.

    SweeperSquare - This is the class which represents each of the squares in the game.
    In this file, a square is defined with information such as being a bomb, being flagged,
    and if its covered or not. Its what the user is actually clicks on in the Gameboard itself.


    RunMinesweeper - This is the class that creates the actual GUI of the game. Its important
    as it houses all the buttons and allows for the user to interact with a creation of the
    gameboard.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

It was very hard to make the recursion, but it thankfully worked in the end

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

There is as its seperated with the Minesweeper class and gui. Its also encapsulated
fairly well, while the user isn't able to really effect the state through the gui.
I would refactor a field places and clean up the code given some more time.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
