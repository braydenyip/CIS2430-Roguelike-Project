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

    public void setPlayer(Player thePlayer){
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
            System.out.println(symbolsJSON.toString());
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
      System.out.println(roomsJSON.toString());

    }
    public String displayAll(){
        //creates a string that displays all the rooms in the dungeon
        return null;
    }
}
