| method signature | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Item() | The default Item constructor | none | Item(4 Parameter see below) | none | 2
| Item(String newType) | Item constructor specifying type | none | setters for id, type, name, location | none | 5
| Item(int id, String newName, String newType, Point) | Item constructor specifying basic properties | none | all setters | none | 8
| getId() | Getter for id | id | none | none | 2
| setId(int) | Setter for id | id | none | none | 2
| getName() | Getter for name | name | none | none | 2
| setName(String) | Setter for name | name | none | none | 2
| getType() | Getter for type | type | none | none | 2
| setType(String) | Setter for type | type | none | none | 2
| getDisplayCharacter() | Getter for displayCharacter | displayCharacter | none | none | 2
| setDisplayCharacter(String) | Setter for displayCharacter | displayCharacter | none | none | 2
| getDescription() | Getter for description | description | none | none | 2
| setDescription(String) | Setter for description | description | none | none | 2
| getXyLocation() | Getter for xyLocation | xyLocation | none | none | 2
| setXyLocation(Point newXyLocation) | Setter for xyLocation | xyLocation | none | none | 2
| int getXCoordinate() | Returns the x coordinate as an int | xyLocation | none | xyLocation, a Point | 2
| int getYCoordinate() | Returns the y coordinate as an int | xyLocation | none | "" | 2
| moveItemBy(int dx, int dy) | Translates an item by a given amount in x and y | xyLocation | none | none | 2
| getCurrentRoomId() | Returns the id of the room in which the item is located | currentRoomId | none | none | 2
| setCurrentRoomId() | Sets the id of the room in which the item is located | currentRoomId | none | none | 2
| boolean openSpotExists(Room room) | Attempts to find an open spot in the room to place an item | none | setXyLocation | Room, checking for locational validity | 10
| String getPickupMessage() | Returns the message to print when the item is picked up | name | none | none | 4