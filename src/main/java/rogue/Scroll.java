package rogue;

public class Scroll extends MagicalItem {

  /**
  * The default constructor.
  */

  public Scroll() {
    super();
  }

  /**
  * Generates a string upon opening a Scroll.
  * @return (String) the string to print when scroll is used.
  */
  public String useScroll() {
    return ("You gain " + this.getDescription());
  }

}
