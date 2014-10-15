package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Block
{
    private Coordinate coordinate;
    private BlockState block_state;
    private Grid grid;

    /**
     * Creates a new block.
     * @param grid the block belongs to
     * @param coordinate the block is located at
     */
    public Block(Grid grid, Coordinate coordinate)
    {
        this.grid = grid;
        this.coordinate = coordinate;
        this.block_state = null;
    }

    /**
     * Gets the block's coordinates
     * @return the block's coordinates
     */
    public Coordinate getCoordinate()
    {
        return this.coordinate;
    }

    /**
     * Sets new coordinates for the block
     * @param new coordinates for the block
     * @return the block
     */
    private Block setCoordinate(Coordinate coordinate)
    {
        this.coordinate = coordinate;
        return this;
    }

    /**
     * Changes the block state of the block
     * @param the new block state
     * @return the block
     */
    public Block setBlockState(BlockState blockstate)
    {
        this.block_state = blockstate;
        return this;
    }

    /**
     * Gets the current state of the block
     * @return the block's state
     */
    public Block getBlockState()
    {
        return this.block_state;
    }
}