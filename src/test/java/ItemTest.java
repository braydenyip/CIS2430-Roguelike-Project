package rogue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.awt.Point;

public class ItemTest {
  private Item item;
  private static final int ID_CONST = 11;

  /**
  * Sets up an item.
  */
  @Before
  public void setUp() {
    item = new Item(6, "Golden Apple", "Consumable", new Point(3, 3));


  }

  /**
  *
  */
  @After
  public void tearDown() {

  }
  /**
  * Four param constructor test.
  */
  @Test
  public void paramItemConstTest() {
    Assert.assertTrue(item.getId() == 6);
    Assert.assertTrue(item.getName().equals("Golden Apple"));
    Assert.assertEquals(item.getXyLocation(), new Point(3, 3));
    Assert.assertTrue(item.getType().equals("Consumable"));
  }
  /**
  * Tests item ID methods.
  */
  @Test
  public void testIDGetAndSet() {
    item.setId(ID_CONST);
    Assert.assertEquals(item.getId(), ID_CONST);
  }

  /**
  * Tests setting the location of an item and moving it.
  */
  @Test
  public void testXyLocationAndMove() {
    Point pt = new Point(7, 5);
    item.setXyLocation(pt);
    Assert.assertEquals(pt, item.getXyLocation());
    Assert.assertEquals(pt.getX(), item.getXyLocation().getX(), 0.1);
    item.moveItemBy(-3, 2);
    Assert.assertEquals(item.getXCoordinate(), 4);
    Assert.assertEquals(item.getYCoordinate(), 7);
  }

  /**
  * Tests description methods.
  */
  @Test
  public void testDescriptionGetAndSet() {
    item.setDescription("You feel a lot better.");
    Assert.assertEquals(item.getDescription(), "You feel a lot better.");
  }

  /**
  * Tests pickup message.
  */
  @Test
  public void testPickupMessage() {
    Assert.assertEquals(item.getPickupMessage(), "Picked up Golden Apple.");
  }

  /**
  * Tests room id setting.
  */
  @Test
  public void testRoomId() {
    Room room;
    room = new Room();
    room.setId(8);
    item.setCurrentRoomId(room.getId());
    Assert.assertEquals(room.getId(), item.getCurrentRoomId());
  }
}
