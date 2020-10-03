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
//TO-DO: error handling for this function
 public int getDoor(String direction){
   if (doors.containsKey(direction)){
     return (doors.get(direction)).intValue(); //get the door in (direction), which returns a value, which is converted to a primitive int.
   }
   else{
     return -1;
   }
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
  if (thePlayer.getCurrentRoom().getId() == id){
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
  int x = 0;
  int y = 0;
  String roomString = new String();
  roomString = "Room #" + id + ":\n";
  // store the relative coordinate for each door
  int nDoorLoc = this.getDoor("N");
  int sDoorLoc = this.getDoor("S");
  int wDoorLoc = this.getDoor("W");
  int eDoorLoc = this.getDoor("E");

  for (x=0;x<width;x++){ //Make the string for the N wall , the first EW wall
    if (this.playerOnTile(id,x,y)){
      roomString += "@";
    }
    else if (nDoorLoc == x){
      roomString+="+";
    }
    else{
      roomString+="-"; //append an EW door char
    }
  }
  roomString+="\n";
  for(y=1;y<height-1;y++){ // for each row in the room that is not the N/S walls
    for(x=0;x<width;x++){ // print a string of length width, we need to process each coordinate.
      if (x == 0){ // West wall or door
        if (this.playerOnTile(id,x,y)){
          roomString += "@";
        }
        else if (wDoorLoc == y){
          roomString += "+";
        }
        else{
          roomString += "|";
        }
      }
      else if (x == width-1){ // East wall or door
        if (this.playerOnTile(id,x,y)){
          roomString += "@";
        }
        else if (eDoorLoc == y){
          roomString += "+";
        }
        else{
          roomString += "|";
        }
      }
      else{ // If nothing else is there show the floor or the player or an item
        if (this.playerOnTile(id,x,y)){
          roomString += "@";
        }
        else{
          roomString += ".";
        }
      }
    }
    roomString += "\n";
  }

  y=height-1;
  for (x=0;x<width;x++){ //Make the string for the N wall , the first EW wall
    if (this.playerOnTile(id,x,y)){
      roomString += "@";
    }
    else if (sDoorLoc == x){
      roomString+="+";
    }
    else{
      roomString+="-"; //append an EW door char
    }
  }
  roomString+="\n";
  return roomString;
}

private boolean playerOnTile(int id, int x, int y){
  if (this.isPlayerInRoom() == true){ // Of course the player must be in the room
    Point pt = thePlayer.getXyLocation();
    int px = (int)pt.getX();
    int py = (int)pt.getY();
    if (x == px && y == py){ // if the x and y coordinates given and the player's current coords match return true.
      return true;
    }
  }
  return false;
}

}
