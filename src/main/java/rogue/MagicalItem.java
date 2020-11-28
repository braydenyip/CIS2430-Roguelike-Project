package rogue;

public class MagicalItem extends Item {

  private String effect;

  /**
  * The default constructor.
  */
  public MagicalItem() {
    super("MagicalItem");
    setEffect("Fire");
  }

  /**
  * The one parameter constructor.
  * @param subtype the subtype of magical item
  */
  public MagicalItem(String subtype) {
    super(subtype);
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
  public void setEffect(String newEffect) {
    effect = newEffect;
  }

}
