package rogue;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
/**
 * The player's character.
 */
public class Player {

    private String name;
    private Point xyLocation;
    private Room currentRoom;
    private Inventory inventory;
    private Inventory wearables;

    private int hp;
    private static final int MAXHP = 200;
    private int ap;

    /**
    * The default player constructor.
    */
    public Player() {
      this("Bilbo Baggins");
    }

    /**
    * Constructor which allows for the specification of the name of the player.
    * @param newName the name of the player
    */
    public Player(String newName) {
      inventory = new Inventory();
      wearables = new Inventory(12);
      name = newName;
      hp = 100;
      ap = 0;
    }

    /**
    * Returns the name of the player.
    * @return name of the player
    */
    public String getName() {
      return name;
    }

    /**
    * Changes the name of the player.
    * @param newName the name of the player
    */
    public void setName(String newName) {
      name = newName;
    }

    /**
     * Gets all the items being worn by the player.
     * @return (Inventory) an Inventory object containing the items worn by the player
     */
    public Inventory getWearables() {
        return wearables;
    }

    /**
     * Sets the inventory of worn items on the player.
     * @param wearables the wearable items to put on the player.
     */
    public void setWearables(Inventory wearables) {
        this.wearables = wearables;
    }

    /**
     * Determines if the wearables are full.
     * @return True if the player has the max num of wearables
     */
    public boolean hasFullWearables() {
        return wearables.isFull();
    }

    /**
     * Puts the wearable item on the player.
     * @param toWear the Wearable item to add
     * @return (String) A message indicating the item was worn.
     */
    public String wearItem(Wearable toWear) {
        wearables.add((Item) toWear);
        inventory.remove(((Item) toWear).getId());
        if (toWear instanceof Clothing) {
            addAp(((Clothing) toWear).getArmourPoints());
        }
        return toWear.wear();
    }

    /**
     * Takes the item with the given id off the player.
     * @param toStrip the id of the item to strip.
     * @return a String indicating the item was stripped
     */
    public String stripItem(int toStrip) {
        Item stripped = wearables.remove(toStrip);
        if (stripped == null) {
            return "not removed";
        } else {
            inventory.add(stripped);
            if (stripped instanceof Clothing) {
                addAp(((Clothing) stripped).getArmourPoints() * -1);
            }
        }
        return (((Wearable) stripped).strip());
    }

    /**
     * Gets the armour level of the player.
     * @return (int) the armour level
     */
    public int getAp() {
        return ap;
    }

    /**
     * Sets the armour level of the player.
     * @param ap the armour level
     */
    public void setAp(int ap) {
        this.ap = ap;
    }

    /**
     * Adds or subtracts a number to the player's armour.
     * @param deltaAp the amount to change the player's armour.
     */
    public void addAp(int deltaAp) {
        setAp(this.ap + deltaAp);
    }


    /**
    * Returns a Point with the player's location.
    * @return (Point) A Point object containing the x and y coordinates of the player
    */
    public Point getXyLocation() {
      return xyLocation;
    }

    /**
    * Changes the location of the player by taking a new Point.
    * @param newXyLocation A Point object containing the player's x and y coordinates
    */
    public void setXyLocation(Point newXyLocation) {
      xyLocation = newXyLocation;
    }

    /**
    * Returns only the x coordinate of the player.
    * @return (int) the player's x coordinate (horizontal) as an int
    */
    public int getXCoordinate() {
      return ((int) xyLocation.getX());
    }

    /**
    * Returns only the y coordinate of the player.
    * @return (int) the player's y coordinate (vertical) as an int
    */
    public int getYCoordinate() {
      return ((int) xyLocation.getY());
    }

    /**
    * Moves the player by a specific amount. This function assumes a legal move.
    * @param dx the amount to move the player by in x (horizontally)
    * @param dy the amount to move the player by in y (vertically)
    */
    public void movePlayerBy(int dx, int dy) {
      xyLocation.translate(dx, dy);
    }

    /**
    * Get the player's health points.
    * @return (int) the players current hp
    */
    public int getHp() {
      return hp;
    }

    /**
    * Sets the player's health points.
    * @param newHp the player's new hp value
    */
    public void setHp(int newHp) {
      hp = newHp;
    }

    /**
    * Adds or subtracts a number to the player health points.
    * @param deltaHp the change in player health, where a negative value reflects damage
    */
    public void addHp(int deltaHp) {
      int newHp = hp + deltaHp;
      if (newHp <= 0) {
        hp = 0;
      } else if (newHp >= MAXHP) {
        hp = MAXHP;
      } else {
        hp = newHp;
      }
    }

    /**
     * Consumes an item from the inventory.
     * @param toConsume the item to consume
     * @return a message indicating the item has been consumed.
     */
    public String consumeItem(Consumable toConsume) {
        if (toConsume instanceof Food) {
            addHp(((Food) toConsume).getHeal());
        }
        inventory.remove(((Item) toConsume).getId());
        return (toConsume.consume());
    }

    /**
     * Tosses an item from the player inventory.
     * @param toToss the item to toss
     * @return (string) a string that indicates the item has been tossed
     */
    public String tossItem(Tossable toToss) {
        Item removed = inventory.remove(((Item) toToss).getId());
        if (!(currentRoom.replaceItem(removed, getXCoordinate(), getYCoordinate()))) {
            inventory.add(removed);
            return "There was no space to toss the item.";
        }
        return toToss.toss();
    }

    /**
    * Detects if the player has died.
    * @return (boolean) True if the player has 0 HP, otherwise return False.
    */
    public boolean playerIsDead() {
      if (hp == 0) {
        return true;
      }
      return false;
    }

    /**
    * Returns the Room which the player is in.
    * @return (Room) The room the player is currently in.
    */
    public Room getCurrentRoom() {
      return currentRoom;
    }

    /**
    * Changes the room which the player is in.
    * @param newRoom The new Room object of the player. Don't forget to change the room state.
    */
    public void setCurrentRoom(Room newRoom) {
      currentRoom = newRoom;
    }

    /**
    * Returns the entire inventory.
    * @return (Inventory) the player's whole inventory.
    */
    public Inventory getInventory() {
      return inventory;
    }

    /**
    * Sets a brand new inventory.
    * @param newInventory the inventory to associate with the player.
    */
    public void setInventory(Inventory newInventory) {
      inventory = newInventory;
    }

    /**
     * Returns the mapping of the inventory of the player.
     * @return (HashMap<Integer, Item>) the inventory as a Map
     */
    public Map<Integer, Item> getInventoryMap() {
        return inventory.getInventory();
    }

    /**
    * Adds the item to the inventory of the player.
    * @param toAdd the Item to add to the player's inventory.
    */
    public void collectItem(Item toAdd) {
      if (inventory.add(toAdd)) {
        currentRoom.removeItem(toAdd); // remove from the room
      }
    }

    /**
    * Returns an item from the inventory of the player.
    * @param id the id of the item to get
    * @return (Item) the Item with that id
    */
    public Item getFromInventory(int id) {
      return (inventory.get(id));
    }

    /**
    * Returns an item from the inventory of the player, removing it in the process.
    * @param id the id of the item to remove
    * @return (Item) the Item with that id
    */
    public Item removeFromInventory(int id) {
      return (inventory.remove(id));
    }

}
