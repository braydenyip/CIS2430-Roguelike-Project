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
      // turn the prim-longs in the JSON to prim-ints that our methods take. like string decoding?
      newRoom.setHeight( Integer.parseInt(roomData.get("height")) );
      newRoom.setWidth( Integer.parseInt(roomData.get("width")) );
      newRoom.setId( Integer.parseInt(roomData.get("id")) );
      newRoom.setSymbols(defaultSymbols);
      // Set the doors
      newRoom.setDoor("N",Integer.parseInt(roomData.get("N")));
      newRoom.setDoor("S",Integer.parseInt(roomData.get("S")));
      newRoom.setDoor("E",Integer.parseInt(roomData.get("E")));
      newRoom.setDoor("W",Integer.parseInt(roomData.get("W")));
      if (roomData.get("start").equals("true")) {
        configurePlayerStart(newRoom);
      }
      System.out.println(thePlayer.getXyLocation());
      newRoom.setPlayer(thePlayer);
      // append the rooms list with the newly made room.
      rooms.add(newRoom);
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
}
