package rogue;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RogueParser {

    private ArrayList<Map<String, String>> rooms = new ArrayList<>();
    private ArrayList<Map<String, String>> items = new ArrayList<>();
    private ArrayList<Map<String, String>> itemLocations = new ArrayList<>();
    private HashMap<String, Map<String, String>> doors = new HashMap<>();
    private HashMap<String, Map<String, String>> doorConnections = new HashMap<>();
    private HashMap<String, String> symbols = new HashMap<>();

    private Iterator<Map<String, String>> roomIterator;
    private Iterator<Map<String, String>> itemIterator;

    private int numOfRooms = -1;
    private int numOfItems = -1;

    /**
     * Default constructor.
     */
    public RogueParser() {

    }

    /**
     * Constructor that takes filename and sets up the object.
     * @param filename  (String) name of file that contains file location for rooms and symbols
     */
    public RogueParser(String filename) {
        parse(filename);
    }

    /**
     * Return the next room.
     * @return (Map) Information about a room
     */
    public Map nextRoom() {

        if (roomIterator.hasNext()) {
            return roomIterator.next();
        } else {
            return null;
        }
    }

    /**
    * Returns the item locations map.
    * @return (ArrayList<HashMap<String, String>>) the item location map
    */
    public ArrayList<Map<String, String>> getItemLocations() {
      return itemLocations;
    }
    /**
     * Returns the next item.
     * @return (Map) Information about an item
     */
    public Map nextItem() {

        if (itemIterator.hasNext()) {
            return itemIterator.next();
        } else {
            return null;
        }

    }

    /**
     * Get the character (actually, a string) for a symbol.
     * This functionality also exists in Room.java
     * @param symbolName (String) Symbol Name
     * @return (String) Display character for the symbol
     */
    public String getSymbol(String symbolName) {

        if (symbols.containsKey(symbolName)) {
            return symbols.get(symbolName);
        }

        // Does not contain the key
        return null;
    }
    /**
    * Get the door position map of a room.
    * @param id the id of the room
    * @return (HashMap<String, String>) a map associating door direction to position
    */
    public HashMap<String, String> getDoorPositions(int id) {
      return (HashMap<String, String>) (doors.get(Integer.toString(id)));
    }
    /**
    * Get the door connection map of a given room.
    * @param id the id of the room
    * @return (HashMap<String, String>) a map indicating door connection
    */
    public HashMap<String, String> getDoorConnections(int id) {
      return (HashMap<String, String>) (doorConnections.get(Integer.toString(id)));
    }
    /**
     * Get all the symbols in a map.
     * @return (HashMap<String, String>) The map with all the symbols
     */
    public HashMap<String, String> getAllSymbols() {
      return symbols;
    }

    /**
     * Get the number of items.
     * @return (int) Number of items
     */
    public int getNumOfItems() {

        return numOfItems;
    }

    /**
     * Get the number of rooms.
     * @return (int) Number of rooms
     */
    public int getNumOfRooms() {

        return numOfRooms;
    }

    /**
     * Read the file containing the file locations.
     * @param filename (String) Name of the file
     */
    private void parse(String filename) {

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONObject configurationJSON = (JSONObject) obj;

            // Extract the Rooms value from the file to get the file location for rooms
            String roomsFileLocation = (String) configurationJSON.get("Rooms");

            // Extract the Symbols value from the file to get the file location for symbols-map
            String symbolsFileLocation = (String) configurationJSON.get("Symbols");

            parseSymbols(symbolsFileLocation);
            parseRooms(roomsFileLocation);


        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file named: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        }

    }

    /**
     * Parses just a symbols file.
     * @param symbolsFilename the filename of the symbols file.
     */
    private void parseSymbols(String symbolsFilename) throws FileNotFoundException, IOException, ParseException {
        try {
            JSONParser parser = new JSONParser();
            Object symbolsObj = parser.parse(new FileReader(symbolsFilename));
            JSONObject symbolsJSON = (JSONObject) symbolsObj;
            extractSymbolInfo(symbolsJSON);
        } catch (FileNotFoundException fe) {
            throw fe;
        } catch (IOException ioe) {
            throw ioe;
        } catch (ParseException pe) {
            throw pe;
        }
    }

    private void parseRooms(String roomsFilename) throws FileNotFoundException, IOException, ParseException {
        try {
            JSONParser parser = new JSONParser();
            Object roomsObj = parser.parse(new FileReader(roomsFilename));
            JSONObject roomsJSON = (JSONObject) roomsObj;

            extractRoomInfo(roomsJSON);
            extractItemInfo(roomsJSON);

            roomIterator = rooms.iterator();
            itemIterator = items.iterator();
        } catch (FileNotFoundException fe) {
            throw fe;
        } catch (IOException ioe) {
            throw ioe;
        } catch (ParseException pe) {
            throw pe;
        }
    }

    /**
     * Parses a .json file object.
     * @param symbolsFile the file
     * @throws FileNotFoundException if file is not found
     * @throws IOException if there is an error with file io
     * @throws ParseException if there is an error parsing the file
     */
    public void parseSymbols(File symbolsFile) throws FileNotFoundException, IOException, ParseException {
        try {
            JSONParser parser = new JSONParser();
            Object symbolsObj = parser.parse(new FileReader(symbolsFile));
            JSONObject symbolsJSON = (JSONObject) symbolsObj;
            extractSymbolInfo(symbolsJSON);
        } catch (FileNotFoundException fe) {
            throw fe;
        } catch (IOException ioe) {
            throw ioe;
        } catch (ParseException pe) {
            throw pe;
        }
    }

    /**
     * Parses a .json file object for the rooms.
     * @param roomsFile the rooms file
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if there is an error with io
     * @throws ParseException if there is an error parsing the file
     */
    public void parseRooms(File roomsFile) throws FileNotFoundException, IOException, ParseException {
        try {
            JSONParser parser = new JSONParser();
            Object roomsObj = parser.parse(new FileReader(roomsFile));
            JSONObject roomsJSON = (JSONObject) roomsObj;

            extractRoomInfo(roomsJSON);
            extractItemInfo(roomsJSON);

            roomIterator = rooms.iterator();
            itemIterator = items.iterator();
        } catch (FileNotFoundException fe) {
            throw fe;
        } catch (IOException ioe) {
            throw ioe;
        } catch (ParseException pe) {
            throw pe;
        }
    }

    /**
     * Get the symbol information.
     * @param symbolsJSON  (JSONObject) Contains information about the symbols
     */
    private void extractSymbolInfo(JSONObject symbolsJSON) {


        JSONArray symbolsJSONArray = (JSONArray) symbolsJSON.get("symbols");

        // Make an array list of room information as maps
        for (int i = 0; i < symbolsJSONArray.size(); i++) {
            JSONObject symbolObj = (JSONObject) symbolsJSONArray.get(i);
            symbols.put(symbolObj.get("name").toString(), String.valueOf(symbolObj.get("symbol")));
        }
    }

    /**
     * Get the room information.
     * @param roomsJSON (JSONObject) Contains information about the rooms
     */
    private void extractRoomInfo(JSONObject roomsJSON) {


        JSONArray roomsJSONArray = (JSONArray) roomsJSON.get("room");

        // Make an array list of room information as maps
        for (int i = 0; i < roomsJSONArray.size(); i++) {
            rooms.add(singleRoom((JSONObject) roomsJSONArray.get(i)));
            numOfRooms += 1;
        }
    }

    /**
     * Get a room's information.
     * @param roomJSON (JSONObject) Contains information about one room
     * @return (Map<String, String>) Contains key/values that has information about the room
     */
    private Map<String, String> singleRoom(JSONObject roomJSON) {

        HashMap<String, String> room = new HashMap<>();
        String idStr = roomJSON.get("id").toString();
        room.put("id", idStr);
        room.put("start", roomJSON.get("start").toString());
        room.put("height", roomJSON.get("height").toString());
        room.put("width", roomJSON.get("width").toString());

        JSONArray doorArray = (JSONArray) roomJSON.get("doors");
        HashMap<String, String> newDoors = new HashMap<>();
        HashMap<String, String> newDoorConnects = new HashMap<>();
        for (int j = 0; j < doorArray.size(); j++) {
            JSONObject doorObj = (JSONObject) doorArray.get(j);
            String dir = doorObj.get("dir").toString();
            String id = doorObj.get("wall_pos").toString();
            String con = doorObj.get("con_room").toString();
            newDoors.put(dir, id);
            newDoorConnects.put(dir, con);
            doors.put(idStr, newDoors);
            doorConnections.put(idStr, newDoorConnects);
        }
        JSONArray lootArray = (JSONArray) roomJSON.get("loot");
        for (int j = 0; j < lootArray.size(); j++) {
            itemLocations.add(itemPosition((JSONObject) lootArray.get(j), roomJSON.get("id").toString()));
        }
        return room;
    }

    /**
     * Create a map for information about an item in a room.
     * @param lootJSON (JSONObject) Loot key from the rooms file
     * @param roomID (String) Room id value
     * @return (Map<String, String>) Contains information about the item, where it is and what room
     */
    private Map<String, String>  itemPosition(JSONObject lootJSON, String roomID) {

        HashMap<String, String> loot = new HashMap<>();
        loot.put("room", roomID);
        loot.put("id", lootJSON.get("id").toString());
        loot.put("x", lootJSON.get("x").toString());
        loot.put("y", lootJSON.get("y").toString());

        return loot;
    }

    /**
     * Get the Item information from the Item key.
     * @param roomsJSON (JSONObject) The entire JSON file that contains keys for room and items
     */
    private void extractItemInfo(JSONObject roomsJSON) {

        JSONArray itemsJSONArray = (JSONArray) roomsJSON.get("items");
        for (int i = 0; i < itemsJSONArray.size(); i++) {
            items.add(singleItem((JSONObject) itemsJSONArray.get(i)));
            numOfItems += 1;
        }
    }

    /**
     * Get a single item from its JSON object.
     * @param itemsJSON (JSONObject) JSON version of an item
     * @return (Map<String, String>) Contains information about a single item
     */
    private Map<String, String> singleItem(JSONObject itemsJSON) {
        HashMap<String, String> item = new HashMap<>();
        item.put("id", itemsJSON.get("id").toString());
        item.put("name", itemsJSON.get("name").toString());
        item.put("type", itemsJSON.get("type").toString());
        item.put("description", itemsJSON.get("description").toString());
        item.put("displayCharacter", symbols.get(item.get("type").toUpperCase()));
        initializeItemLoc(item);
        for (Map<String, String> itemLocation : itemLocations) {
            if (itemLocation.get("id").toString().equals(item.get("id"))) {
                // if the item exists in the room specify the item's coordinates
                item.put("room", itemLocation.get("room"));
                item.put("x", itemLocation.get("x"));
                item.put("y", itemLocation.get("y"));
                break;
            }
        }
        return item;
    }

    private void initializeItemLoc(HashMap<String, String> item) {
      // if the item location is not associated with the item
      item.put("room", "-1"); // if an item has no association it will stay -1
      item.put("x", "-1");
      item.put("y", "-1");
    }

}
