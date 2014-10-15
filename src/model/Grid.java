package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Grid {

    private int gridId;
    private int grid_size;
    private Block[] blocks;
    private Coordinate[] coordinates;
    
    /**
     * Creates a new grid
     * @param the size of the grid
     */
    public Grid(int gridsize)
    {
        this.grid_size = gridsize;
        this.gridId = 123; //TODO create a randomized grid id
        this.blocks = new Block[gridsize];
        this.coordinates = new Coordinate[gridsize];
    }
    
    /**
     * Builds the grid
     * @param the grid you want to build
     * @return the grid you built
     */
    public build(Grid grid)
    {
        //TODO implement
        return this;
    }
    
    /**
     * Sets the block state of the block to start
     * @param the block that will become the start
     * @return the start block
     */
    public Block setStart(Block start)
    {
        this.block_state = "START";
        return this;
    }
    
    /**
     * Gets the starting block
     * @param the grid you want the starting block for
     * @return the start block
     */
    public Grid getStart(Grid grid)
    {
        Block start;
        //TODO find the starting block
        return start;
    }
    
    /**
     * Sets the block state of the block to target
     * @param the block that will become the target
     * @return the target block
     */
    public Block setTarget(Block target)
    {
        this.block_state = "TARGET";
        return this;
    }
    
    /**
     * Gets the target block
     * @param the grid you want the target block for
     * @return the target block
     */
    public Grid getTarget(Grid grid)
    {
        Block target;
        //TODO find the target block
        return target;
    }
    
    /**
     * Puts block in the grid
     * @param the block to be put in the grid
     * @return grid
     */
    public Grid setBlock(Block new_block)
    {
        //TODO find the spot in the grid's block array that the new block belongs in
        return this;
    }
    
    /**
     * Gets the wanted block in the grid
     * @param the grid the block is in
     * @return the wanted block
     */
    public Block getBlock(Grid grid)
    {
        Block wanted_block;
        //TODO find the wanted block
        return wanted_block;
    }
    
    /**
     * Gets the grid size
     * @return the grid's size
     */
    public Grid getGridSize()
    {
        return this.grid_size;
    }
    
    /**
     * Sets the grid size
     * @param the new grid size
     * @return the grid
     */
    private Grid setGridSize(int new_size)
    {
        this.grid_size = new_size;
        return this;
    }
    
    /**
     * Gets the potato count in the grid
     * @return the amount of potato's in the grid
     */
    public int potatoCount()
    {
        int potato_count = 0;
        //TODO count the potatos
        return potato_count;
    }
}


    