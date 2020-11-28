package rogue;
import java.awt.Point;
import java.util.Random;
/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item  {


    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    private String displayCharacter;
    private int currentRoomId;
    private String description;
    private Random random = new Random();
    /**
    * Constructs a blank Item.
    * Sets a blank "scroll" at the origin
    */
    public Item() {
      this(-1, "Void Scroll", "default", new Point(-1, -1));
    }

    /**
    * Constructs an item with just type
    * @param newType the item's type
    */
    public Item(String newType) {
      setType(newType);
      setName("unknown");
      setId(-1);
      setXyLocation(new Point(-1, -1));
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
      this.setDisplayCharacter("!");
      this.setCurrentRoomId(-1);
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
    public String getDisplayCharacter() {
      return displayCharacter;
    }

    /**
    * Set the display character for the item.
    * @param newDisplayCharacter The character which should be displayed when this item appears in-game
    */
    public void setDisplayCharacter(String newDisplayCharacter) {
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
    * Returns only the x coordinate of the item.
    * @return (int) the item's x coordinate (horizontal) as an int
    */
    public int getXCoordinate() {
      return ((int) xyLocation.getX());
    }

    /**
    * Returns only the y coordinate of the item.
    * @return (int) the item's y coordinate (vertical) as an int
    */
    public int getYCoordinate() {
      return ((int) xyLocation.getY());
    }

    /**
    * Moves the item by a specific amount.
    * @param dx the amount to move the item by in x (horizontally)
    * @param dy the amount to move the item by in y (vertically)
    */
    public void moveItemBy(int dx, int dy) {
      xyLocation.translate(dx, dy);
    }

    /**
    * Returns the Room object where the item is located.
    * @return (Room) A room object.
    */
    public int getCurrentRoomId() {
      return currentRoomId;
    }

    /**
    * Puts the item into a specific room.
    * @param newCurrentRoomId the Room where the item will go
    */
    public void setCurrentRoomId(int newCurrentRoomId) {
      currentRoomId = newCurrentRoomId;
    }

    /**
    * Determines if there is an open spot on the map to place an item.
    * @param room the room to check if a spot exists in
    * @return (boolean) True if there is an adjacent open spot, otherwise false
    */

    public boolean openSpotExists(Room room) {
      int height = room.getHeight();
      int width = room.getWidth();
      for (int yNew = 1; yNew < (height - 1); yNew++) {
        for (int xNew = 1; xNew < (width - 1); xNew++) {
          if (room.itemOnTile(xNew, yNew) == null && !(room.playerOnTile(xNew, yNew))) {
            if (!(room.positionIsInvalid(xNew, yNew))) {
              setXyLocation(new Point(xNew, yNew));
              return true;
            }
          }
        }
      }
      return false;
    }

    /**
    * Returns a message that the game should display on item pickup.
    * @return (String) A string which indicates the user picked up the item
    */
    public String getPickupMessage() {
      String message = new String();
      message += ("Picked up " + name + ".");
      return message;
    }
}
