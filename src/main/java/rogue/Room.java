package rogue;
import java.util.ArrayList;
import java.util.Map;
import java.awt.Point;
import java.util.HashMap;

/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room  {
  // TO-DO: Replace with an AL
  private Map<String, String> defaultSymbols = new HashMap<String, String>();
  private Player thePlayer;
  private int width;
  private int height;
  private int id;
  private ArrayList<Item> roomItems = new ArrayList<Item>(); // Stores all the items.
  private HashMap<String, Integer> doors = new HashMap<String, Integer>(); // Stores the locations of each door
    // Default constructor
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
* @param int The new width of the room.
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
 * @param int The new height of the room.
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
 * @param int The new ID number of the room.
 */
 public void setId(int newId) {
   id = newId;
 }

 /**
 * Returns all the items in the room.
 * @return (ArrayList<Item>) An ArrayList of all the items in the room, or an empty array if there are no items
 */

 public ArrayList<Item> getRoomItems() {
   return roomItems;
 }

 /**
 * Sets the items for a room
 * @param ArrayList<Item> Items to be added to the room
 */

 public void setRoomItems(ArrayList<Item> newRoomItems) {
   roomItems = newRoomItems;
 }

 /**
 * Returns the room's Player object
 * @return (Player) The player associated with the room
 */

 public Player getPlayer() {
   return thePlayer;
 }

 /**
 * Sets the Player object for the room. This player should be in the room.
 * @param Player The player in the room
 */

 public void setPlayer(Player newPlayer) {
   thePlayer = newPlayer;
 }

/**
* Get the position of a door given a direction.
* There shall not be more than 1 door per direction.
* @param String The direction of the door
* @return (int) The position of the door from the upper-left hand corner, or -1 if it is not found
*/
 public int getDoor(String direction) {
   if (doors.containsKey(direction)) {
     return (doors.get(direction)).intValue(); // get the door in given dir
   }
   return -1;
 }

/**
* Direction is one of "N", "S", "E", or "W".
* Location is a number between 0 and the length of the wall
* @param String The direction of the door
* @param int The distance of the door, measured from the upper-left corner
*/

public void setDoor(String direction, int location) {
  Integer loc = new Integer(location);
  doors.put(direction, loc);
}

/**
* Sets the symbols that are displayed for each room element (e.g. walls, doors).
* @param HashMap<String,String> A map associating each element of a room to an ASCII character.
*/
public void setSymbols(HashMap<String, String> symbols) {
  defaultSymbols = symbols;
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


private boolean itemOnTile(int x, int y) { // Checks for an item on the specified tile
  Point tile = new Point(x, y);
  for (Item item : roomItems) {
    if (tile.equals(item.getXyLocation())) {
      return true;
    }
  }
  return false;
}


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

public String displayRoom() { // Shrink to < 50 lines
  int x = 0;
  int y = 0;
  String roomString = new String();
  roomString = "Room #" + id + ":\n";
  // store the relative coordinate for each door
  int nDoorLoc = this.getDoor("N");
  int sDoorLoc = this.getDoor("S");
  int wDoorLoc = this.getDoor("W");
  int eDoorLoc = this.getDoor("E");

  for (x = 0; x < width; x++) { // Make the string for the N wall , the first EW wall
    if (this.playerOnTile(x, y)) {
      roomString += defaultSymbols.get("PLAYER");
    } else if (nDoorLoc == x) {
      roomString += defaultSymbols.get("DOOR");
    } else {
      roomString += defaultSymbols.get("NS_WALL"); // append an NS door char
    }
  }

  roomString += "\n";

  for (y = 1; y < (height - 1); y++) { // for each row in the room that is not the N/S walls
    for (x = 0; x < width; x++) { // print a string of length width, we need to process each coordinate.
      if (x == 0) { // West wall or door
        if (this.playerOnTile(x, y)) {
          roomString += defaultSymbols.get("PLAYER"); // player in door
        } else if (wDoorLoc == y) {
          roomString += defaultSymbols.get("DOOR");
        } else {
          roomString += defaultSymbols.get("EW_WALL");
        }
      } else if (x == (width - 1)) { // East wall or door
        if (this.playerOnTile(x, y)) {
          roomString += defaultSymbols.get("PLAYER");
        } else if (eDoorLoc == y) {
          roomString += defaultSymbols.get("DOOR");
        } else {
          roomString += defaultSymbols.get("EW_WALL");
        }
      } else { // If nothing else is there show the floor or the player or an item
        if (this.playerOnTile(x, y)) {
          roomString += defaultSymbols.get("PLAYER");
        } else if (this.itemOnTile(x, y)) {
          roomString += defaultSymbols.get("ITEM");
        } else {
          roomString += defaultSymbols.get("FLOOR");
        }
      }
    }
    roomString += "\n";
  }

  y = height - 1;

  for (x = 0; x < width; x++) { // Make the string for the S wall
    if (this.playerOnTile(x, y)) {
      roomString += defaultSymbols.get("PLAYER");
    } else if (sDoorLoc == x) {
      roomString += defaultSymbols.get("DOOR");
    } else {
      roomString += defaultSymbols.get("NS_WALL"); // append an NS door char
    }
  }

  roomString += "\n";
  return roomString;
}


}
