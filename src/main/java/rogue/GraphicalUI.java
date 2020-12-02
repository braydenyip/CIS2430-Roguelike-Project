package rogue;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

import javax.swing.*;
import java.awt.*;

public class GraphicalUI extends JFrame {


    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    // Screen buffer dimensions are different than terminal dimensions
    public static final int COLS = 80;
    public static final int ROWS = 24;
    private final char startCol = 0;
    private final char msgRow = 1;
    private final char roomRow = 3;
    private Container contentPane;

    private JPanel infoPanel;
    private JPanel inventoryPanel;

    private JLabel playerNameLabel;
    private JLabel playerHpLabel;
/**
Constructor.
**/

    public GraphicalUI() {
        super("rogue game");
        contentPane = getContentPane();
        setWindowDefaults();
        setPanels();
        pack();
    }

    private void setWindowDefaults() {
        setTitle("Rogue!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());

    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel, BorderLayout.CENTER);
    }

    private void setPanels() {
        setInfoPanel();
        setInventoryPanel();
        setTerminal();
    }

    private void setInventoryPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        contentPane.add(inventoryPanel, BorderLayout.WEST);
    }

    private void setInfoPanel() {
        infoPanel = new JPanel(new FlowLayout());
        playerNameLabel = new JLabel("DefaultName");
        playerHpLabel = new JLabel("HP: " + 0);
        infoPanel.add(playerNameLabel);
        infoPanel.add(playerHpLabel);
        contentPane.add(infoPanel, BorderLayout.PAGE_START);
    }

    private void providePlayerUpdates(Player thePlayer) {
        updateStats(thePlayer);
        updateInventory(thePlayer);
    }

    private void updateStats(Player thePlayer) {
        playerHpLabel.setText("HP: " + thePlayer.getHp());
        playerNameLabel.setText(thePlayer.getName());
    }

    private void updateInventory(Player thePlayer) {
        inventoryPanel.removeAll();
        for (Item item : thePlayer.getInventoryMap().values()) {
            inventoryPanel.add(new JLabel(item.getId() + ". " + item.getName()));
        }
    }
/**
The controller method for making the game logic work.
@param args command line parameters
**/
  public static void main(String[] args) {
    char userInput = 'h';
    String message;
    String configurationFileLocation = "fileLocations.json";
    //Parse the json files
    RogueParser parser = new RogueParser(configurationFileLocation);
    //allocate memory for the GUI
    GraphicalUI gui = new GraphicalUI();
    // allocate memory for the TextUI -- mostly a set of instructions relating to Lanterna UI
    TextUI tui = new TextUI(gui.terminal);
    // allocate memory for the game and set it up
    Rogue theGame = new Rogue(parser);
   //set up the initial game display
    Player thePlayer = new Player("Brayden");
    theGame.setPlayer(thePlayer);
    theGame.initializeGameState();
    gui.setVisible(true);
    gui.providePlayerUpdates(thePlayer);
    if (theGame.verifyAllRooms()) {
      message = "Welcome to my Rogue game";
      tui.draw(message, theGame.getNextDisplay());
    } else {
      message = "The rooms file could not be used.\n";
      tui.draw(message, "Press 'q' to quit\n");
    }
    while (userInput != 'q' && !(thePlayer.playerIsDead())) {
      //get input from the user
      userInput = tui.getInput();
      //ask the game if the user can move there
      try {
        message = theGame.makeMove(userInput);
        tui.draw(message, theGame.getNextDisplay());
      } catch (InvalidMoveException badMove) {
          tui.setMessage(badMove.getMessage());
      }
      gui.providePlayerUpdates(thePlayer);
    }
    if (thePlayer.playerIsDead()) {
        while (userInput != 'q') {
            userInput = tui.getInput();
            tui.draw(message, "GAME OVER\n Restart the game to try again; press q to quit.");
        }
    }
    System.exit(0);
  }

}
