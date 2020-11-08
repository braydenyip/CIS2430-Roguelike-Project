package rogue;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Point;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
* The class that stores the game state and links other classes together.
*/
public class Rogue {
    private Player thePlayer;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private HashMap<String, String> defaultSymbols = new HashMap<String, String>();
    private RogueParser parser;

    /**
    * Two parameter constructor
    * @param theParser the RogueParser for the game, which must be initialized
    * @param theHero the Player, which must be initialized
    */
    public Rogue(RogueParser theParser, Player theHero) {
      setPlayer(theHero);
      setParser(theParser);
    }

    /**
    * Sets the parser that the Rogue uses
    * @param newParser the new parser for the game
    */
    public void setParser(RogueParser newParser) {
      parser = newParser;
    }

    /**
    * Sets the player associated with the game.
    * @param newPlayer the Player object that represents the user character
    */
    public void setPlayer(Player newPlayer) { // player will be made in the Solution class
        thePlayer = newPlayer;
        this.thePlayer.setXyLocation(new Point(1, 1));
    }


    /**
    * Sets or refreshes the displaySymbols to be passed to display methods
    */
    public void setSymbols() {
      // (Almost) the same code as before to read the file and get a JSONObject which stores the graphics definitions.
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
    * Creates the rooms and items for the game environment from JSON
    */
    public void initializeGameState(){
      setSymbols();
      createItems();
      createRooms();
    }
    /**
    * Runs the Parser's roomIterator to fill the array "rooms" with objects
    */
    public void createRooms() {
      // This method fills up the rooms array.
      HashMap<String, String> theRoomData = (HashMap<String, String>) parser.nextRoom();
      while (theRoomData != null){
        addRoom(theRoomData);
        theRoomData = (HashMap<String, String>) parser.nextRoom();
      }
    }

    // turns a HashMap from the RogueParser into a Room and appends
    private void addRoom(HashMap<String, String> roomData) { //method to add a single room
      Room newRoom = new Room();
      newRoom.setHeight( Integer.parseInt(roomData.get("height")) );
      newRoom.setWidth( Integer.parseInt(roomData.get("width")) );
      newRoom.setId( Integer.parseInt(roomData.get("id")) );
      newRoom.setSymbols(defaultSymbols);
      addDoorsToRoom(newRoom);
      if (roomData.get("start").equals("true")) {
        configurePlayerStart(newRoom);
      }
      newRoom.setPlayer(thePlayer);
      for (Item anItem : items){
        try {
          newRoom.addItem(anItem);
        } catch(NoSuchItemException e) {
          System.out.println(e);
        } catch(ImpossiblePositionException e) {
          System.out.println(e);
        }
      }
      // append the rooms list with the newly made room.
      rooms.add(newRoom);
    }

    // method to add doors to the room
    private void addDoorsToRoom(Room newRoom){
      HashMap<String, String> doorData = parser.getDoorPositions(newRoom.getId());
      for(String direction : doorData.keySet()){
        Door newDoor = new Door();
        newDoor.setPosition(Integer.parseInt(doorData.get(direction)));
        newDoor.setDirection(direction);
        newRoom.addDoor(newDoor);
      }
    }

    private void configurePlayerStart(Room newRoom) {
      // set thePlayer's room to the room where you start.
      thePlayer.setCurrentRoom(newRoom);
      // set thePlayer's location to the average location
      int avgHeight = newRoom.getHeight() / 2;
      int avgWidth = newRoom.getWidth() / 2;
      thePlayer.setXyLocation(new Point(avgWidth, avgHeight));
    }

    /**
    * Returns a string that displays all the rooms in the level.
    * @return (String) A formatted string that displays all the rooms in the level
    */
    public String displayAll() {
      String displayString = new String();
        // creates a string that displays all the rooms in the dungeon
        // not worried about fulfilling rooms that are adjacent or whatever, we're just printing rooms one after another
        for (Room rm: rooms) {
          displayString += rm.displayRoom() + "\n\n";
        }
        return displayString;
    }

    /**
    * Fills the item array in the same way as the rooms
    */
    public void createItems(){
      HashMap<String, String> theItemData = (HashMap<String, String>) parser.nextItem();
      while (theItemData != null){
        addItem(theItemData);
        theItemData = (HashMap<String, String>) parser.nextItem();
      }
    }

    private void addItem(HashMap<String, String> itemData){
      Item newItem = new Item();
      int roomId = Integer.parseInt(itemData.get("room"));
      newItem.setId(Integer.parseInt(itemData.get("id")));
      newItem.setName(itemData.get("name"));
      newItem.setType(itemData.get("type"));
      newItem.setDisplayCharacter(itemData.get("displayCharacter"));
      newItem.setCurrentRoomId(roomId);
      if (roomId > 0){
        setItemPosition(newItem,itemData.get("x"),itemData.get("y"));
      }
      items.add(newItem);
    }

    // Converts strings to ints and sets newItem's position to a new Point.
    private void setItemPosition(Item newItem, String xString, String yString){
      int x = Integer.parseInt(xString);
      int y = Integer.parseInt(yString);
      newItem.setXyLocation(new Point(x,y));
    }

    public String makeMove(char input) throws InvalidMoveException{
      System.out.println(input);
    }
}
