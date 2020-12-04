package rogue;

public class SmallFood extends Food implements Tossable {

  private static final long serialVersionUID = -3001565396779253307L;

  /**
  * The default constructor.
  */
  public SmallFood() {
    super();
    setType("SmallFood");
  }

  /**
   * Generates the message to indicated that the item has been consumed.
   * @return the message to be printed on consumption
   */
  @Override
  public String consume() {
    return (this.getDescription().split(":"))[0];
  }

  /**
  * Generates the message that indicates that the item has been thrown.
  * @return the message to be printed upon tossing the item.
  */
  @Override
  public String toss() {
    return (this.getDescription().split(":"))[1];
  }

}
