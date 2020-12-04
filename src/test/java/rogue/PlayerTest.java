package rogue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class PlayerTest {

    Player testPlayer;
    @Before
    public void setUp() {
        testPlayer = new Player("Hero");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testConstructor() {
        Assert.assertEquals(24, testPlayer.getInventory().getCapacity());
        Assert.assertEquals(12, testPlayer.getWearables().getCapacity());
        Assert.assertEquals("Hero", testPlayer.getName());
        Assert.assertEquals(100, testPlayer.getHp());
        Assert.assertEquals(0 , testPlayer.getAp());
    }

    @Test
    public void testWearing() {
        Clothing shirt = new Clothing();
        shirt.setId(3);
        shirt.setName("Shirt");
        shirt.setType("Clothing");
        shirt.setArmourPoints(20);
        Inventory testInv = new Inventory();
        testInv.add(shirt);
        testPlayer.setInventory(testInv);
        testPlayer.wearItem(shirt);
        Assert.assertEquals(20, testPlayer.getAp());
        Assert.assertEquals(0, testPlayer.getInventory().getNumberOfItems());
    }

    @Test
    public void testHealing() {
        Food food = new Food();
        food.setId(5);
        food.setName("Gold Apple");
        food.setType("Food");
        food.setDescription("A yummy apple");
        food.setHeal(33);
        Inventory testInv = new Inventory();
        testInv.add(food);
        testPlayer.setInventory(testInv);
        Assert.assertEquals("A yummy apple", testPlayer.consumeItem(food));
        Assert.assertEquals(133, testPlayer.getHp());
        Assert.assertEquals(0, testPlayer.getInventory().getNumberOfItems());
    }
}
