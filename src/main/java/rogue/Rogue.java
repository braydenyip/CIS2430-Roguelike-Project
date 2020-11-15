package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.awt.Point;


/**
* The class that stores the game state and links other classes together.
*/
public class Rogue {
    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';

    private String messageToPrint = new String();
    private String displayString = new String();
    private Player thePlayer;
    private static ArrayList<Room> rooms = new ArrayList<Room>();
    private static ArrayList<Item> items = new ArrayList<Item>();
    private static ArrayList<Map<String, String>> itemLocations;
    private static HashMap<String, String> defaultSymbols = new HashMap<String, String>();
    private RogueParser parser;

    /**
    * One parameter constructor.
    * @param theParser the RogueParser for the game, which must be initialized.
    */
    public Rogue(RogueParser theParser) {
      setParser(theParser);
    }

    /**
    * Creates the rooms and items for the game environment from JSON.
    */
    public void initializeGameState() {
      setSymbols();
      setItemLocations();
      createItems();
      createRooms();
    }

    /**
    * Sets the parser that the Rogue uses.
    * @param newParser the new parser for the game
    */
    public void setParser(RogueParser newParser) {
      parser = newParser;
    }

    /**
    * Sets the player associated with the game.
    * @param newPlayer the Player object that represents the user character
    */
    public void setPlayer(Player newPlayer) { // player will be made in the class above
        thePlayer = newPlayer;
        this.thePlayer.setXyLocation(new Point(1, 1));
    }

    /**
    * Gets the display symbol map.
    * @return (HashMap<String, String>) the symbol map
    */
    public HashMap<String, String> getSymbols() {
      return defaultSymbols;
    }

    /**
    * Sets or refreshes the displaySymbols to be passed to display methods.
    */
    public void setSymbols() {
      defaultSymbols = parser.getAllSymbols();
    }

    /**
    * Returns a list of all of the Rooms in the level.
    * @return (ArrayList<Room>) the list of rooms in the level
    */
    public ArrayList<Room> getRooms() {
        return rooms;

    }
    /**
    * Returns a list of all of the Items in the level.
    * @return (ArrayList<Room>) the list of items in the level
    */
    public ArrayList<Item> getItems() {
        return items;

    }
    /**
    * Returns the Player in its current state in the game.
    * @return (Player) the Player object containing its state and attributes
    */
    public Player getPlayer() {
        return thePlayer;
    }

    /**
    * Sets the itemLocations to the one in the parser.
    */
    public void setItemLocations() {
      itemLocations = parser.getItemLocations();
    }
    /**
    * Determines whether an item exists in the list of items.
    * @param toAdd the item to add to the room
    * @return (boolean) True if the item is in the list of items, otherwise false
    */
    public static boolean itemExists(Item toAdd) {
      if (items.contains(toAdd)) {
        return true;
      }
      return false;
    }

    /**
    * Runs the Parser's roomIterator to fill the array "rooms" with objects.
    */
    public void createRooms() {
      // This method fills up the rooms array.
      HashMap<String, String> theRoomData = (HashMap<String, String>) parser.nextRoom();
      while (theRoomData != null) {
        Room newRoom = createARoom(theRoomData);
        rooms.add(newRoom);
        theRoomData = (HashMap<String, String>) parser.nextRoom();
      }
      addDoors();
    }

    // turns a HashMap from the RogueParser into a Room and appends.
    private Room createARoom(HashMap<String, String> roomData) { //method to add a single room
      Room newRoom = new Room();
      newRoom.setRogue(this);
      newRoom.setHeight(Integer.parseInt(roomData.get("height")));
      newRoom.setWidth(Integer.parseInt(roomData.get("width")));
      newRoom.setId(Integer.parseInt(roomData.get("id")));
      newRoom.updateFromRogue();
      if (roomData.get("start").equals("true")) {
        configurePlayerStart(newRoom);
      }
      for (Item anItem : items) { // add items from parser
        if (anItem.getCurrentRoomId() > 0) {
          attemptToAddItem(newRoom, anItem);
        }
      }
      return newRoom;
    }

    private void configurePlayerStart(Room newRoom) {
      // set thePlayer's room to the room where you start.
      thePlayer.setCurrentRoom(newRoom);
      // set thePlayer's location to the UL corner
      thePlayer.setXyLocation(new Point(1, 1));
    }


    private void attemptToAddItem(Room newRoom, Item anItem) {
      try {
        newRoom.addItem(anItem);
      } catch (NoSuchItemException e) {
        for (Map<String, String> location : itemLocations) {
          if (Integer.parseInt(location.get("id")) == anItem.getId()) {
            itemLocations.remove(location);
            break;
          }
        }
      } catch (ImpossiblePositionException e) {
        if (newRoom.openSpotExists(anItem)) {
          attemptToAddItem(newRoom, anItem);
        } else {
          System.out.println(e);
        }
      }
    }

    // once all rooms are initialized, this connects the rooms' doors
    private void addDoors() {
      for (Room room: rooms) {
        addDoorsToRoom(room);
      }
    }

    // method to add doors to the room
    private void addDoorsToRoom(Room newRoom) {
      int id = newRoom.getId();
      HashMap<String, String> doorData = parser.getDoorPositions(id);
      for (String direction : doorData.keySet()) {
        Door newDoor = new Door();
        newDoor.setPosition(Integer.parseInt(doorData.get(direction)));
        newDoor.setDirection(direction);
        connectDoor(newDoor, direction, id);
        newRoom.addDoor(newDoor);
      }
    }

    private void connectDoor(Door newDoor, String direction, int id) {
      HashMap<String, String> doorCons = parser.getDoorConnections(id);
      int roomId = Integer.parseInt(doorCons.get(direction));
      for (Room room: rooms) {
        if (roomId == room.getId()) {
          newDoor.connectRoom(room);
          break;
        }
      }
    }

    /**
    * Verifies all the rooms in the game.
    * @return (boolean) True if all rooms are valid, otherwise false.
    */
    public boolean verifyAllRooms() {
      for (Room room: rooms) {
        try {
          if (!(room.verifyRoom())) {
            return false;
          }
        } catch (NotEnoughDoorsException e) {
          Room room2 = findOpenRoom();
          if (room2 == null) {
            return false;
          } else if (!(room.addRandomDoor(room2) && room2.addRandomDoor(room))) {
            return false;
          }
        }
      }
      return true;
    }

    private Room findOpenRoom() {
      for (Room room: rooms) {
        if (room.getAllDoors().size() < 4) {
          return room;
        }
      }
      return null;
    }
    /**
    * Fills the item array in the same way as the rooms.
    */
    public void createItems() {
      HashMap<String, String> theItemData = (HashMap<String, String>) parser.nextItem();
      while (theItemData != null) {
        addItem(theItemData);
        theItemData = (HashMap<String, String>) parser.nextItem();
      }
    }

    // adds an item to the list of items
    private void addItem(HashMap<String, String> itemData) {
      Item newItem = new Item();
      int roomId = Integer.parseInt(itemData.get("room"));
      newItem.setId(Integer.parseInt(itemData.get("id")));
      newItem.setName(itemData.get("name"));
      newItem.setType(itemData.get("type"));
      newItem.setDisplayCharacter(itemData.get("displayCharacter"));
      newItem.setDescription(itemData.get("description"));
      newItem.setCurrentRoomId(roomId);
      setItemPosition(newItem, itemData.get("x"), itemData.get("y"));
      items.add(newItem);
    }

    // Converts strings to ints and sets newItem's position to a new Point.
    private void setItemPosition(Item newItem, String xString, String yString) {
      int x = Integer.parseInt(xString);
      int y = Integer.parseInt(yString);
      newItem.setXyLocation(new Point(x, y));
    }

    /**
    * Outputs a new string based on a player move.
    * @return (String) the updated string
    * @param input The character representing the player input
    * @throws InvalidMoveException when the player makes a move to an invalid location
    */
    public String makeMove(char input) throws InvalidMoveException {
      messageToPrint = "";
      setNewCoordinates(input);
      Room nextRoom = movingIntoDoor();
      Item toCollect = movingIntoItem();

      if (nextRoom != null) {
        moveOverPlayer(nextRoom);
      } else if (moveIsIllegal()) {
        throw new InvalidMoveException("You can't move there!");
      } else { // legal move scenario
        if (toCollect != null) { // add item to inventory, remove from room
          messageToPrint = toCollect.getPickupMessage();
          thePlayer.collectItem(toCollect);
        }
      }
      // rerender the room
      return messageToPrint;
    }

    // Like the above method, but does not allocate new memory
    private void setNewCoordinates(char input) {
      if (input == this.LEFT) {
        thePlayer.movePlayerBy(-1, 0);
      } else if (input == this.RIGHT) {
        thePlayer.movePlayerBy(1, 0);
      } else if (input == this.UP) {
        thePlayer.movePlayerBy(0, -1);
      } else if (input == this.DOWN) {
        thePlayer.movePlayerBy(0, 1);
      }
    }

    //move player to new room (vv broken).
    private void moveOverPlayer(Room toMoveTo) {
      thePlayer.setCurrentRoom(toMoveTo);
      int x = thePlayer.getXCoordinate();
      int y = thePlayer.getYCoordinate();
      if (x == 1) { // Exited west entering east
        y = toMoveTo.getDoorPosition("E");
        thePlayer.setXyLocation(new Point((toMoveTo.getWidth() - 2), y));
      } else if (x == (toMoveTo.getWidth() - 1)) { // exited east entering west
        y = toMoveTo.getDoorPosition("W");
        thePlayer.setXyLocation(new Point(1, y));
      } else if (y == 1) { // exited north entering south
        x = toMoveTo.getDoorPosition("S");
        thePlayer.setXyLocation(new Point(x, (toMoveTo.getHeight() - 2)));
      } else { // exited south entering north
        x = toMoveTo.getDoorPosition("N");
        thePlayer.setXyLocation(new Point(x, 1));
      }
    }



    private Room movingIntoDoor() {
      Room theRoom = thePlayer.getCurrentRoom();
      int width = theRoom.getWidth();
      int height = theRoom.getHeight();
      int newX = thePlayer.getXCoordinate();
      int newY = thePlayer.getYCoordinate();
      if (newX == 0 && newY == theRoom.getDoorPosition("W")) {
        return theRoom.getDoor("W").getOtherRoom(theRoom);
      } else if (newX == (width - 1) && newY == theRoom.getDoorPosition("E")) {
        return theRoom.getDoor("E").getOtherRoom(theRoom);
      } else if (newY == 0 && newX == theRoom.getDoorPosition("N")) {
        return theRoom.getDoor("N").getOtherRoom(theRoom);
      } else if (newY == (height - 1) && newX == theRoom.getDoorPosition("S")) {
        return theRoom.getDoor("S").getOtherRoom(theRoom);
      }
      return null;
    }

    // Encapsulates illegal move logic
    private boolean moveIsIllegal() {
      Room theRoom = thePlayer.getCurrentRoom();
      int width = theRoom.getWidth();
      int height = theRoom.getHeight();
      int newX = thePlayer.getXCoordinate();
      int newY = thePlayer.getYCoordinate();
      if (newX < 1 || newX >= (width - 1) || newY < 1 || newY >= (height - 1)) {
        resetPlayerPosition(newX, newY, width, height);
        return true;
      }
      return false;
    }

    private void resetPlayerPosition(int x, int y, int width, int height) {
      if (x == 0) {
        thePlayer.movePlayerBy(1, 0);
      } else if (x == (width - 1)) {
        thePlayer.movePlayerBy(-1, 0);
      } else if (y == 0) {
        thePlayer.movePlayerBy(0, 1);
      } else if (y == (height - 1)) {
        thePlayer.movePlayerBy(0, -1);
      }
    }
    // Returns an Item if the player is about to move on it, null otherwise.
    private Item movingIntoItem() {
      int newX = thePlayer.getXCoordinate();
      int newY = thePlayer.getYCoordinate();
      return (thePlayer.getCurrentRoom().itemOnTile(newX, newY));
    }

    /**
    * Returns the updated string that displays the game.
    * @return (String) A formatted string that represents the room the player is in
    */
    public String getNextDisplay() {
      displayString = "";
      // creates a string that displays all the rooms in the dungeon
      // not worried about fulfilling rooms that are adjacent or whatever, we're just printing rooms one after another
      for (Room rm: rooms) {
        if (rm.equals(thePlayer.getCurrentRoom())) {
          displayString = rm.updateRoomString(displayString);
          break;
        }
      }
      return displayString;
    }

}
