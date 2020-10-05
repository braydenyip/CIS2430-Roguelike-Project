package rogue;
import java.awt.Point;

/**
 * A basic Item class; basic functionality for both consumables and equipment
 */
public class Item  {


    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    private Character displayCharacter;
    private Room currentRoom;
    private String description;

    //Constructors
    public Item() {
        this(-1,"Void Scroll", "default", new Point(0,0));
    }

    public Item(int id, String name, String type, Point xyLocation) {
      this.setId(id);
      this.setName(name);
      this.setType(type);
      this.setXyLocation(xyLocation);
    }

    // Getters and setters


    public int getId() {
      return id;
    }


    public void setId(int id) {
      this.id = id;
    }


    public String getName() {
      return name;
    }


    public void setName(String name) {
      this.name = name;
    }


    public String getType() {
      return type;
    }


    public void setType(String type) {
      this.type = type;
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
