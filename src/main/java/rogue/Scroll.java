package rogue;

public class Scroll extends MagicalItem {

  private static final long serialVersionUID = -5130581301863097171L;

  /**
  * The default constructor.
  */

  public Scroll() {
    super("Scroll");
  }

  /**
  * Generates a string upon opening a Scroll.
  * @return (String) the string to print when scroll is used.
  */
  public String useScroll() {
    return (this.getDescription());
  }

}
