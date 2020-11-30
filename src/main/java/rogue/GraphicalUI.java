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
  }
}
