package rogue;
import java.util.ArrayList;
import java.util.Map;
import java.awt.Point;
import java.util.HashMap;

/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room {
  private static Map<String, String> defaultSymbols = new HashMap<String, String>();
  private Player thePlayer;
  private int width;
  private int height;
  private int id;
  private ArrayList<Item> roomItems = new ArrayList<Item>();
  private ArrayList<Door> doors = new ArrayList<Door>(); // Stores Door objects
  private Rogue rogue;
/**
* Constructs a room with a default ID of -1.
*/
 public Room() {
   this.setId(-1);
 }

   // Required getter and setters below

/**
* Returns the width of the room, including the walls.
* @return (int) Width of the room.
*/
 public int getWidth() {
   return width;
 }

/**
* Sets the width of the room, including the walls.
* @param newWidth The new width of the room.
*/
 public void setWidth(int newWidth) {
   width = newWidth;
 }

/**
* Returns the height of the room, including the walls.
* @return (int) Height of the room.
*/
 public int getHeight() {
   return height;
 }

 /**
 * Sets the height of the room, including the walls.
 * @param newHeight The new height of the room.
 */
 public void setHeight(int newHeight) {
   height = newHeight;
 }

 /**
 * Returns the ID number of the room.
 * @return (int) The room's ID number
 */
 public int getId() {
   return id;
 }

 /**
 * Sets the ID number of the room.
 * @param newId The new ID number of the room.
 */
 public void setId(int newId) {
   id = newId;
 }

 /**
 * Gives the rogue data to the room.
 * @param newRogue the rogue class of the game
 */
 public void setRogue(Rogue newRogue) {
   rogue = newRogue;
 }

 /**
 * Returns all the items in the room.
 * @return (ArrayList<Item>) An ArrayList of all the items in the room, or an empty array if there are no items
 */
 public ArrayList<Item> getRoomItems() {
   return roomItems;
 }

 /**
 * Sets the items for a room.
 * @param allGameItems Items to be added to the room
 */

 public void setRoomItems(ArrayList<Item> allGameItems) {
   for (Item newItem : allGameItems) {
     int newItemRoomId = newItem.getCurrentRoomId();
     if (newItemRoomId > 0 && newItemRoomId == id) {
       roomItems.add(newItem);
     }
   }
 }

  /**
  * Adds an item to the room if valid.
  * @param toAdd the Item to be added to the room
  * @throws ImpossiblePositionException if the item is in an illegal position
  * @throws NoSuchItemException if the item is not in the room's list of items
  */

  public void addItem(Item toAdd) throws ImpossiblePositionException, NoSuchItemException {
    Point loc = toAdd.getXyLocation();
    int x = (int) loc.getX();
    int y = (int) loc.getY();
    if (x <= 0 || x >= (width - 1) || y <= 0 || y >= (height - 1)) { // wall exception
      throw new ImpossiblePositionException("Item is in or beyond a wall or door.");
    } else if (itemOnTile(x, y) != null || playerOnTile(x, y)) {
      throw new ImpossiblePositionException("Another object is on the tile");
    } else if (!(rogue.itemExists(id, toAdd.getId()))) {
      throw new NoSuchItemException("No such item exists for this room");
    } else {
      roomItems.add(toAdd);
    }
  }

 /**
 * Returns the room's Player object.
 * @return (Player) The player associated with the room
 */

 public Player getPlayer() {
   return thePlayer;
 }

 /**
 * Sets the Player object for the room from the Rogue class.
 */

 public void setPlayer() {
   thePlayer = rogue.getPlayer();
 }

  /**
  * Get the position of a door given a direction.
  * There shall not be more than 1 door per direction.
  * @param direction The direction of the door
  * @return (int) The position of the door from the upper-left hand corner, or -1 if it is not found
  */
   public int getDoor(String direction) {
     for (Door door : doors) {
       if (door.getDirection().equals(direction)) {
         return door.getPosition();
       }
     }
     return -1;
   }

  /**
  * Adds a door to the list of the doors.
  * @param newDoor a new door
  */
  public void addDoor(Door newDoor) {
    newDoor.connectRoom(this); // Mutually associate the rooms and doors, filling the pair
    doors.add(newDoor);
  }

  /**
  * Sets the symbols for this room based on the rogue's map.
  */
  public void setSymbols() {
    defaultSymbols = rogue.getSymbols();
  }

  /**
  * Sets the symbols and player from Rogue.
  */
  public void updateFromRogue() {
    setSymbols();
    setPlayer();
  }


  // "detector" classes
  /**
  * Checks if the player is in the current room.
  * @return (boolean) true if the player is in the room, otherwise return false.
  */
  public boolean isPlayerInRoom() {
    if (thePlayer.getCurrentRoom().getId() == id) {
      return true;
    }
    return false;
  }

  /**
  * Determines if there is an adjacent, open spot next to the coordinates.
  * Adjacent tiles will be a king's move away.
  * @param anItem the item to find a spot for
  * @return (boolean) True if there is an adjacent open spot, otherwise false
  */

  public boolean openSpotExists(Item anItem) {
    int y = (int) anItem.getXyLocation().getY();
    int x = (int) anItem.getXyLocation().getX();
    for (int yNew = (y - 1); yNew <= (y + 1); yNew++) {
      for (int xNew = (x - 1); xNew <= (x + 1); xNew++) {
        if (itemOnTile(xNew, yNew) == null && !(playerOnTile(xNew, yNew))) {
          if (xNew > 0 &&  yNew > 0 && yNew < width - 1 && xNew < height - 1) {
            anItem.setXyLocation(new Point(xNew, yNew));
            return true;
          }
        }
      }
    }
    return false;
  }

  // Determines if an item is on the tile.
  private Item itemOnTile(int x, int y) { // Checks for an item on the specified tile
    Point tile = new Point(x, y);
    for (Item item : roomItems) {
      if (tile.equals(item.getXyLocation())) {
        return item;
      }
    }
    return null;
  }

  // Determines if the player is on the tile.
  private boolean playerOnTile(int x, int y) {
    if (this.isPlayerInRoom()) { // Of course the player must be in the room
      Point pt = thePlayer.getXyLocation();
      int px = (int) pt.getX();
      int py = (int) pt.getY();
      if (x == px && y == py) { // if the x and y coordinates given and the player's current coords match return true.
        return true;
      }
    }
    return false;
  }


  /**
  * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents.
  * @return (String) String representation of how the room looks
  * WILL NOT provide extra newlines -- handled by Rogue's class.
  */

  public String displayRoom() {
    String roomString = new String("");
    int wDoorLoc = this.getDoor("W");
    int eDoorLoc = this.getDoor("E");
    roomString = addNSWallLine(this.getDoor("N"), roomString);
    for (int y = 1; y < (height - 1); y++) { // for each row in the room that is not the N/S walls
      roomString = addRoomLine(wDoorLoc, eDoorLoc, y, roomString);
    }
    roomString = addNSWallLine(this.getDoor("S"), roomString);
    return roomString;
  }

  // Creates a line that is an NS wall
  private String addNSWallLine(int doorLoc, String roomString) { // Add a north-south wall
    for (int x = 0; x < width; x++) {
      roomString += getDoorOrWall(doorLoc, x, "NS");
    }
    roomString += "\n";
    return roomString;
  }

  // Creates a line in between the NS walls.
  private String addRoomLine(int wDoorLoc, int eDoorLoc, int y, String roomString) {
    Item tileItem;
    roomString += getDoorOrWall(wDoorLoc, y, "EW");
    for (int x = 1; x < (width - 1); x++) { // rendering all internal tiles
      tileItem = itemOnTile(x, y);
      if (this.playerOnTile(x, y)) {
        roomString += defaultSymbols.get("PLAYER");
      } else if (tileItem != null) {
        roomString += tileItem.getDisplayCharacter();
      } else {
        roomString += defaultSymbols.get("FLOOR");
      }
    }
    roomString += getDoorOrWall(eDoorLoc, y, "EW");
    roomString += "\n";
    return roomString;
  }

  // Logic to determine if an edge tile is a wall or a door.
  private String getDoorOrWall(int doorLoc, int coord, String direction) {
    if (direction == "NS" && doorLoc != coord) {
      return (defaultSymbols.get("NS_WALL"));
    } else if (direction == "EW" && doorLoc != coord) {
      return (defaultSymbols.get("EW_WALL"));
    } else {
      return (defaultSymbols.get("DOOR"));
    }
  }

  private String getSymbol(String symbolName) {

      if (defaultSymbols.containsKey(symbolName)) {
          return defaultSymbols.get(symbolName);
      }
      // Does not contain the key
      return null;
  }

}
