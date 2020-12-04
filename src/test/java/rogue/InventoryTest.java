package rogue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;

public class InventoryTest {
    private Inventory inv1;
    private Item sampleItem1;
    private Item sampleItem2;
    private Item sampleItem3;
    private Item sampleItem4;
    private Item sampleItem5;
    @Before
    public void setUp() {
        inv1 = new Inventory(4);
        sampleItem1 = new Item(1, "Apple", "Food", new Point(1, 1));
        sampleItem2 = new Item(2, "Orange", "Food", new Point(1, 2));
        sampleItem3 = new Item(3, "Tomato", "Food", new Point(2, 4));
        sampleItem4 = new Item(4, "Chainmail Leggings", "Armour", new Point(3, 3));
        sampleItem5 = new Item(5, "Boots", "Clothing", new Point(4, 1));
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCap() {
        Assert.assertEquals(inv1.getCapacity(), 4);
        inv1.setCapacity(15);
        Assert.assertEquals(inv1.getCapacity(), 15);
        inv1.setCapacity(4);
    }

    @Test
    public void testAddToInv() {
        inv1.add(sampleItem1);
        Assert.assertEquals(inv1.getNumberOfItems(), 1);
    }

    @Test
    public void testItemRetrieval() {
        inv1.add(sampleItem1);
        Item retItem = inv1.get(1);
        Assert.assertEquals("Apple", retItem.getName());
        Assert.assertEquals("Food", retItem.getType());
        Assert.assertEquals(inv1.getNumberOfItems(), 1);
    }

    @Test
    public void testItemRemoval() {
        inv1.add(sampleItem1);
        Item retItem = inv1.remove(1);
        Assert.assertEquals("Apple", retItem.getName());
        Assert.assertEquals(inv1.getNumberOfItems(), 0);
        inv1.add(sampleItem3);
        inv1.add(sampleItem2);
        retItem = inv1.remove("Tomato");
        Assert.assertEquals(3, retItem.getId());
        Assert.assertTrue(inv1.remove("Apple") == null);
    }

    @Test
    public void testOverflow() {
        inv1.add(sampleItem1);
        inv1.add(sampleItem2);
        inv1.add(sampleItem3);
        inv1.add(sampleItem4);
        Assert.assertTrue(inv1.isFull());
        Assert.assertFalse(inv1.add(sampleItem5));
    }

    @Test
    public void testGetNumItems() {
        inv1.add(sampleItem1);
        inv1.add(sampleItem2);
        inv1.add(sampleItem3);
        inv1.add(sampleItem4);
        Assert.assertEquals(4, inv1.getNumberOfItems());
    }

    @Test
    public void testTrashAll() {
        inv1.add(sampleItem1);
        inv1.add(sampleItem2);
        inv1.add(sampleItem3);
        inv1.add(sampleItem5);
        inv1.trashAll();
        inv1.add(sampleItem4);
        Assert.assertEquals(1, inv1.getNumberOfItems());
    }

}