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
    * Constructs a blank Item.
    * Sets a blank "scroll" at the origin
    */
    public Item() {
        this(-1, "Void Scroll", "default", new Point(0, 0));
    }
    /**
    * Constructs an item with the given properties.
    * @param newId the Id number of the item.
    * @param newName the name of the item, or null for no name
    * @param newType the type of the item.
    * @param startXyLocation where the item should be placed.
    */
    public Item(int newId, String newName, String newType, Point startXyLocation) {
      this.setId(newId);
      this.setName(newName);
      this.setType(newType);
      this.setXyLocation(startXyLocation);
      this.setDescription("");
      this.setDisplayCharacter('*');
    }

    // Getters and setters

    /**
    * Returns the id of the item.
    * @return (int) The id of the item
    */
    public int getId() {
      return id;
    }

    /**
    * Sets the id of an item
    * Do not use an id which has already been used.
    * @param newId the new id of the item
    */
    public void setId(int newId) {
      id = newId;
    }

    /**
    * Returns the name of the item.
    * @return (String) The name of the item
    */
    public String getName() {
      return name;
    }

    /**
    * Changes the name of the item.
    * @param newName The new name of the item
    */
    public void setName(String newName) {
      name = newName;
    }

    /**
    * Returns the type of the item.
    * @return (String) the item's type
    */
    public String getType() {
      return type;
    }

    /**
    * Sets the type of the item.
    * @param newType The new type of the item
    */
    public void setType(String newType) {
      type = newType;
    }

    /**
    * Returns the display character for the item.
    * @return (Character) The character that represents the item
    */
    public Character getDisplayCharacter() {
      return displayCharacter;
    }

    /**
    * Set the display character for the item.
    * @param newDisplayCharacter The character which should be displayed when this item appears in-game
    */
    public void setDisplayCharacter(Character newDisplayCharacter) {
      displayCharacter = newDisplayCharacter;
    }

    /**
    * Returns the description of the item.
    * @return (String) A description of the item.
    */
    public String getDescription() {
      return description;
    }

    /**
    * Sets a description for the item.
    * @param newDescription A brief description of something about the item
    */
    public void setDescription(String newDescription) {
      description = newDescription;
    }

    /**
    * Returns a Point object.
    * @return (Point) A Point object containing the x and y coordinates of the item.
    */
    public Point getXyLocation() {
      return xyLocation;
    }

    /**
    * Sets the x and y coordinates of the item.
    * @param newXyLocation the new coordinates of the object.
    */
    public void setXyLocation(Point newXyLocation) {
      xyLocation = newXyLocation;
    }

    /**
    * Returns the Room object where the item is located.
    * @return (Room) A room object.
    */
    public Room getCurrentRoom() {
      return currentRoom;
    }

    /**
    * Puts the item into a specific room.
    * @param newCurrentRoom the Room where the item will go
    */
    public void setCurrentRoom(Room newCurrentRoom) {
      currentRoom = newCurrentRoom;
    }
}
