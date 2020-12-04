package rogue;

import java.util.Random;

public class Food extends Item implements Consumable {

  private static final long serialVersionUID = 9018984331599714045L;
  private int heal; // how much this item heals you for
  private Random random = new Random();
  /**
  * The default constructor. Sets healing level randomly.
  */
  public Food() {
    super("Food");
    setHeal(random.nextInt(26) * 2);
  }

  /**
  * Returns the message to print to the user when the item is consumed.
  * @return (String) the message that indicates the item has been consumed
  */
  @Override
  public String consume() {
    return ((this.getDescription().split(":"))[0]);
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
