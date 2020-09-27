package rogue;
import java.util.ArrayList; 
import java.util.Map;
import java.awt.Point;


/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room  {

  

    // Default constructor
 public Room() {

 }

 
 public Room(int width, int height, int id, ArrayList<Item> roomItems, HashMap<String, Character> roomSymbols, Player thePlayer, HashMap<String, Integer> doors) {
  
 }

   // Required getter and setters below

 
 public int getWidth() {

 }

 
 public void setWidth(int newWidth) {

 }

 
 public int getHeight() {

 }


 public void setHeight(int newHeight) {
 }

 public int getId() {

 }


 public void setId(int newId) {

 }


 public ArrayList<Item> getRoomItems() {

 }


 public void setRoomItems(ArrayList<Item> newRoomItems) {

 }


 public Player getPlayer() {

 }


 public void setPlayer(Player newPlayer) {

 }


 



 public int getDoor(String direction){

 }

/*
direction is one of NSEW
location is a number between 0 and the length of the wall
*/

public void setDoor(String direction, int location){

}


public boolean isPlayerInRoom() {

}

return true;
}

   /**
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents
    * @return (String) String representation of how the room looks
    */
   public String displayRoom() {

     
     
   }
