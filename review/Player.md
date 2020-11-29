| method signature | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Player() | The default constructor | none | Player(String newName) | none | 2
| Player(String newName) | Constructor specifying name | inventory, name, hp | none | none | 4
| getName() | Getter for name | name | none | none | 2
| setName(String) | Setter for name | name | none | none | 2
| getXyLocation() | Getter for xyLocation | xyLocation | none | none | 2
| setXyLocation(Point) | Setter for xyLocation | xyLocation | none | none | 2
| getXCoordinate() | Gets the X coordinate of the player as int | xyLocation | none | none | 2
| getYCoordinate() | Gets the Y coordinate of the player as int | xyLocation | none | none | 2
| movePlayerBy(int dx, int dy) | Moves the player by a specified amount in X and Y | xyLocation | translate() | none | 2 
| getHp() | Gets the player's HP | hp | none | none | 2
| setHp(int) | Sets the player's HP | hp | none | none | 2
| addHp(int) | Adds or subtracts to the player's HP | hp | none | none | 8
| boolean playerIsDead() | Detects if player has no HP left | hp | none | none | 4
| getCurrentRoom() | Returns the room the player is in | currentRoom | none | none | 2
| setCurrentRoom(Room) | Sets the room the player is in | currentRoom | none | none | 2
| getInventory() | Gets the player's inventory object | inventory | none | none | 2
| setInventory() | Sets the player's inventory object | inventory | none | none | 2
| collectItem(Item toAdd) | Collects an item from a room | inventory, currentRoom | none | Room.removeItem(), Inventory.add() | 3
| getFromInventory(int id) | Returns a copy of an item in the player inventory | inventory | none | none | 2
| removeFromInventory(int id) | Returns an item that was just removed from inventory | inventory | none | none | 2 