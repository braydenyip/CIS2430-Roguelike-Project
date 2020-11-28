package rogue;

import java.util.Random;

public class Food extends Item implements Consumable {

  private int heal; // how much this item heals you for
  private Random random = new Random();
  /**
  * The default constructor.
  */
  public Food() {
    super();
    setHeal(random.nextInt(26) * 2);
  }

  /**
  * Returns the message to print to the user when the item is consumed.
  * @return (String) the message that indicates the item has been consumed
  */
  @Override
  public String consume() {
    return ("You eat the " + this.getName());
  }

  /**
  * Returns the amount the item heals (or hurts) the player.
  * @return (int) the heal integer
  */
  public int getHeal() {
    return heal;
  }

  /**
  * Sets the amount that the player's HP changes when the item is consumed.
  * @param newHeal the amount the item should heal for.
  */
  public void setHeal(int newHeal) {
    heal = newHeal;
  }
}
