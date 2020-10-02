package rogue;

import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Point;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Rogue{
    private Player thePlayer;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items = new ArrayList<Item>();

    public void setPlayer(Player thePlayer){ // player will be made in the Solution class
        this.thePlayer = thePlayer;
    }


    public void setSymbols(String filename){
      // (Almost) the same code as before to read the file and get a JSONObject which stores the graphics definitions.
            JSONParser parser = new JSONParser();
            JSONObject symbolsJSON = new JSONObject(); // Node that we need to declare the symbolsJSON outside of the try/catch block-- this is required.
            try {
                Object obj = parser.parse(new FileReader(filename));
                symbolsJSON = (JSONObject) obj;

            } catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Object symbolsObj = symbolsJSON.get("symbols");
            JSONArray symbolsList = new JSONArray();
            symbolsList = (JSONArray) symbolsObj;
            
    }

    public ArrayList<Room> getRooms(){
        return rooms;

    }

    public ArrayList<Item> getItems(){
        return items;

    }
    public Player getPlayer(){
        return thePlayer;

    }

    public void createRooms(String filename){
      // This method fills up the rooms array.
      //  (Almost) the same code as before to read the file and get a JSONObject which stores all that room information.
      JSONParser parser = new JSONParser();
      JSONObject roomsJSON = new JSONObject();
      try {
          Object obj = parser.parse(new FileReader(filename));
          roomsJSON = (JSONObject) obj;

      } catch(FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (ParseException e) {
          e.printStackTrace();
      }
      Object roomsObj = roomsJSON.get("room");
      JSONArray allRooms = new JSONArray();
      allRooms = (JSONArray) roomsObj;
      for (Object roomObject: allRooms){
        addRoom((JSONObject) roomObject);
      }
      //System.out.println(allRooms.get(0));
    }

    private void addRoom(JSONObject roomInfo){ //roomInfo should be one of the objects from the array with the required fields
      Room newRoom = new Room();
      // turn the prim-longs in the JSON to prim-ints that our methods take. like string decoding?



      //JSONArray doors = (JSONArray)roomInfo.get("doors");
      // TO-DO: add the doors to the map in Room.

      //append the rooms list with the newly made room.
      rooms.add(newRoom);
    }
    public String displayAll(){
      String displayString = new String();
        //creates a string that displays all the rooms in the dungeon
        // not worried about fulfilling rooms that are adjacent or whatever, we're just printing rooms one after another
        for(Room rm: rooms){
          displayString += rm.displayRoom() + "\n\n";
        }
        return null;
    }
}
