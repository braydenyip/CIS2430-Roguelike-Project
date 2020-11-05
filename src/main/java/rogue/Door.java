package rogue;

import java.util.ArrayList;

public class Door{
  /**
  * The default door constructor
  */
  ArrayList<Room> connectedRooms;
  String em = "Attempted to add a door connection, but the door already has a door pair.";
  public Door(){
    connectedRooms = new ArrayList<Room>();
  }
  /**
  * Connects a door to a room, so long as there isn't already a pair of rooms
  * @param r The room to be connected
  */
  public void connectRoom(Room r) {
    if (connectedRooms.size() < 2){
      connectedRooms.add(r); // Do not care if there is already a pair
    }
  }
  /**
  * Returns the pair of connected rooms
  * @return (ArrayList<Room>) the pair of rooms that are connected
  */
  public ArrayList<Room> getConnectedRooms(){
    return connectedRooms;
  }
  /**
  * Given a room, this function returns the other room connected by the Door
  * @return (Room) the other room in the pair
  */
  public Room getOtherRoom(Room currentRoom){
    if(connectedRooms.get(0) == currentRoom){
      return connectedRooms.get(1);
    }
    else{
      return connectedRooms.get(0);
    }
  }

}
