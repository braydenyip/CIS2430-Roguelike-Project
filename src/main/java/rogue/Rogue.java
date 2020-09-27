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
    private ArrayList<Room> Rooms;
    private ArrayList<Item> Items;


    public setPlayer(Player thePlayer){
        this.thePlayer = thePlayer;
    }


    public void setSymbols(String filename){
        // parse thru the file at filename which contains the symbols.
    }

    public ArrayList<Room> getRooms(){
        return Rooms;
    }

    public ArrayList<Item> getItems(){
        return Items;
    }
    public Player getPlayer(){
        return thePlayer;
    }

    public void createRooms(String filename){
        // parse thru the file which contains the room layouts
        JSONParser parser = new JSONParser();
        
    }
    public String displayAll(){
        //creates a string that displays all the rooms in the dungeon
    }
}