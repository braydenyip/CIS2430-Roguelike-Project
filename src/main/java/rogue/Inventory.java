package rogue;

import java.util.HashMap;
import java.util.Map;
/**
* Provides inventory functionality with two ways to access the inventory.
*/
public class Inventory {

  private Map<Integer, Item> inventory;
  private int capacity;
  /**
  * The default constructor.
  * Sets the capacity to 24 by default.
  */
  public Inventory() {
    inventory = new HashMap<>();
    capacity = 24;
  }

  /**
  * A constructor that sets the inventory size of the player.
  * @param inventorySize the size of the player inventory.
  */
  public Inventory(int inventorySize) {
    this();
    capacity = inventorySize;
  }

  /**
  * Returns the entire inventory as a map.
  * @return (Map<Integer, Item>) the inventory mapping by id.
  */
  public Map<Integer, Item> getInventory() {
    return inventory;
  }

  /**
  * Sets the entire inventory to be equal to a valid map.
  * @param newInventory a mapping of id's to items representing an inventory.
  */
  public void setInventory(Map<Integer, Item> newInventory) {
    inventory = newInventory;
  }

  /**
  * Gets the maximum number of items that this Inventory can hold.
  * @return (int) the capacity of the inventory.
  */
  public int getCapacity() {
    return capacity;
  }

  /**
  * Sets the amount of items that can be held in the inventory, if possible.
  * @param newCapacity the new maximum of items that can be stored in the inventory.
  * @return (boolean) False only if the number of items in the inventory exceeds the newCapacity
  */
  public boolean setCapacity(int newCapacity) {
    if (getNumberOfItems() > newCapacity) {
      return false;
    }
    capacity = newCapacity;
    return true;
  }

  /**
  * Returns the number of items currently in the inventory.
  * @return (int) the number of items that are in the inventory right now.
  */
  public int getNumberOfItems() {
    return inventory.size();
  }


  /**
  * Adds an item to the inventory if possible.
  * @param toAdd the Item to add
  * @return (boolean) True if the item was successfully added, otherwise False
  */
  public boolean add(Item toAdd) {
    if (getNumberOfItems() >= capacity) {
      return false;
    }
    inventory.put(toAdd.getId(), toAdd);
    return true;
  }

  /**
  * Removes an item from the inventory by id.
  * @param id the unique Item ID to remove from inventory.
  * @return (Item) the Item that was removed, or NULL if it was not found
  */
  public Item remove(int id) {
    return (inventory.remove(id));
  }

  /**
  * Removes the first instance of an item from the inventory by it's name.
  * @param name the non-unique name of the item to remove
  * @return (Item) the first instance found of the named item, or NULL if no such item exists
  */
  public Item remove(String name) {
    for (Item item : inventory.values()) {
      if (item.getName().equals(name)) {
        return item;
      }
    }
    return null;
  }

  /**
  * Returns an Item from inventory without removing it.
  * @param id the unique id of the item to find.
  * @return (Item) the Item that was found, or NULL if not found.
  */
  public Item get(int id) {
    return (inventory.get(id));
  }

  /**
  * Trashes (remove without return) an item from inventory.
  * @param id the unique item id.
  */
  public void trash(int id) {
    Item temp = remove(id);
  }

  /**
  * Deletes the entire inventory.
  * The items will not be dumped to the floor but deleted entirely!
  */
  public void trashAll() {
    inventory.clear();
  }

}
