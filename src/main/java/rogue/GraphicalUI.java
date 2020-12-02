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
    private String inputField;
    private Container contentPane;

    private JPanel infoPanel;
    private JPanel inventoryPanel;


    private JLabel playerNameLabel;
    private JLabel playerHpLabel;
    private JLabel playerInvCapLabel;
/**
Constructor.
**/

    public GraphicalUI() {
        super("rogue game");
        setWindowMenu();
        contentPane = getContentPane();
        setWindowDefaults();
        setPanels();
        pack();
    }

    private void setWindowDefaults() {
        setTitle("Brayden's Rogue Game!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());
    }

    /**
     * Adds a menu bar to the window.
     */
    private void setWindowMenu() {
        JMenuBar optionsMenuBar = new JMenuBar();
        setJMenuBar(optionsMenuBar);
        JMenu fileMenu = new JMenu("File");
        optionsMenuBar.add(fileMenu);
        setSaveGameItems(fileMenu);
        setFileLoadingItems(fileMenu);
        JMenuItem changeName = new JMenuItem("Change Player Name");
        fileMenu.add(changeName);
        changeName.addActionListener(ev -> getNewName());
        JMenuItem quitGame = new JMenuItem("Quit");
        quitGame.addActionListener(ev -> System.exit(0));
        fileMenu.add(quitGame);
    }

    /**
     * Adds saving and loading methods to the menu.
     * @param menu the menu to add the items to.
     */
    private void setSaveGameItems(JMenu menu) {
        JMenuItem saveGame = new JMenuItem("Save");
        menu.add(saveGame);
        JMenuItem loadGame = new JMenuItem("Load Saved Game");
        menu.add(loadGame);
    }

    /**
     * Adds file loading items to the menu.
     * @param menu the menu to add the items to.
     */
    private void setFileLoadingItems(JMenu menu) {
        JMenuItem loadFile = new JMenuItem("Load Map File");
        menu.add(loadFile);
        JMenuItem loadSymbols = new JMenuItem("Load Symbols File");
        menu.add(loadSymbols);
    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel, BorderLayout.CENTER);
    }

    private void setPanels() {
        setInfoPanel();
        setTerminal();
    }

    private void setInfoPanel() {
        infoPanel = new JPanel(new FlowLayout());
        playerNameLabel = new JLabel("-");
        playerHpLabel = new JLabel("HP: " + 0);
        playerInvCapLabel = new JLabel("Items: " + 0 + "/" + 0);
        infoPanel.add(playerNameLabel);
        infoPanel.add(playerHpLabel);
        infoPanel.add(playerInvCapLabel);
        contentPane.add(infoPanel, BorderLayout.PAGE_START);
    }

    private void getNewName() {
        inputField = JOptionPane.showInputDialog("What do you want to name your character?");
        playerNameLabel.setText(inputField);
    }

    private void gameOverDialog() {
        JOptionPane.showMessageDialog(this, "GAME OVER! Restart to try again.");
    }

    private void providePlayerUpdates(Player thePlayer) {
        updateStats(thePlayer);
    }

    private void updateStats(Player thePlayer) {
        thePlayer.setName(inputField);
        playerNameLabel.setText(thePlayer.getName());
        playerHpLabel.setText("HP: " + thePlayer.getHp());
        int numItems = thePlayer.getInventory().getNumberOfItems();
        int cap = thePlayer.getInventory().getCapacity();
        playerInvCapLabel.setText("Items: " + numItems + "/" + cap);
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
    if (theGame.verifyAllRooms()) {
      message = "Welcome to my Rogue game";
      tui.draw(message, theGame.getNextDisplay());
    } else {
      message = "The rooms file could not be used.\n";
      tui.draw(message, "Press 'q' to quit\n");
    }
    gui.getNewName();
    gui.providePlayerUpdates(thePlayer);
    while (userInput != 'q' && !(thePlayer.playerIsDead())) {
      gui.providePlayerUpdates(thePlayer);
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
        gui.gameOverDialog();
    }
    System.exit(0);
  }

}
