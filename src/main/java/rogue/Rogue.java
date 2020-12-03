package rogue;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public static final char CONSUME = 'e';
    public static final char TOSS = 't';
    public static final char WEAR = 'y';
    public static final char INV = 'i';

    private boolean itemPickedUp;
    private String messageToPrint = new String();
    private String displayString = new String();
    private Player thePlayer;
    private static ArrayList<Room> rooms = new ArrayList<Room>();
    private static ArrayList<Item> items = new ArrayList<Item>();
    private static ArrayList<Map<String, String>> itemLocations;
    private static HashMap<String, String> defaultSymbols = new HashMap<String, String>();
    // make transient
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
     * Refreshes the game based on file inputs.
     * @param symbols a Symbols file, or null to keep symbols constant.
     * @param rooms a Rooms file, or null to keep rooms constant.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    public void refreshGameState(File symbols, File rooms) throws FileNotFoundException, IOException, ParseException {
        if (symbols != null && rooms != null) { // both files
            parser.parseSymbols(symbols);
            parser.parseRooms(rooms);
        } if (rooms == null && symbols != null) { // just symbols
            parser.parseSymbols(symbols);
        } if (symbols == null && rooms != null) { // just rooms
            parser.parseRooms(rooms);
        }
        if (symbols != null || rooms != null) { // refresh the game state if something was changed.
            initializeGameState();
        }
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
     * Detects if an item was picked up on last turn.
     * @return True only if the item was picked up on the last move.
     */
    public boolean isItemPickedUp() {
        return itemPickedUp;
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
        if (anItem.getCurrentRoomId() == newRoom.getId()) {
          newRoom.addItem(anItem);
        }
      } catch (NoSuchItemException e) {
        for (Map<String, String> location : itemLocations) {
          if (Integer.parseInt(location.get("id")) == anItem.getId()) {
            itemLocations.remove(location);
            break;
          }
        }
      } catch (ImpossiblePositionException e) {
        if (anItem.openSpotExists(newRoom)) {
          attemptToAddItem(newRoom, anItem);
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
      if (doorData != null) {
        for (String direction : doorData.keySet()) {
          Door newDoor = new Door();
          newDoor.setPosition(Integer.parseInt(doorData.get(direction)));
          newDoor.setDirection(direction);
          connectDoor(newDoor, direction, id);
          newRoom.addDoor(newDoor);
        }
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
      Item newItem = chooseItemSubtype(itemData);
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

    // Picks an item subtype to apply to appropriate methods to
    private Item chooseItemSubtype(HashMap<String, String> itemData) {
      String type = itemData.get("type");
      if (type.equals("Food") || type.equals("SmallFood") || type.equals("Potion")) {
        return handleConsumable(itemData);
      } else if (type.equals("Clothing") || type.equals("Ring")) {
        return handleWearable(itemData);
      } else if (type.equals("Scroll")) {
        return handleOtherMagical(itemData);
      }
      return new Item();
    }

    private Item handleConsumable(HashMap<String, String> itemData) {
      String type = itemData.get("type");
      if (type.equals("Food")) {
        return new Food();
      } else if (type.equals("SmallFood")) {
        return new SmallFood();
      } else {
        return new Potion();
      }
    }

    private Item handleWearable(HashMap<String, String> itemData) {
      String type = itemData.get("type");
      if (type.equals("Clothing")) {
        return new Clothing();
      } else {
        return new Ring();
      }
    }

    private Item handleOtherMagical(HashMap<String, String> itemData) {
      String type = itemData.get("type");
      if (type.equals("Scroll")) {
        return new Scroll();
      } else {
        return new MagicalItem();
      }
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
      messageToPrint = makeInputDecision(input);
      Room nextRoom = movingIntoDoor();
      if (nextRoom != null) {
        moveOverPlayer(nextRoom, input);
      } else if (moveIsIllegal()) {
        throw new InvalidMoveException("You can't move there!");
      }
      Item toCollect = movingIntoItem();
      if (toCollect != null) { // add item to inventory, remove from room
        messageToPrint = toCollect.getPickupMessage();
        thePlayer.collectItem(toCollect);
        itemPickedUp = true;
      } else {
          itemPickedUp = false;
      }
      return messageToPrint; // return the pickup message.
    }

    private String makeInputDecision(char input) {
      if (input == CONSUME) {
        return "You eat";
      } else if (input == TOSS) {
        return "You throw";
      } else if (input == WEAR) {
        return "You wear";
      } else {
        setNewCoordinates(input);
        return "";
      }
    }

    /*
    public boolean playerDoesInventoryAction(char input) {
        return input == CONSUME || input == WEAR || input == TOSS;
    }
     */
    // changes a player's location
    private void setNewCoordinates(char input) {
      if (input == LEFT) {
        thePlayer.movePlayerBy(-1, 0);
      } else if (input == RIGHT) {
        thePlayer.movePlayerBy(1, 0);
      } else if (input == UP) {
        thePlayer.movePlayerBy(0, -1);
      } else if (input == DOWN) {
        thePlayer.movePlayerBy(0, 1);
      }
    }


    private Map<Integer, Item> getInventoryMap() {
      return thePlayer.getInventory().getInventory();
    }

    //move player to new room  based on door positions
    private void moveOverPlayer(Room toMoveTo, char input) {
      thePlayer.setCurrentRoom(toMoveTo);
      int x = 0;
      int y = 0;
      if (input == LEFT) { // out the west door in east side
        x = toMoveTo.getWidth() - 2;
        y = newPositionAgainstDoor(toMoveTo, "E");
      } else if (input == RIGHT) { // out east door in west side
        x = 1;
        y = newPositionAgainstDoor(toMoveTo, "W");
      } else if (input == UP) {
        y = toMoveTo.getHeight() - 2;
        x = newPositionAgainstDoor(toMoveTo, "S");
      } else if (input == DOWN) {
        y = 1;
        x = newPositionAgainstDoor(toMoveTo, "N");
      }
      thePlayer.setXyLocation(new Point(x, y));
    }

    private int newPositionAgainstDoor(Room toMoveTo, String direction) {
      int l = toMoveTo.getDoorPosition(direction);
      return Math.max(l, 1);
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

    // "Rebounds" a player after a move that was not legal.
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
      for (Room rm: rooms) {
        if (rm.equals(thePlayer.getCurrentRoom())) {
          displayString = rm.updateRoomString(displayString);
          break;
        }
      }
      return displayString;
    }

}
