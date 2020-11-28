package rogue;

import java.util.Random;

public class Potion extends MagicalItem implements Consumable, Tossable {

  private Random random = new Random();
  private int potency;

  /**
  * The default constructor.
  */
  public Potion() {
    super("Potion");
    setPotency(random.nextInt(101));
  }

  /**
  * Returns a strength value of the potion.
  * @return (int) the potency of the potion.
  */
  public int getPotency() {
    return potency;
  }

  /**
  * Sets the potency value of the potion.
  * @param newPotency the potency value for the potion
  */
  public void setPotency(int newPotency) {
    potency = newPotency;
  }

  /**
  * Generates a message for when you drinl the potion.
  * @return (String) the message to print when the potion is consumed.
  */
  @Override
  public String consume() {
    return ("You drink the " + getName());
  }

  /**
  * Generates a message for when you throw the potion.
  * @return (String) the message to print when the potion is thrown
  */
  @Override
  public String toss() {
    return ("You throw the" + getName() + " and it breaks.");
  }
}
