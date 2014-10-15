package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Coordinate {

    private int x-axis,
    private int y-axis;
    private Grid grid;
    
    /**
     * Creates a new coordinate.
     * @param graph the coordinate belongs to and the 
     * @param x coordinate element
     * @param y coordinate element
     */
    public Coordinate(Grid grid, int x, int y)
    {
        this.grid = grid;
        this.x-axis = x;
        this.y-axis = y;
    }
    
    /**
     * Changes the current coordinate
     * @param the new x coordinate element
     * @param the new y coordinate element
     * @return this coordiante
     */
    public Coordinate setCoordinate(int x, int y)
    {
        this.x-axis = x;
        this.y-axis = y;
        return this;
    }
    
    /**
     * Gets this coordinate's x element
     * @return coordinate's x element
     */
    public String getCoordinateX()
    {
        return this.x-axis;
    }
    
    /**
     * Gets this coordinate's y element
     * @return coordinate's y element
     */
    public String getCoordinateY()
    {
        return this.y-axis;
    }
}
