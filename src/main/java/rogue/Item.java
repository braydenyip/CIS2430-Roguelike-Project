package rogue;
import java.awt.Point;

/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item  {


    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    private Character displayCharacter;
    private Room currentRoom;
    private String description;

    /**
    * The default constructor
    * Sets a blank "scroll" at the origin
    */
    public Item() {
        this(-1, "Void Scroll", "default", new Point(0, 0));
    }

    public Item(int newId, String newName, String newType, Point startXyLocation) {
      this.setId(newId);
      this.setName(newName);
      this.setType(newType);
      this.setXyLocation(startXyLocation);
    }

    // Getters and setters


    public int getId() {
      return id;
    }


    public void setId(int newId) {
      id = newId;
    }


    public String getName() {
      return name;
    }


    public void setName(String newName) {
      name = newName;
    }


    public String getType() {
      return type;
    }


    public void setType(String newType) {
      type = newType;
    }


    public Character getDisplayCharacter() {
      return null;
    }


    public void setDisplayCharacter(Character newDisplayCharacter) {
      displayCharacter = newDisplayCharacter;
    }


    public String getDescription() {
      return null;
    }


    public void setDescription(String newDescription) {
      description = newDescription;
    }


    public Point getXyLocation() {
      return xyLocation;
    }


    public void setXyLocation(Point newXyLocation) {
      xyLocation = newXyLocation;
    }


    public Room getCurrentRoom() {
      return null;
    }


    public void setCurrentRoom(Room newCurrentRoom) {
      currentRoom = newCurrentRoom;
    }
}
