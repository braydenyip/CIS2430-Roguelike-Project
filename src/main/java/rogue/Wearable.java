package rogue;

/**
* Provides the ability for an item to be worn and taken off by a player.
*/
public interface Wearable {
  /**
  * Generates a message to indicate that the item is being worn.
  * @return (String) a message to show the user when the item is worn.
  */
  String wear();
  /**
  * Generates a message to indicate that the item has been taken off.
  * @return (String) a message to show the user when the item is stripped.
  */
  String strip();
}
