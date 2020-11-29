| method signature | responsibility | instance vars used | other class methods called | objects used with method calls | lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| Inventory() | Default constructor | inventory, capacity | none | HashMap<>() | 3
| Inventory(int) | Constructor with size | capacity | this() | this | 3
| Map getInventory() | Getter for inventory | inventory | none | none | 2
| setInventory(Map) | Setter for inventory | inventory | none | none | 2
| int getCapacity() | Getter for capacity | capacity | none | none | 2
| boolean setCapacity(int) | Conditional setter for capacity | capacity | getNumberOfItems() | Map | 5
| boolean add(Item) | Adds an item if possible | capacity, inventory | getNumberOfItems() | Map.put(), Item.getId() | 5
| Item remove(int) | Removes item by id | inventory | none | Map.remove() | 2
| Item remove(String) | Removes item by name | inventory | none | Map.values(), item.getName() | 5
| Item get(int) | Gets item without removing it | inventory | none | Map.get() | 2
| void trash(int) | Removes item without a return value | inventory | remove() | Item (no method calls) | 2
| void trashAll() | Trashes all items | inventory | none | Map.clear() | 2