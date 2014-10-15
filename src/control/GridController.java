package control;

import model.Grid;
import model.Block;
import model.Coordinate;
import model.BlockState;
import model.Robot;
import model.Direction;
import view.GridView;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class GridController
{
    private Grid grid;
    
    /**
     * Creates a new grid controller
     * @param the new grid that will be used to play
     */
    public GridController(Grid grid)
    {
        this.grid = grid;
    }
    
    /**
     * Updates the grid controller
     * @return the same grid controller
     */
    public GridController update()
    {
        //TODO implement an update in the grid controller
        return;
    }
}