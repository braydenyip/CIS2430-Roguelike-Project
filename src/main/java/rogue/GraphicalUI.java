package rogue;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GraphicalUI extends JFrame implements Serializable {


    private static final long serialVersionUID = -7801391088502547399L;
    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private String inputField;
    private Container contentPane;

    private JPanel infoPanel;
    private JPanel inventoryPanel;
    private JScrollPane scrollableInventory;
    private JPanel descriptivePanel;
    private JLabel descriptiveText;

    private JMenuItem loadFile;
    private JMenuItem loadSymbols;
    private JMenuItem loadGame;
    private JMenuItem saveGame;

    private JFileChooser fileChooser;

    private JLabel playerNameLabel;
    private JLabel playerHpLabel;
    private JLabel playerWealthLabel;
    private JLabel playerApLabel;
    private JLabel playerInvCapLabel;
    private JLabel playerWearCapLabel;

    Rogue theGame;
    Player thePlayer;
    RogueParser parser;


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
        // Parse the json files
        parser = new RogueParser("fileLocations.json");

        // allocate memory for the game and set it up
        theGame = new Rogue(parser);
        thePlayer = theGame.getPlayer();
    }


    private void setWindowDefaults() {
        setTitle("Brayden's Rogue Game!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());
        fileChooser = new JFileChooser(System.getProperty("user.dir"));
    }

    /**
     * Gets the game from the ui including save files.
     * @return the game collected from the ui
     */
    public Rogue getTheGame() {
        return theGame;
    }

    /**
     * Gets the player from the ui including saved player.
     * @return
     */
    public Player getThePlayer() {
        return thePlayer;
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
        saveGame.addActionListener(ev -> saveFileDialog());
        menu.add(saveGame);
        loadGame = new JMenuItem("Load Saved Game");
        loadGame.addActionListener(ev -> loadSaveFileDialog());
        menu.add(loadGame);
    }

    /**
     * Adds file loading items to the menu.
     * @param menu the menu to add the items to.
     */
    private void setFileLoadingItems(JMenu menu) {
        loadFile = new JMenuItem("Load Map File");
        loadFile.addActionListener(ev -> loadGameFileDialog());
        menu.add(loadFile);
        loadSymbols = new JMenuItem("Load Symbols File");
        loadSymbols.addActionListener(ev -> loadSymbolsFileDialog());
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
        updateInventoryPanelBlank();
        setDescriptivePanel();
        setTerminal();
    }

    private void setInfoPanel() {
        infoPanel = new JPanel(new FlowLayout());
        initializeStatsLabels();
        addStatsLabels();
        contentPane.add(infoPanel, BorderLayout.PAGE_START);
    }

    private void initializeStatsLabels() {
        playerNameLabel = new JLabel("???");
        playerNameLabel.setFont(new Font("Helvetica", Font.ITALIC, 12));
        playerHpLabel = new JLabel("HP: " + 0);
        playerHpLabel.setForeground(new Color(130, 20, 0));
        playerApLabel = new JLabel("AP: " + 0);
        playerApLabel.setForeground(new Color(95, 175, 250));
        playerInvCapLabel = new JLabel("Items: " + 0 + "/" + 0);
        playerWealthLabel = new JLabel("Gold: " + 0);
        playerWearCapLabel = new JLabel("Worn: " + 0 + "/" + 0);
    }

    private void addStatsLabels() {
        infoPanel.add(playerNameLabel);
        infoPanel.add(playerHpLabel);
        infoPanel.add(playerApLabel);
        infoPanel.add(playerWealthLabel);
        infoPanel.add(playerInvCapLabel);
        infoPanel.add(playerWearCapLabel);
    }

    private void setInventoryPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        scrollableInventory = new JScrollPane(inventoryPanel);
        scrollableInventory.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollableInventory.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(scrollableInventory, BorderLayout.LINE_END);
    }

    private void setDescriptivePanel() {
        descriptivePanel = new JPanel();
        descriptiveText = new JLabel();
        descriptiveText.setFont(new Font("Helvetica", Font.BOLD, 12));
        descriptivePanel.add(descriptiveText);
        contentPane.add(descriptivePanel, BorderLayout.PAGE_END);
    }

    private void setDescriptive(String toDisplay) {
        descriptiveText.setText(toDisplay);
    }

    private void loadGameFileDialog() {
        int retval = fileChooser.showOpenDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                theGame.refreshGameState(null, file);
            } catch (Exception e) {
                if (fileErrorDialog() == JOptionPane.YES_OPTION) {
                    loadGameFileDialog();
                } else {
                    System.exit(0);
                }
            }
        } else {
            descriptiveText.setText("Loading cancelled.");
        }
    }

    private void loadSymbolsFileDialog() {
        int retval = fileChooser.showOpenDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                theGame.refreshGameState(file, null);
            } catch (Exception e) {
                if (fileErrorDialog() == JOptionPane.YES_OPTION) {
                    loadSymbolsFileDialog();
                } else {
                    System.exit(0);
                }
            }
        } else {
            descriptiveText.setText("Loading cancelled.");
        }
    }

    private void loadSaveFileDialog() {
        int res = fileChooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            load(fileChooser.getSelectedFile());
        } else if (res == JFileChooser.CANCEL_OPTION) {
            descriptiveText.setText("Loading cancelled.");
        } else {
            descriptiveText.setText("Error loading the save file");
        }
    }

    private void load(File savefile) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(savefile)); ) {
            theGame = (Rogue)in.readObject();
            descriptiveText.setText("Game successfully loaded.");
        } catch (Exception e) {
            descriptiveText.setText(e.getMessage());
        }
    }

    private void saveFileDialog() {
        int res = fileChooser.showSaveDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            descriptiveText.setText(save(fileChooser.getSelectedFile()));
        } else if (res == JFileChooser.CANCEL_OPTION) {
            descriptiveText.setText("Saving canceled.");
        } else {
            descriptiveText.setText("User interaction error.");
        }
    }

    private String save(File sv) {
        try {
            FileOutputStream outputStream = new FileOutputStream(sv);
            ObjectOutputStream objOutStream = new ObjectOutputStream(outputStream);
            objOutStream.writeObject(theGame);
            objOutStream.close();
            outputStream.close();
            return "File saved successfully";
        } catch (IOException ioe) {
            return ioe.getMessage();
        }
    }

    private void getNewName() {
        inputField = JOptionPane.showInputDialog("What do you want to name your character?");
        playerNameLabel.setText(inputField);
        thePlayer.setName(inputField);
    }

    private void gameOverDialog() {
        JOptionPane.showMessageDialog(this, "GAME OVER! Restart to try again.");
    }

    private int fileErrorDialog() {
        String msg = "This file could not be used.\n" + "Try another file?\n";
        return JOptionPane.showConfirmDialog(this,msg, "Error!", JOptionPane.YES_NO_OPTION);
    }

    private Item itemSelectDialog(char input, Inventory inventory) {
        Item resultItem = null;
        if (input == Rogue.WEAR) {
            if (thePlayer.hasFullWearables()) {
                JOptionPane.showMessageDialog(this, "You can't wear any more stuff!");
            } else {
                resultItem = selectWearableDialog(inventory);
            }
        } else if (input == Rogue.CONSUME) {
            resultItem = selectConsumableDialog(inventory);
        } else if (input == Rogue.TOSS) {
            resultItem = selectTossableDialog(inventory);
        }
        return resultItem;
    }

    private Item selectWearableDialog(Inventory inventory) {
        String result = "h";
        String msg = "What item would you like to wear?";
        String title = "What to wear?";
        while (true) {
            result = JOptionPane.showInputDialog(this, msg, title, JOptionPane.PLAIN_MESSAGE);
            if (result == null) {
                return null;
            }
            try {
                Item item = inventory.get(Integer.parseInt(result));
                if (item == null) {
                    msg = "You don't have this item.";
                } else if (!(item instanceof Wearable)) {
                    msg = "This item isn't wearable.";
                } else {
                    return item;
                }
            } catch (NumberFormatException e) {
                msg = "I'm sorry, that isn't a valid number.";
            }
        }
    }

    private Item selectConsumableDialog(Inventory inventory) {
        String result = "h";
        String msg = "What do you want to eat/drink?";
        String title = "Consume?";
        while (true) {
            result = JOptionPane.showInputDialog(this, msg, title, JOptionPane.PLAIN_MESSAGE);
            if (result == null) {
                return null;
            }
            try {
                Item item = inventory.get(Integer.parseInt(result));
                if (item == null) {
                    msg = "You don't have this item.";
                } else if (!(item instanceof Consumable)) {
                    msg = "This item isn't edible.";
                } else {
                    return item;
                }
            } catch (NumberFormatException e) {
                msg = "I'm sorry, that isn't a valid number.";
            }
        }
    }

    private Item selectTossableDialog(Inventory inventory) {
        String result = "h";
        String msg = "What item do you want to toss?";
        String title = "Throw?";
        while (true) {
            result = JOptionPane.showInputDialog(this, msg, title, JOptionPane.PLAIN_MESSAGE);
            if (result == null) {
                return null;
            }
            try {
                Item item = inventory.get(Integer.parseInt(result));
                if (item == null) {
                    msg = "You don't have this item.";
                } else if (!(item instanceof Tossable)) {
                    msg = "You can't throw this item.";
                } else {
                    return item;
                }
            } catch (NumberFormatException e) {
                msg = "I'm sorry, that isn't a valid number.";
            }
        }
    }

    private void providePlayerUpdates() {
        updateStats();

    }

    private void updateStats() {
        thePlayer.setName(inputField);
        playerNameLabel.setText(thePlayer.getName());
        playerHpLabel.setText("HP: " + thePlayer.getHp());
        playerApLabel.setText("AP: " + thePlayer.getAp());
        playerWealthLabel.setText("Gold: " + 0);
        int numItems = thePlayer.getInventory().getNumberOfItems();
        int cap = thePlayer.getInventory().getCapacity();
        playerInvCapLabel.setText("Items: " + numItems + "/" + cap);
        int numWearables = thePlayer.getWearables().getNumberOfItems();
        int capWearables = thePlayer.getWearables().getCapacity();
        playerWearCapLabel.setText("Wearables: " + numWearables + "/" + capWearables);
    }

    private void updateInventoryPanel() {
        inventoryPanel.removeAll();
        Inventory anInventory = thePlayer.getInventory();
        Inventory theWearables = thePlayer.getWearables();
        if (anInventory.getNumberOfItems() < 1) {
            updateInventoryPanelBlank();
        } else {
            for (Item item : anInventory.getInventory().values()) {
                JButton b1 = makeInvButton(item.toString());
                b1.addActionListener(ev -> setDescriptive(item.getDescription()));
                b1.setBackground(new Color(20, 17, 97));
                b1.setForeground(new Color(255, 255, 255));
                inventoryPanel.add(b1);
            }
            for (Item w : theWearables.getInventory().values()) {
                JButton bw = makeInvButton(makeWearString(w));
                bw.addActionListener(ev -> setDescriptive(w.getDescription()));
                bw.setBackground(Color.GREEN);
                inventoryPanel.add(bw);
            }
        }
    }

    private String makeWearString(Item w) {
        return w.toString() + " (worn)";
    }
    private void updateInventoryPanelBlank() {
        inventoryPanel.add(makeInvButton("Your backpack is empty..."));
    }

    private JButton makeInvButton(String buttonName) {
        JButton b1 = new JButton();
        b1.setText(buttonName);
        b1.setPreferredSize(new Dimension(200,50));
        return b1;
    }

/**
The controller method for making the game logic work.
@param args command line parameters
**/
  public static void main(String[] args) {
    char userInput = 'h';
    String message;

    //allocate memory for the GUI and Terminal UI
    GraphicalUI gui = new GraphicalUI();
    TextUI tui = new TextUI(gui.terminal);

    gui.theGame.initializeGameState();

    //set up the initial game display
    gui.setVisible(true);

    if (gui.theGame.verifyAllRooms()) {
      message = "Click inside the window to start.";
      tui.draw(message, gui.theGame.getNextDisplay());
    } else {
      message = "The rooms file could not be used.\n";
      tui.draw(message, "Press 'q' to quit\n");
    }

    // ask the player for name before starting.
    gui.getNewName();
    gui.providePlayerUpdates();
    gui.updateInventoryPanel();

    while (userInput != 'q' && !(gui.thePlayer.playerIsDead())) {
      gui.providePlayerUpdates();
      //get input from the user
      userInput = tui.getInput();
      //ask the game if the user can move there
      try {
        message = gui.theGame.makeMove(userInput);
        if (gui.theGame.playerDoesInventoryAction(userInput)) {
            Item toUse = gui.itemSelectDialog(userInput, gui.thePlayer.getInventory());
            message = gui.theGame.decideHowToUse(toUse, userInput);
            gui.updateInventoryPanel();
        }
        tui.draw("", gui.theGame.getNextDisplay());
        gui.setDescriptive(message);
        if (gui.theGame.isItemPickedUp()) {
            gui.updateInventoryPanel();
        }
      } catch (InvalidMoveException badMove) {
          gui.setDescriptive(badMove.getMessage());
      }
    }

    if (gui.thePlayer.playerIsDead()) {
        gui.gameOverDialog();
    }

    System.exit(0);
  }

}
