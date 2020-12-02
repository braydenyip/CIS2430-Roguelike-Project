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
    private JScrollPane scrollableInventory;

    private JMenuItem loadFile;
    private JMenuItem loadSymbols;
    private JMenuItem loadGame;
    private JMenuItem saveGame;

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
        saveGame = new JMenuItem("Save");
        menu.add(saveGame);
        loadGame = new JMenuItem("Load Saved Game");
        menu.add(loadGame);
    }

    /**
     * Adds file loading items to the menu.
     * @param menu the menu to add the items to.
     */
    private void setFileLoadingItems(JMenu menu) {
        loadFile = new JMenuItem("Load Map File");
        menu.add(loadFile);
        loadSymbols = new JMenuItem("Load Symbols File");
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
        setInventoryPanel();
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

    private void setInventoryPanel() {
        inventoryPanel = new JPanel();
        scrollableInventory = new JScrollPane(inventoryPanel);
        scrollableInventory.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableInventory.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JButton b1 = new JButton();
        b1.setText("Press me");
        b1.addActionListener(ev -> System.exit(0));
        inventoryPanel.add(b1);
        contentPane.add(scrollableInventory, BorderLayout.LINE_START);
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

    // Parse the json files
    RogueParser parser = new RogueParser(configurationFileLocation);

    //allocate memory for the GUI and Terminal UI
    GraphicalUI gui = new GraphicalUI();
    TextUI tui = new TextUI(gui.terminal);

    // allocate memory for the game and set it up
    Rogue theGame = new Rogue(parser);
    Player thePlayer = new Player("Brayden");
    theGame.setPlayer(thePlayer);
    theGame.initializeGameState();

    //set up the initial game display
    gui.setVisible(true);

    if (theGame.verifyAllRooms()) {
      message = "Welcome to my Rogue game";
      tui.draw(message, theGame.getNextDisplay());
    } else {
      message = "The rooms file could not be used.\n";
      tui.draw(message, "Press 'q' to quit\n");
    }

    // ask the player for name before starting.
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
