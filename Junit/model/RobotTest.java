package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steven
 * 
 * Junit test for the Robot Class
 * 
 * @see Robot
 * 
 */
public class RobotTest {
    
    /**
     * Test of move method, of class Robot.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Robot instance = new Robot(new Coordinate(0,0));
        Coordinate expResult = new Coordinate(1,0);
        Coordinate result = instance.move().getCoordinate();
        assertEquals(expResult, result);
    }

    /**
     * Test of pickup method, of class Robot.
     */
    @Test
    public void testPickup() {
        System.out.println("pickup");
        Robot instance = new Robot(new Coordinate(0,0));
        boolean expResult = true;
        boolean result = instance.pickup().hasPotatoes();
        assertEquals(expResult, result);
    }

    /**
     * Test of drop method, of class Robot.
     */
    @Test
    public void testDrop() {
        System.out.println("drop");
        Robot instance = new Robot(new Coordinate(0,0));
        boolean expResult = false;
        boolean result = instance.drop().hasPotatoes();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDirection method, of class Robot.
     */
    @Test
    public void testSetDirection() {
        System.out.println("setDirection");
        Direction direction = Direction.LEFT;
        Robot instance = new Robot(new Coordinate(0,0));
        Direction expResult = Direction.LEFT;
        Direction result = instance.setDirection(direction).getDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoordinate method, of class Robot.
     */
    @Test
    public void testGetCoordinate() {
        System.out.println("getCoordinate");
        Robot instance = new Robot(new Coordinate(0,0));
        Coordinate expResult = new Coordinate(0,0);
        Coordinate result = instance.getCoordinate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDirection method, of class Robot.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        Robot instance = new Robot(new Coordinate(0,0));
        Direction expResult = Direction.RIGHT;
        Direction result = instance.getDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBackpackSize method, of class Robot.
     */
    @Test
    public void testGetBackpackSize() {
        System.out.println("getBackpackSize");
        Robot instance = new Robot(new Coordinate(0,0));
        int expResult = 0;
        int result = instance.getBackpackSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNextCoordinate method, of class Robot.
     */
    @Test
    public void testGetNextCoordinate() {
        System.out.println("getNextCoordinate");
        Robot instance = new Robot(new Coordinate(0,0));
        Coordinate expResult = new Coordinate(1,0);
        Coordinate result = instance.getNextCoordinate();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasPotatoes method, of class Robot.
     */
    @Test
    public void testHasPotatoes() {
        System.out.println("hasPotatoes");
        Robot instance = new Robot(new Coordinate(0,0));
        boolean expResult = false;
        boolean result = instance.hasPotatoes();
        assertEquals(expResult, result);
    }
    
}
