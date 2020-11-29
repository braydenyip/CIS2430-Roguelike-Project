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
| setId() | Setter for id | id | none | none | 2
| setRogue() | Setter for rogue | rogue | none | Rogue | 2
| getRoomItems() | Getter for roomItems | roomItems | none | ArrayList | 2
| setRoomItems() | Add items to the room's list based on eligibility | roomItems, id | none | Item (.getCurrentRoomId()) | 5
| addItem() | Adds an item to a room | roomItems, rogue, id | itemOnTile(), playerOnTile(), positionIsInvalid() | Item, Rogue, ArrayList | 10
| removeItem() | Removes an item from the room | roomItems | none | ArrayList (remove) | 2
| getPlayer() | Getter for player | player | none | Player | 2
| setPlayer() | Setter for player | player | none | Player | 2
| getDoorPosition() | Gets the position of a door based on direction | doors | getDoor | door.getPosition() | 5
| getAllDoors() | Getter for "doors" | doors | none | ArrayList<Door> | 2
| addDoor() | Adds a door to room | doors | door.connectRoom(), ArrayList.add() | this | 3
   
