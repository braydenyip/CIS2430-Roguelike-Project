package rogue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class A1Solution {

    /**
    * The main executable for the game.
    * @param args This program does not use any command line arguments.
    *
    *
    */
    public static void main(String[] args) {
        // Hardcoded configuration file location/name
        String configurationFileLocation = "fileLocations.json"; // still don't touch
        String roomsFileName = new String();
        String symbolsFileName = new String();
 // reading the input file locations using the configuration file
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(configurationFileLocation));
            JSONObject configurationJSON = (JSONObject) obj;

            // Extract the Rooms value from the file to get the file location for rooms
            roomsFileName = (String) configurationJSON.get("Rooms");
            // Extract the Symbols value from the file to get the file location for symbols-map
            symbolsFileName = (String) configurationJSON.get("Symbols");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

// instantiate a new Rogue object and call methods to do the required things
        Rogue game = new Rogue();
        Player thePlayer = new Player();
        game.setPlayer(thePlayer);
        game.setSymbols(symbolsFileName);
        game.createRooms(roomsFileName);
        System.out.println("Welcome " + game.getPlayer().getName());
        System.out.println(game.displayAll());
    }


}
