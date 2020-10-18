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
    /**
    * Sets the player associated with the game.
    * @param newPlayer the Player object that represents the user character
    */
    public void setPlayer(Player newPlayer) { // player will be made in the Solution class
        thePlayer = newPlayer;
        this.thePlayer.setXyLocation(new Point(1, 1));
    }

    /**
    * Sets the display symbols based on a JSON file.
    * @param filename a JSON file containing associations of characters to elements of the game
    */
    public void setSymbols(String filename) {
      // (Almost) the same code as before to read the file and get a JSONObject which stores the graphics definitions.
            JSONParser parser = new JSONParser();
            JSONObject symbolsJSON = new JSONObject();
            try {
                Object obj = parser.parse(new FileReader(filename));
                symbolsJSON = (JSONObject) obj;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String symbolName = new String();
            String symbol = new String();
            Object symbolsObj = symbolsJSON.get("symbols");
            JSONArray symbolsList = new JSONArray();
            symbolsList = (JSONArray) symbolsObj;
            for (Object s : symbolsList) {
              JSONObject symbolPair = (JSONObject) s;
              symbolName = (String) symbolPair.get("name");
              symbol = (String) symbolPair.get("symbol");
              defaultSymbols.put(symbolName, symbol);
            }
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
    * Parses a JSON file which contains information about all the rooms and items in the level.
    * @param filename the name of the JSON file containing level data
    */
    public void createRooms(String filename) {
      // This method fills up the rooms array.
      //  (Almost) the same code as before to read the file and get a JSONObject which stores all that room information.
      JSONParser parser = new JSONParser();
      JSONObject roomsJSON = new JSONObject();
      try {
          Object obj = parser.parse(new FileReader(filename));
          roomsJSON = (JSONObject) obj;

      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (ParseException e) {
          e.printStackTrace();
      }
      Object roomsObj = roomsJSON.get("room");
      JSONArray allRooms = new JSONArray();
      allRooms = (JSONArray) roomsObj;

      for (Object roomObject: allRooms) {
        addRoom((JSONObject) roomObject);
      }

    }

    private void addRoom(JSONObject roomInfo) { //method to add a single room
      Room newRoom = new Room();
      // turn the prim-longs in the JSON to prim-ints that our methods take. like string decoding?
      int doorId;
      String doorStr;
      newRoom.setHeight(((Long) roomInfo.get("height")).intValue());
      newRoom.setWidth(((Long) roomInfo.get("width")).intValue());
      newRoom.setId(((Long) roomInfo.get("id")).intValue());
      newRoom.setPlayer(thePlayer);
      newRoom.setSymbols(defaultSymbols);
      // Get the array of doors, turn it into a JSONObject and get the id and dir
      JSONArray doors = (JSONArray) roomInfo.get("doors");
      JSONObject doorObj = new JSONObject();

      for (Object door : doors) {
        doorObj = (JSONObject) door;

        doorId = ((Long) doorObj.get("id")).intValue();
        doorStr = (String) doorObj.get("dir");

        newRoom.setDoor(doorStr, doorId);
      }

      if ((boolean) roomInfo.get("start")) {
        // set thePlayer's room to the room where you start.
        thePlayer.setCurrentRoom(newRoom);
        // set thePlayer's location to the average location -- the middle.
        int avgHeight = newRoom.getHeight() / 2;
        int avgWidth = newRoom.getWidth() / 2;
        thePlayer.setXyLocation(new Point(avgWidth, avgHeight));
      }

      JSONArray loot = (JSONArray) roomInfo.get("loot"); // get the room's loot as a JSONArray
      JSONObject lootObj = new JSONObject();
      ArrayList<Item> newRoomItems = new ArrayList<Item>();

      for (Object item: loot) { // for each item in the list initialize it and add it to an array.
        lootObj = (JSONObject) item;
        int itemId = ((Long) lootObj.get("id")).intValue();
        int itemX = ((Long) lootObj.get("x")).intValue();
        int itemY = ((Long) lootObj.get("y")).intValue();
        Item newItem = new Item(itemId, "default", "default", new Point(itemX, itemY));
        newRoomItems.add(newItem);
      }

      newRoom.setRoomItems(newRoomItems);
      // append the rooms list with the newly made room.
      rooms.add(newRoom);
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
