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

public class RoomTest {

	private Room room;
	private static final int TARGET = 20;
	/**
	* initializes a Room.
	*/
	@Before
	public void setUp() {
		room = new Room();
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


	//Assert.assertTrue(room.getWidth() == target);
	//	Assert.assertFalse(false);
	//	Assert.assertNotNull(room);
	//	Assert.assertNull(null);
}
