package rogue;

public class MagicalItem extends Item {

  public String effect;

  /**
  * The default constructor.
  */
  public MagicalItem() {
    super();
    setEffect("Fire");
  }

  /**
  * Returns the magical item's status effect.
  * @return (String) the item's effect
  */
  public String getEffect() {
    return effect;
  }

  /**
  * Sets the item's effect.
  * @param newEffect the effect that the item should have.
  */
  public String setEffect(String newEffect) {
    effect = newEffect;
  }

}
