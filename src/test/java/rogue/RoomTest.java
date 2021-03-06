/**
  * Place this in the src/test/java/rogue/ directory
  * Naming convention is [class being tested]Test.java. For example this is called RoomTest.java
  * Run all tests using gradle test
  * View output in terminal, or fancy html (found uder build/reports/tests/test/index.html)
*/
package rogue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.HashMap;

public class RoomTest {

	private Room room;
	private static final int TARGET = 10;
	private static final int POSITION = 4;
	private static final int X1 = 3;
	private static final int Y1 = 3;
	private HashMap<String, String> defaultSymbols;
	/**
	* initializes a Room.
	*/
	@Before
	public void setUp() {
		room = new Room();
		defaultSymbols = new HashMap<>();
		defaultSymbols.put("NS_WALL", "-");
		defaultSymbols.put("EW_WALL", "|");
		defaultSymbols.put("FLOOR", ".");
		defaultSymbols.put("PLAYER", "@");
		defaultSymbols.put("DOOR", "+");
		room.setId(1);
		room.setSymbols(defaultSymbols);
	}

	/**
	* Tears down.
	*/
	@After
	public void tearDown() {
		//Do stuff here
	}

	/**
	* Tests the width methods.
	*/
	@Test
	public void testWidthGetAndSet() {
		room.setWidth(TARGET);
		Assert.assertEquals(TARGET, room.getWidth());
	}

	/**
	* Tests the id methods.
	*/
	@Test
	public void testIDGetAndSet() {
		room.setId(TARGET);
		Assert.assertEquals(TARGET, room.getId());
	}

	/**
	* Tests the height methods.
	*/
	@Test
	public void testHeightGetAndSet() {
		room.setHeight(TARGET);
		Assert.assertEquals(room.getHeight(), TARGET);
	}

	/**
	* Tests Door addition.
	*/
	@Test
	public void testAddDoor() {
		Door newDoor = new Door();
		newDoor.setPosition(POSITION);
		newDoor.setDirection("E");
		room.addDoor(newDoor);
		newDoor.connectRoom(new Room());
		Assert.assertTrue(newDoor.getOtherRoom(room).getId() == -1); // should be default
		Assert.assertTrue(room.getDoorPosition("S") == -1); // should return 4
		Assert.assertTrue(room.getDoorPosition("E") == POSITION); // returns position
	}

	/**
	* Tests display method.
	*/
	@Test
	public void testRoomUpdate() {
		room.setWidth(X1);
		room.setHeight(Y1);
		room.setMaxWidth(X1);
		room.setMaxHeight(Y1);
		String str = "";
		str = room.updateRoomString();
		// 3x3 room test; note the spaces!
		Assert.assertEquals(room.getMaxWidth(), 3);
		Assert.assertEquals(room.getMaxHeight(), 3);
		Assert.assertTrue(str.equals("---\n |.|\n ---\n"));
	}

 /**
 * Tests maximum methods.
 */
 @Test
 public void testMaxHeightAndWidth() {
	 room.setMaxWidth(0);
	 room.setMaxHeight(0);
	 room.setHeight(2);
	 room.setWidth(4);
	 room.setHeight(20);
	 room.setWidth(8);
	 room.setWidth(2);
	 room.setHeight(5);
	 Assert.assertEquals(room.getMaxWidth(), 8);
	 Assert.assertEquals(room.getMaxHeight(), 20);
 }

}
