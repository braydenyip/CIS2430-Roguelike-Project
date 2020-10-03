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
  private ArrayList<String> defaultSymbols;
  private String doorSymbol;
  private String floorSymbol;
  private String passageSymbol;
  private String wallSymbolNS;
  private String wallSymbolEW;
  private String playerSymbol;
  private Player thePlayer;
  private int width;
  private int height;
  private int id;
  private ArrayList<Item> roomItems = new ArrayList<Item>(); // Stores all the items.
  private Map <String,Integer> doors = new HashMap<String,Integer>(); // This map stores the locations of each door.
    // Default constructor
 public Room() {
   this.setHeight(8);
   this.setWidth(8);
   this.setId(-1);
   this.setDoor("N",1);
 }

   // Required getter and setters below


 public int getWidth() {
   return width;
 }


 public void setWidth(int newWidth) {
   width = newWidth;
 }


 public int getHeight() {
   return height;
 }


 public void setHeight(int newHeight) {
   height = newHeight;
 }

 public int getId() {
   return id;
 }


 public void setId(int newId) {
   id = newId;
 }


 public ArrayList<Item> getRoomItems() {
   return roomItems;
 }


 public void setRoomItems(ArrayList<Item> newRoomItems) {
   roomItems = newRoomItems;
 }


 public Player getPlayer() {
   return thePlayer;
 }


 public void setPlayer(Player newPlayer) {
   thePlayer = newPlayer;
 }

 public int getDoor(String direction){
   return (doors.get(direction)).intValue(); //get the door in (direction), which returns a value, which is converted to a primitive int.
 }

/*
direction is one of NSEW
location is a number between 0 and the length of the wall
*/

public void setDoor(String direction, int location){
  Integer loc = new Integer(location);
  doors.put(direction,loc);
}

public void setDisplaySymbols(ArrayList<String> symbolsList){
  defaultSymbols = symbolsList;
}

public boolean isPlayerInRoom() {
  if (thePlayer != null){
    return true;
  }
  else{
    return false;
  }
}

/**
* Produces a string that can be printed to produce an ascii rendering of the room and all of its contents
* @return (String) String representation of how the room looks
* WILL NOT provide extra newlines -- handled by Rogue's class.
*/

public String displayRoom() {
  int i = 0;
  int j = 0;
  String roomString = new String();
  roomString = "Room #" + id + ":\n";
  for(i=0;i<height;i++){ // for each row in the room
    for(j=0;j<width;j++){ // print a string of length width, we need to process each coordinate.
      roomString += ".";
    }
    roomString += "\n";
  }
  return roomString;
}

}
