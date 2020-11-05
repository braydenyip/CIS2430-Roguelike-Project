package rogue;

import java.util.ArrayList;

public class Door{
  /**
  * The default door constructor
  */
  ArrayList<Room> connectedRooms;
  int position;
  String direction;
  String em = "Attempted to add a door connection, but the door already has a door pair.";

  public Door(){
    connectedRooms = new ArrayList<Room>();
  }

  /**
  * Returns the pair of connected rooms
  * @return (ArrayList<Room>) the pair of rooms that are connected
  */
  public ArrayList<Room> getConnectedRooms(){
    return connectedRooms;
  }

  /**
  * Sets the relative position of the door on the wall
  * @param newId the position of the door
  */
  public void setPosition(int newPosition){
    position = newPosition;
  }

  /**
  * Returns the relative position of the door on the wall
  * @return (int) the position of the door
  */
  public int getPosition(){
    return position;
  }

  /**
  * Sets the direction of the door
  * @param newDirection the direction of the door in the room.
  */
  public void setDirection(String newDirection){
    direction = newDirection;
  }

  /**
  * Returns the direction of the door
  * @return (String) the direction of the door (N,S,E,W)
  */
  public String getDirection(){
    return direction;
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
