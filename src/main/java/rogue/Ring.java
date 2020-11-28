package rogue;

public class Ring extends MagicalItem implements Wearable {

  /**
  * The default constructor.
  */
  public Ring() {
    super("Ring");
  }

  /**
  * Generates a message to print when the user equips a ring.
  * @return (String) the message to be printed on wearing.
  */
  @Override
  public String wear() {
    return ("You put on the " + this.getName());
  }

  /**
  * Generates a message to print when the user takes off a ring.
  * @return (String) the message to be printed on removal.
  */
  @Override
  public String strip() {
    return ("You take off the " + this.getName());
  }
}
