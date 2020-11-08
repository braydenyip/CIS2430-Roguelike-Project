package rogue;

import java.util.ArrayList;

public class Door {

  private ArrayList<Room> connectedRooms;
  private int position;
  private String direction;
  /**
  * The default door constructor.
  */
  public Door() {
    connectedRooms = new ArrayList<Room>();
  }

  /**
  * Returns the pair of connected rooms.
  * @return (ArrayList<Room>) the pair of rooms that are connected
  */
  public ArrayList<Room> getConnectedRooms() {
    return connectedRooms;
  }

  /**
  * Sets the rooms connected to a Door.
  * @param allConnectedRooms the rooms connected to a door
  */
  public void setConnectedRooms(ArrayList<Room> allConnectedRooms) {
    connectedRooms = allConnectedRooms;
  }

  /**
  * Returns the relative position of the door on the wall.
  * @return (int) the position of the door
  */
  public int getPosition() {
    return position;
  }

  /**
  * Sets the relative position of the door on the wall.
  * @param newPosition the position of the door
  */
  public void setPosition(int newPosition) {
    position = newPosition;
  }


  /**
  * Sets the direction of the door.
  * @param newDirection the direction of the door in the room.
  */
  public void setDirection(String newDirection) {
    direction = newDirection;
  }

  /**
  * Returns the direction of the door.
  * @return (String) the direction of the door (N,S,E,W)
  */
  public String getDirection() {
    return direction;
  }

  /**
  * Connects a door to a room, so long as there isn't already a pair of rooms.
  * @param r The room to be connected
  */
  public void connectRoom(Room r) {
    if (connectedRooms.size() < 2) {
      connectedRooms.add(r); // Do not care if there is already a pair
    }
  }

  /**
  * Given a room, this function returns the other room connected by the Door.
  * @param currentRoom one of the rooms in the pair
  * @return (Room) the other room in the pair
  */
  public Room getOtherRoom(Room currentRoom) {
    if (connectedRooms.get(0) == currentRoom) {
      return connectedRooms.get(1);
    } else {
      return connectedRooms.get(0);
    }
  }

}
