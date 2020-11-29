| method signature | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Room() | Default constructor. Sets Id to -1 | none | setId() | this | 2
| getWidth() | Getter for width | width | none | none | 2
| setWidth() | Setter for width and maxWidth | width, maxWidth | none | none | 4
| getMaxWidth() | Getter for maxWidth | maxWidth | none | none | 2
| setMaxWidth() | Setter for maxWidth | maxWidth | none | none | 2
| getHeight() | Getter for height | height | none | none | 2
| setHeight() | Setter for height and maxHeight | height, maxHeight | none | none | 4
| getMaxHeight() | Getter for maxHeight | maxHeight | none | none | 2
| setMaxHeight() | Setter for maxHeight | maxHeight | none | none | 2
| getId() | Getter for id | id | none | none | 2
| setId(int) | Setter for id | id | none | none | 2
| setRogue(Rogue) | Setter for rogue | rogue | none | Rogue | 2
| getRoomItems() | Getter for roomItems | roomItems | none | ArrayList | 2
| setRoomItems(ArrayList<Item>) | Add items to the room's list based on eligibility | roomItems, id | none | Item (.getCurrentRoomId()) | 5
| addItem(Item) | Adds an item to a room | roomItems, rogue, id | itemOnTile(), playerOnTile(), positionIsInvalid() | Item, Rogue, ArrayList | 10
| removeItem() | Removes an item from the room | roomItems | none | ArrayList (remove) | 2
| getPlayer() | Getter for player | player | none | Player | 2
| setPlayer(Player) | Setter for player | player | none | Player | 2
| getDoorPosition() | Gets the position of a door based on direction | doors | getDoor | door.getPosition() | 5
| getAllDoors() | Getter for "doors" | doors | none | ArrayList<Door> | 2
| addDoor(Door newDoor) | Adds a door to room | doors | door.connectRoom(), ArrayList.add() | this | 3
| setSymbols() | Sets symbols for all rooms to use | rogue, defaultSymbols | none | rogue.getSymbols() | 2
| setSymbols(HashMap<String, String> newSymbols) | Sets symbols without using Rogue | defaultSymbols | none | none | 2
| updateFromRogue() | Calls two setters that use Rogue | none | setSymbols(), setPlayer() | none | 3
| isPlayerInRoom() | Checks if player is in the room | thePlayer, id | getId() | Player.getCurrentRoom() | 6
| Item itemOnTile(x, y) | Checks if an item is at the coordinates | roomItems | none | Item, Point | 6
| boolean playerOnTile(x, y) | Checks if player is at the coordinates | thePlayer | isPlayerInRoom() | thePlayer.getXCoordinate(), thePlayer.getYCoordinate() | 7
| boolean positionIsInvalid(x, y) | Checks if a position is out of bounds | height, width | none | none | 4
| updateRoomString(String roomString) | Put together a complete display string | height, maxHeight, width, maxWidth  | getDoorPosition(), addNSWallLine(), addRoomLine(), addJustSpaces() | none | 14
| addJustSpaces(String roomString) | make a padding line of spaces* | maxWidth | none | none | 5
| addNSWallLine(int doorLoc, String roomString) | write a N/S wall line to roomString | width, maxWidth | getDoorOrWall() | none | 8
| addRoomLine(int wDoorLoc, int eDoorLoc, int y, String roomString) | makes a line that could have any type of object, as well as the EW walls | width, maxWidth, defaultSymbols | playerOnTile(), itemOnTile(), getDoorOrWall() | HashMap, Item | 17
| getDoorOrWall(int, int, String) | Decides whether to display a wall or door for the room edge | defaultSymbols | none | HashMap | 7
| verifyRoom() | Verifies the room has playable characteristics | id | itemsAreValid(), playerIsValid(), doorsAreValid() | NotEnoughDoorsException | 8
| doorsAreValid() | Verifies the room is properly connected to other rooms | doors | none | Door.getConnectedRooms(), ArrayList | 8
| addRandomDoor(Room) | Adds a random door to the room | height, random | getOpenSide(), addDoor() | Random, Door | 8
| getOpenSide() | Finds an open side on which a new door can be added | doors | none | Door, ArrayList | 14
| itemsAreValid() | Determines if all the items have valid position | roomItems | positionIsInvalid | Item (coordinate getters) | 5
| playerIsValid() | Determines whether or not the player is valid, clearing items to do so | thePlayer | isPlayerInRoom(), itemOnTile(), positionIsInvalid() | Player, Item, this | 8
