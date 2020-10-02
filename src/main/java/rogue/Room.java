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
  private String doorSymbol;
  private String floorSymbol;
  private String passageSymbol;
  private String wallSymbolNS;
  private String wallSymbolEW;
  private String playerSymbol;
  private int width;
  private int height;
  private int id;
  private ArrayList<Item> roomItems = new ArrayList<Item>(); // Stores all the items.
  private Player thePlayer;
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
   return 0;
 }


 public void setWidth(int newWidth) {
   width = newWidth;
 }


 public int getHeight() {
   return 0;
 }


 public void setHeight(int newHeight) {
   height = newHeight;
 }

 public int getId() {
   return 0;
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

// New setter methods for symbols, other than the items
public void setDoorSymbol(String symbol){
  doorSymbol = symbol;
}

public void setFloorSymbol(String symbol){
  floorSymbol = symbol;
}

public void setPassageSymbol(String symbol){
  passageSymbol = symbol;
}

public void setWallSymbolEW(String symbol){
  wallSymbolEW = symbol;
}

public void setWallSymbolNS(String symbol){
  wallSymbolNS = symbol;
}

public void setPlayerSymbol(String symbol){
  playerSymbol = symbol;
}

// New getter methods for each symbol, other than the items. Not really necessary but whatever
public String getDoorSymbol(){
  return doorSymbol;
}

public String getFloorSymbol(){
  return floorSymbol;
}

public String getPassageSymbol(){
  return passageSymbol;
}

public String getWallSymbolEW(){
  return wallSymbolEW;
}
public String getWallSymbolNS(){
  return wallSymbolNS;
}
public String getPlayerSymbol(){
  return playerSymbol;
}


public boolean isPlayerInRoom() {
  return true;
}

/**
* Produces a string that can be printed to produce an ascii rendering of the room and all of its contents
* @return (String) String representation of how the room looks
* WILL NOT provide extra newlines -- handled by Rogue's class.
*/
public String displayRoom() {
  int i = 0;
  int j = 0;
  for(i=0;i<height;i++){ // for each row in the room
    for(j=0;j<width;j++){ // print a string of length width, we need to process each coordinate.
      System.out.print("."); // TEST PRINT ONLY
    }
    System.out.println();
  }



}

}
