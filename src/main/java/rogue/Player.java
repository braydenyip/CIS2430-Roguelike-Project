package rogue;
import java.util.ArrayList;
import java.awt.Point;
/**
 * The player character
 */
public class Player {

    private String name;
    private Point xyLocation;
    private Room currentRoom;
    // Default constructor
    public Player() {
      this("Bilbo Baggins");
    }


    public Player(String name) {
      this.name = name;
    }


    public String getName() {
      return name;
    }


    public void setName(String newName) {
      name = newName;
    }

    public Point getXyLocation() {
      return xyLocation;
    }


    public void setXyLocation(Point newXyLocation) {
      xyLocation = newXyLocation;
    }


    public Room getCurrentRoom() {
      return currentRoom;
    }


    public void setCurrentRoom(Room newRoom) {
      room = newRoom;
    }
}
