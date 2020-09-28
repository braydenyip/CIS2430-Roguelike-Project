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
    private ArrayList<Room> rooms;
    private ArrayList<Item> items;

    public void setPlayer(Player thePlayer){
        this.thePlayer = thePlayer;
    }


    public void setSymbols(String filename){
        
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

    }
    public String displayAll(){
        //creates a string that displays all the rooms in the dungeon
        return null;
    }
}