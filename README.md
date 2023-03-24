**CIS2430 Final Assignment (A3) -- F20**

Brayden Yip
ID# {REDACTED FOR PRIVACY}

I attest that the work that I have submitted for this assignment was either a) provided to me by the instructor (the skeleton code)
or b) written completely on my own as an original piece of work for this course.

- https://github.com/fangyidong/json-simple <= The JSONSimple library use to parse the JSON files.

How to run the program:

- Ensure that you have a correctly formatted "fileLocations.json" file available, and that two more JSON files with names specified in the "fileLocations" file also exist.
- In the main folder, type:
`gradle build` , then
`java -jar build/libs/A3.jar`

- This will show the rooms as defined in the "Rooms" file, with each room numbered by the id tag.
- The adventurers name will default to "Brayden".
- The player can be moved around with the WASD or arrow keys.
- The player can view their inventory with the "i" key, although this only shows debug info right now.
- The player can walk through doors, and will be spawned at the opposite door if one exists, otherwise they will spawn in a corner.
- Pressing "q" at any time will exit the application.


File Configuration

The "fileLocations.json" file is a fixed name that must not be changed. In this file, you should include two objects:
- One object entitled "Rooms" that specifies the name of the file containing room data
- One object entitled "Symbols" that specifies which ASCII character represents which map element (e.g. the player, items, walls)

The "Rooms" file will contain data pertaining to the layout of the rooms.
- The "height" and "width" objects specify an positive integer number which is are the heights and widths of the room.
- Note that the height and width of the room will be 2 less than specified, as walls will take up the outermost tiles.
    - Therefore, the height and width of the room must be at least 3 each. (ie a 3x3 room will have 1 floor tile, not 9)
- The "id" object specifies a **unique** identifier for the room. Room ids must not be duplicated.
- The "start" object is a boolean (either true or false) that specifies whether the player starts in the room or not.
    - Please only put the player in one room to start
    - The player will appear (approximately) in the middle of the starting room.
    - The player will override the display of items on a tile (it should be assumed that the player would instantly pick up this item)
- The "doors" object contains a list of JSON objects which specify the relative locations of each door in the room
- The "loot" object contains a list of JSON objects which specify the x and y coordinates of items on the floor of the room.
    - "x" and "y" are self explanatory, and "id" is the item identifier of the item, which is specified lower down on the file.

The "Symbols" file contains the ASCII characters used to display every object in the game
- This file is pretty self explanatory.
- Each symbol must be only **one** character large, otherwise the room will break
- Do not change the names of the symbols, they are hardcoded.


Enjoy!
