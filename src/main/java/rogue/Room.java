package rogue;
import java.util.ArrayList;
import java.util.Map;
import java.awt.Point;
import java.util.HashMap;
import java.util.Random;
/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room {
  private static Map<String, String> defaultSymbols = new HashMap<String, String>();
  private Player thePlayer = null;
  private int width;
  private int height;
  private int id;
  private static int maxWidth = 0;
  private static int maxHeight = 0;
  private ArrayList<Item> roomItems = new ArrayList<Item>();
  private ArrayList<Door> doors = new ArrayList<Door>(); // Stores Door objects
  private Rogue rogue;
  private Random random = new Random();
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
   if (newWidth > maxWidth) {
     maxWidth = newWidth;
   }
 }

 /**
 * Returns the width of the widest room.
 * @return (int) Width of the widest room.
 */
  public int getMaxWidth() {
    return maxWidth;
  }

  /**
  * Manually sets the maximum width to render a room to.
  * @param newMax the new maximum render width.
  */
  public void setMaxWidth(int newMax) {
    maxWidth = newMax;
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
   if (newHeight > maxHeight) {
     maxHeight = newHeight;
   }
 }

 /**
 * Returns the height of the highest room.
 * @return (int) Height of the room.
 */
  public int getMaxHeight() {
    return maxHeight;
  }

  /**
  * Manually sets the maximum height to render a room to.
  * @param newMax the new maximum render height.
  */
  public void setMaxHeight(int newMax) {
    maxHeight = newMax;
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
 * Forcibly sets the items for a room.
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
    int x = toAdd.getXCoordinate();
    int y = toAdd.getYCoordinate();
    if (!(rogue.itemExists(toAdd))) {
      throw new NoSuchItemException("No such item exists for this room");
    } else if (positionIsInvalid(x, y)) { // wall exception
      throw new ImpossiblePositionException("Item is in or beyond a wall or door.");
    } else if (itemOnTile(x, y) != null || playerOnTile(x, y)) {
      throw new ImpossiblePositionException("Something else is on the tile");
    } else {
      roomItems.add(toAdd);
    }
  }

  /**
  * Removes an item from the room.
  * @param toRemove the Item to remove
  */
  public void removeItem(Item toRemove) {
    roomItems.remove(toRemove);
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
 * Sets the Player object for the room manually.
 * @param newPlayer the Player object to put in the room
 */
 public void setPlayer(Player newPlayer) {
   thePlayer = newPlayer;
 }

  /**
  * Get the position of a door given a direction.
  * There shall not be more than 1 door per direction.
  * @param direction The direction of the door
  * @return (int) The position of the door from the upper-left hand corner, or -1 if it is not found
  */
   public int getDoorPosition(String direction) {
     Door door = getDoor(direction);
     if (door != null) {
       return (door.getPosition());
     }
     return -1;
   }

   /**
   * Get the door object given a direction.
   * There shall not be more than 1 door per direction.
   * @param direction The direction of the door
   * @return (Door) The door object, or null if not found.
   */
    public Door getDoor(String direction) {
      for (Door door : doors) {
        if (door.getDirection().equals(direction)) {
          return door;
        }
      }
      return null;
    }

    /**
    * Returns the whole list of doors...
    * @return (ArrayList<Door>) the doors of the room.
    */
    public ArrayList<Door> getAllDoors() {
      return doors;
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
  * Sets the symbols for all rooms based on the rogue's map.
  */
  public void setSymbols() {
    defaultSymbols = rogue.getSymbols();
  }

  /**
  * Sets the symbol map for all rooms manually.
  * @param newSymbols the map of symbols to set for Rooms.
  */
  public void setSymbols(HashMap<String, String> newSymbols) {
    defaultSymbols = newSymbols;
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
    if (thePlayer == null) {
      return false;
    } else if (thePlayer.getCurrentRoom().getId() == id) {
      return true;
    }
    return false;
  }

  // "Eligibility Detector" classes

  /**
  * Determines if there is an item on the tile.
  * @param x the x coordinate to check
  * @param y the y coordinate to check
  * @return (Item) If an item is found, return the item. Otherwise, return null
  */
  public Item itemOnTile(int x, int y) { // Checks for an item on the specified tile
    Point tile = new Point(x, y);
    for (Item item : roomItems) {
      if (tile.equals(item.getXyLocation())) {
        return item;
      }
    }
    return null;
  }

  /**
  * Determines if the player is on a tile.
  * @param x the x coordinate
  * @param y the y coordinate
  * @return (boolean) Returns true if the player is on the tile, otherwise false
  */
  public boolean playerOnTile(int x, int y) {
    if (this.isPlayerInRoom()) { // Of course the player must be in the room
      int px = thePlayer.getXCoordinate();
      int py = thePlayer.getYCoordinate();
      if (x == px && y == py) { // if the x and y coordinates given and the player's current coords match return true
        return true;
      }
    }
    return false;
  }

  /**
  * Determines if a position is out of bounds.
  * @param x the x coordinate
  * @param y the y coordinate
  * @return (boolean) True if the location is in bounds, otherwise return False
  */
  public boolean positionIsInvalid(int x, int y) {
    if (x < 1 || x >= (width - 1) || y < 1 || y >= (height - 1)) {
      return true;
    }
    return false;
  }

  /**
  * Edits a String to display the room.
  * @param roomString the String to change
  * @return (String) the updated string
  */

  public String updateRoomString(String roomString) {
    roomString = "";
    int wDoorLoc = this.getDoorPosition("W");
    int eDoorLoc = this.getDoorPosition("E");
    roomString = addNSWallLine(this.getDoorPosition("N"), roomString);
    for (int y = 1; y <= (maxHeight - 1); y++) {
      if (y < (height - 1)) { // for each row in the room that is not the N/S walls
        roomString += " ";
        roomString = addRoomLine(wDoorLoc, eDoorLoc, y, roomString);
      } else if (y == (height - 1)) {
        roomString += " ";
        roomString = addNSWallLine(this.getDoorPosition("S"), roomString);
      } else {
        roomString = addJustSpaces(roomString);
      }
    }
    return roomString;
  }

  // add a padding line of spaces
  private String addJustSpaces(String roomString) {
    for (int x = 0; x <= maxWidth; x++) {
      roomString += " ";
    }
    roomString += "\n";
    return roomString;
  }

  // Creates a line that is an NS wall
  private String addNSWallLine(int doorLoc, String roomString) { // Add a north-south wall
    for (int x = 0; x < maxWidth; x++) {
      if (x < width) {
        roomString += getDoorOrWall(doorLoc, x, "NS");
      } else {
        roomString += " ";
      }
    }
    roomString += "\n";
    return roomString;
  }

  // Creates a line in between the NS walls.
  private String addRoomLine(int wDoorLoc, int eDoorLoc, int y, String roomString) {
    Item tileItem;
    roomString += getDoorOrWall(wDoorLoc, y, "EW");
    for (int x = 1; x < maxWidth; x++) {
      if (x < (width - 1)) { // rendering all internal tiles
        tileItem = itemOnTile(x, y);
        if (this.playerOnTile(x, y)) {
          roomString += defaultSymbols.get("PLAYER");
        } else if (tileItem != null) {
          roomString += tileItem.getDisplayCharacter();
        } else {
          roomString += defaultSymbols.get("FLOOR");
        }
      } else if (x == (width - 1)) {
        roomString += getDoorOrWall(eDoorLoc, y, "EW");
      } else {
        roomString += " ";
      }
    }
    return (roomString + "\n");
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

  /**
  * Verifies that the room has correct placements.
  * @return (boolean) True if the room is valid and correct, otherwise False
  * @throws NotEnoughDoorsException Thrown if the room does not have a door.
  */
  public boolean verifyRoom() throws NotEnoughDoorsException {
    try {
      if (itemsAreValid() && playerIsValid() && doorsAreValid() && id > 0) {
        return true;
      }
      return false;
    } catch (NotEnoughDoorsException e) {
      throw e;
    }
  }

  private boolean doorsAreValid() throws NotEnoughDoorsException {
    if (doors.size() == 0) {
      throw new NotEnoughDoorsException("No doors found!");
    } else {
      for (Door door: doors) {
        if (door.getConnectedRooms().size() != 2) {
          return false;
        }
      }
    }
    return true;
  }

  /**
  * This method is called if there are no doors in the room.
  * It will attempt to add a door at a random location in the room.
  * @param otherRoom the other room to connect
  * @return (boolean) True if successful, otherwise false.
  */
  public boolean addRandomDoor(Room otherRoom) {
    String openSide = getOpenSide();
    if (openSide.equals("X")) {
      return false; // no open side -- impossible
    }
    Door newDoor = new Door(openSide, (random.nextInt(height - 2) + 1));
    newDoor.connectRoom(otherRoom);
    addDoor(newDoor);
    return true;
  }

  private String getOpenSide() {
    ArrayList<String> sidesWithDoors = new ArrayList<>();
    for (Door door: doors) {
      sidesWithDoors.add(door.getDirection());
    }
    if (!(sidesWithDoors.contains("E"))) {
      return "E";
    } else if (!(sidesWithDoors.contains("W"))) {
      return "W";
    } else if (!(sidesWithDoors.contains("N"))) {
      return "N";
    } else if (!(sidesWithDoors.contains("E"))) {
      return "S";
    }
    return "X";
  }

  private boolean itemsAreValid() {
    for (Item item: roomItems) {
      if (positionIsInvalid(item.getXCoordinate(), item.getYCoordinate())) {
        return false;
      }
    }
    return true;
  }

  private boolean playerIsValid() {
    if (this.isPlayerInRoom()) {
      Item toReplace = itemOnTile(thePlayer.getXCoordinate(), thePlayer.getYCoordinate());
      if (positionIsInvalid(thePlayer.getXCoordinate(), thePlayer.getYCoordinate())) {
        return false;
      } else if (toReplace != null) {
        return toReplace.openSpotExists(this); // validity hinges on ability to move item
      }
    }
    return true;
  }

}
