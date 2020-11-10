package rogue;
//import java.util.ArrayList;
import java.awt.Point;
/**
 * The player's character.
 */
public class Player {

    private String name;
    private Point xyLocation;
    private Room currentRoom;
    /**
    * The default player constructor.
    */
    public Player() {
      this("Bilbo Baggins");
    }

    /**
    * Constructor which allows for the specification of the name of the player.
    * @param newName the name of the player
    */
    public Player(String newName) {
      name = newName;
    }

    /**
    * Returns the name of the player.
    * @return name of the player
    */
    public String getName() {
      return name;
    }

    /**
    * Changes the name of the player.
    * @param newName the name of the player
    */
    public void setName(String newName) {
      name = newName;
    }

    /**
    * Returns a Point with the player's location.
    * @return (Point) A Point object containing the x and y coordinates of the player
    */
    public Point getXyLocation() {
      return xyLocation;
    }

    /**
    * Changes the location of the player by taking a new Point.
    * @param newXyLocation A Point object containing the player's x and y coordinates
    */
    public void setXyLocation(Point newXyLocation) {
      xyLocation = newXyLocation;
    }

    /**
    * Returns only the x coordinate of the player.
    * @return (int) the player's x coordinate (horizontal) as an int
    */
    public int getXCoordinate() {
      return ((int) xyLocation.getX());
    }

    /**
    * Returns only the y coordinate of the player.
    * @return (int) the player's y coordinate (vertical) as an int
    */
    public int getYCoordinate() {
      return ((int) xyLocation.getY());
    }

    /**
    * Moves the player by a specific amount.
    * @param dx the amount to move the player by in x (horizontally)
    * @param dy the amount to move the player by in y (vertically)
    */
    public void movePlayerBy(int dx, int dy) {
      xyLocation.translate(dx, dy);
    }

    /**
    * Returns the Room which the player is in.
    * @return (Room) The room the player is currently in.
    */
    public Room getCurrentRoom() {
      return currentRoom;
    }

    /**
    * Changes the room which the player is in.
    * @param newRoom The new Room object of the player. Don't forget to change the room state.
    */
    public void setCurrentRoom(Room newRoom) {
      currentRoom = newRoom;
    }

}
