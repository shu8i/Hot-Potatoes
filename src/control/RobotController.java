package control;

import model.*;
import static model.BlockState.*;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class RobotController {

    private Robot robot;
    private Grid grid;

   
    /**
     * Creates a new Robot Controller
     * @param grid the grid
     */
    public RobotController(Grid grid) {
        this.grid = grid;
        this.robot = new Robot(grid.getKarel().coordinates());
    }

    /**
     * Picks up a potato from the current block
     * @return the robot controller
     */
    public RobotController pickup() {
        if (grid.getBlock(robot.getCoordinate()).is(POTATO))
        {
            robot.pickup();
            grid.removePotatoFromGrid(robot.getCoordinate());
        }
        return this;
    }

    /**
     * Drops a potato onto the current block
     * @return the robot controller
     */
    public RobotController drop() {
        if (robot.hasPotatoes())
        {
            robot.drop();
            grid.addPotatoToGrid(robot.getCoordinate());
        }
        return this;
    }

    /**
     * Moves the robot by one spot towards the direction its facing
     * @return the robot controller
     */
    public RobotController move() {
        if (isMoveValid(robot.getNextCoordinate()))
        {
            grid.removeKarel();
            robot.move();
            grid.setKarel(robot.getCoordinate());
        }
        return this;
    }

    /**
     * Turns the robot's direction to left
     * @return the robot controller
     */
    public RobotController turnLeft()
    {
        switch (robot.getDirection())
        {
            case UP:
                robot.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                robot.setDirection(Direction.UP);
                break;
            case DOWN:
                robot.setDirection(Direction.RIGHT);
                break;
            case LEFT:
                robot.setDirection(Direction.DOWN);
                break;
            default: break;
        }
        return this;
    }

    /**
     * Checks whether a given move is valid
     * @param coordinate the coordinates that the robot would want to move
     * @return whether the move would be valid
     */
    public boolean isMoveValid(Coordinate coordinate) {
        return coordinate.getX() > 0 && coordinate.getX() <= this.grid.getSize()
                && coordinate.getY() > 0 && coordinate.getY() <= this.grid.getSize()
                && !grid.getBlock(coordinate).is(WALL);
    }

    public Robot getRobot()
    {
        return this.robot;
    }

    public boolean facing(Direction direction)
    {
        return this.robot.getDirection().equals(direction);
    }

    public boolean dirIsFree()
    {
        return isMoveValid(this.robot.getNextCoordinate());
    }
    
    public boolean levelFinished()
        {
            return robot.getCoordinate().equals(grid.getHome().coordinates());
        }
    
        public int backpackSize()
        {
            return this.robot.getBackpackSize();
        }

}
