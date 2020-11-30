package rogue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.JTextArea;

//import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Container;

/**
* The GraphicalUI class makes a JFrame that contains the entire game UI.
*/
public class GraphicalUI extends JFrame {

  public static final int WIDTH = 1000;
  public static final int HEIGHT = 700;
  private Container contentPane;

  /**
  * Default GUI constructor.
  */
  public GraphicalUI() {
    super();
    setDefaults();
    setLabels();
  }

  /**
  * Sets the basic parameters of the game window.
  */
  public void setDefaults() {
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    contentPane = getContentPane();
    contentPane.setLayout(new GridBagLayout());
  }

  /**
  * Sets the labels.
  */
  public void setLabels() {
    JPanel messagePanel = new JPanel();
    JPanel instructPanel = new JPanel();
    messagePanel.add(new JLabel("This is a test message"));
    instructPanel.add(new JLabel("Press the button in the upper-right corner to exit."));
    contentPane.add(messagePanel);
    contentPane.add(instructPanel);
  }

  /**
  * Sets the statistical fields.
  * @param playerName the name of the player
  */
  public void setStatsPanel(String playerName) {
    JPanel statsPanel = new JPanel();
    JLabel playerNameLabel = new JLabel(playerName);
    statsPanel.add(playerNameLabel);
    contentPane.add(statsPanel);
  }
  /**
  * The main executable.
  * @param args this program does not use any command line arguments.
  */
  public static void main(String[] args) {
    GraphicalUI gui = new GraphicalUI();
    char userInput = 'h';
    String message;
    String configurationFileLocation = "fileLocations.json";
    //Parse the json files
    RogueParser parser = new RogueParser(configurationFileLocation);
    gui.setStatsPanel("Brayden");
    gui.setVisible(true);
  }
}
