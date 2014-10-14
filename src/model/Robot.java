package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Robot {

    private Coordinate coordinate;
    private Direction direction;
    private int backpackSize;

    /**
     * Creates a new robot.
     * @param coordinate the initial coordinates of the robot
     */
    public Robot(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.backpackSize = 0;
    }

    /**
     * Moves the robot one block in the direction its facing
     * @return the robot
     */
    public Robot move() {
        //TODO implement
        return this;
    }

    /**
     * Picks (if available) a potato from its current block
     * @return the robot
     */
    public Robot pickup() {
        //TODO implement
        this.backpackSize++;
        return this;
    }

    /**
     * Drops (if available in backpack) a potato onto the current block
     * @return the robot
     */
    public Robot drop() {
        //TODO implement
        this.backpackSize--;
        return this;
    }

    /**
     * Changes the direction of the robot
     * @param direction the new direction
     * @return the robot
     */
    public Robot setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    /**
     * Gets the robot's current coordinates
     * @return the robot's coordinates
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * Gets the current direction the robot is facing
     * @return the robot's direction
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Gets the size of the robot's backpack
     * @return the robot's backpack size
     */
    public int getBackpackSize() {
        return this.backpackSize;
    }

    /**
     * Sets new coordinates for the robot (
     * @param coordinate the new coordinates
     * @return the robot
     */
    private Robot setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }

}
