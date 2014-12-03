package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Block implements Serializable {

    private List<BlockState> blockState;
    private Coordinate coordinate;

    /**
     * Creates a new instance of a block
     * @param x the x coordinates of the block
     * @param y the y coordinates of the block
     */
    public Block(int x, int y) {
        this.blockState = new ArrayList<BlockState>();
        this.coordinate = new Coordinate(x, y);
    }

    /**
     * Checks whether a particular blockstate is contained within this grid
     * @param state the blockstate that should be checked for
     * @return whether this block contains the blockstate specified
     */
    public boolean is(BlockState state) {
        return this.blockState.contains(state);
    }

    /**
     * Gets the coordinates of the grid
     * @return the coordinates of this block
     */
    public Coordinate coordinates() {
        return this.coordinate;
    }

    /**
     * Adds a blockstate to this grid
     * @param state the blockstate that should be added
     * @return the block
     */
    public Block add(BlockState state) {
        if (!is(state))
            this.blockState.add(state);
        return this;
    }

    /**
     * Removes a blockstate from this block
     * @param state the blockstate that should be removed
     * @return the block
     */
    public Block remove(BlockState state) {
        this.blockState.remove(state);
        return this;
    }

    /**
     * Returns whether this block is empty or not
     * @return whether this block is empty or not
     */
    public boolean isEmpty() {
        return this.blockState.size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Block block = (Block) o;
        return block.coordinates() != null &&
                block.coordinates().equals(this.coordinates());
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        for(final BlockState state : this.blockState) {
            stringBuffer.append(state + ", ");
        }

        return stringBuffer.substring(0, stringBuffer.length()-2);
    }

    /**
     * Comparators for futue use.
     */
    public static class Comparators {
        //TODO implement
    }

}