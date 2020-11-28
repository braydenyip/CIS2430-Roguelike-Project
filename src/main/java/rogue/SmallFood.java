package rogue;

public class SmallFood extends Food implements Tossable {

  /**
  * The default constructor.
  */
  public SmallFood() {
    super();
  }

  /**
  * Generates the message that indicates that the item has been thrown.
  * @return the message to be printed upon tossing the item.
  */
  @Override
  public String toss() {
    return ("You throw the " + this.getName());
  }

}
