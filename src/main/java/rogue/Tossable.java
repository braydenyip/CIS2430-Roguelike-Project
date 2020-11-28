package rogue;

/**
* An interface that provides the ability for an item to be thrown.
*/
public interface Tossable {
  /**
  * Generates a message to show the user when the player throws the item.
  * @return (String) a message to display when the item is tossed.
  */
  String toss();
}
