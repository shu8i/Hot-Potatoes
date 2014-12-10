package control;

import model.Grid;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class GridController
{
	   public Grid copy(Grid grid)
	    {
	        return new Grid(grid);
	    }
}