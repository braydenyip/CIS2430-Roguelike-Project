package rogue;

/**
* An interface that provides the ability to consume an item.
*/
public interface Consumable {
  /**
  * Generates a message to show the user when the item is consumed.
  * @return (String) the message to display when the player consumes the item.
  */
  String consume();
}
