package rogue;

import java.util.HashMap;

/**
* Provides inventory functionality with two ways to access the inventory
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
  * @param inventory the size of the player inventory.
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
  * Sets the amount of items that can be held in the inventory.
  *
  */
  public void setCapacity(int newCapacity) {
    capacity = newCapacity;
  }

  /**
  * Adds an item to the inventory if possible.
  * @param toAdd the Item to add
  * @return (boolean) True if the item was successfully added, otherwise False
  */
  public boolean add(Item toAdd) {
    if (inventory.size() >= capacity) {
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
  * Dumps the entire inventory.
  */
  public void dumpAll() {
    inventory.clear();
  }



}
