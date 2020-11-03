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
        RogueParser theParser = new RogueParser(configurationFileLocation);

        Player thePlayer = new Player("Brayden");
        Rogue game = new Rogue(theParser, thePlayer); // This parser will be "full"

        System.out.println("Welcome " + game.getPlayer().getName());
        game.setPlayer(thePlayer);
        game.initializeGameState();
        System.out.println(game.displayAll());
    }


}
