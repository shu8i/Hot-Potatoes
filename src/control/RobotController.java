package control;

import model.Coordinate;
import model.Direction;
import model.Robot;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class RobotController {

    private Robot robot;

    /**
     * Creates a new Robot Controller
     * @param robot the robot
     */
    public RobotController(Robot robot) {
        this.robot = robot;
    }

    /**
     * Picks up a potato from the current block
     * @return the robot controller
     */
    public RobotController pickup() {
        //TODO implement
        return this;
    }

    /**
     * Drops a potato onto the current block
     * @return the robot controller
     */
    public RobotController drop() {
        //TODO implement
        return this;
    }

    /**
     * Moves the robot by one spot towards the direction its facing
     * @return the robot controller
     */
    public RobotController move() {
        //TODO implement
        return this;
    }

    /**
     * Checks whether a given move is valid
     * @param coordinate the coordinates that the robot would want to move
     * @return whether the move would be valid
     */
    public boolean isMoveValid(Coordinate coordinate) {
        //TODO implement
        return true;
    }

    /**
     * Changes the direction of the robot
     * @param direction the new direction
     * @return the robot controller
     */
    public RobotController changeDirection(Direction direction) {
        //TODO implement
        return this;
    }

}
