package rogue;

import java.util.Random;

public class Clothing extends Item implements Wearable {

  private int armourPoints;
  private Random random = new Random();
  /**
  * The default constructor.
  */
  public Clothing() {
    super();
    setArmourPoints(random.nextInt(100));
  }

  /**
  * Returns the amount of protection this item provides.
  * @return (int) the amount of protection
  */
  public int getArmourPoints() {
    return armourPoints;
  }

  /**
  * Returns the amount of protection the clothing provides.
  * @param armourPoints the amount of protection the item provides
  */
  public void setArmourPoints(int newArmourPoints) {
    armourPoints = newArmourPoints;
  }

  /**
  * Generates the message to print to the user when the item is worn.
  * @return (String) a message that indicates the item has been put on.
  */
  @Override
  public String wear() {
    return ("You put on the " + this.getName());
  }

  /**
  * Generates the message to print when the player takes off the item
  * @return (String) a message that indicates the item has been taken off
  */
  @Override
  public String strip() {
    return ("You take off your " + this.getName());
  }
}