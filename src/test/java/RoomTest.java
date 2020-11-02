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
import rogue.Room;

public class RoomTest {

	Room room;

	@Before
	public void setUp(){
		room = new Room();
	}

	@After
	public void tearDown(){
		//Do stuff here
	}


	@Test
	public void testWidthGetAndSet(){
		int target = 20;
		room.setWidth(target);
		Assert.assertEquals(target, room.getWidth());
	}

	@Test
	public void testIDGetAndSet(){
		int target = 1;
		room.setId(1);
		Assert.assertEquals(target, room.getId());
	}


	//Assert.assertTrue(room.getWidth() == target);
	//	Assert.assertFalse(false);
	//	Assert.assertNotNull(room);
	//	Assert.assertNull(null);
}
