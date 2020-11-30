package rogue;

import javax.swing.*;
import java.awt.*;

/**
* The GraphicalUI class makes a JFrame that contains the entire game UI.
*/
public class GraphicalUI extends JFrame {

  public static final int WIDTH = 800;
  public static final int HEIGHT = 700;

  /**
  * Default GUI constructor.
  */
  public GraphicalUI() {
    super();
  }

  public static void main(String[] args) {
    System.out.println("---");
    GraphicalUI gui = new GraphicalUI();
    char userInput = 'h';
    String message;
    String configurationFileLocation = "fileLocations.json";
    //Parse the json files
    RogueParser parser = new RogueParser(configurationFileLocation);
    //allocate memory for the GUI
    TextUI theGameUI = new TextUI();
    // allocate memory for the game and set it up
    Rogue theGame = new Rogue(parser);
   //set up the initial game display
    Player thePlayer = new Player("Brayden");
    theGame.setPlayer(thePlayer);
    theGame.initializeGameState();

    if (theGame.verifyAllRooms()) {
      message = "Welcome to my Rogue game";
      theGameUI.draw(message, theGame.getNextDisplay());
    } else {
      message = "The rooms file could not be used.\n";
      theGameUI.draw(message, "Press 'q' to quit\n");
    }

    while (userInput != 'q') {
      //get input from the user
      userInput = theGameUI.getInput();

      //ask the game if the user can move there
      try {
        message = theGame.makeMove(userInput);
        theGameUI.draw(message, theGame.getNextDisplay());
      } catch (InvalidMoveException badMove) {
          theGameUI.setMessage(badMove.getMessage());
      }
    }
    // do something here to say goodbye to the user
    System.out.println("Thanks for playing!");
  }
}
