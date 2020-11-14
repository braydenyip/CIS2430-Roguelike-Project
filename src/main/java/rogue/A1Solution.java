package rogue;

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
        Rogue game = new Rogue(theParser); // This parser will be "full"
        game.setPlayer(thePlayer);
        game.initializeGameState();
        System.out.println("Welcome " + game.getPlayer().getName());
        System.out.println(game.getNextDisplay());
    }


}
