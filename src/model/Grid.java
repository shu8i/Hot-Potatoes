package model;

import java.util.List;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Grid {

    private int gridHeight, gridWidth, gridSize;
    private Block[][] grid;
    private Block startingPosition, targetPosition;
    private int numPotatoes;

    /**
     * Creates a new grid (through the grid builder)
     * @param builder the grid builder
     */
    private Grid(Builder builder) {
        this.grid = builder.grid;
        this.startingPosition = builder.startingPosition;
        this.targetPosition = builder.targetPosition;
        //TODO init potatoes
    }

    /**
     * Gets the starting position of the robot
     * @return the starting position of the robot
     */
    public Block getStartingPosition() {
        return this.startingPosition;
    }

    /**
     * Gets the target position of the robot
     * @return the target position of the robot
     */
    public Block getTargetPosition() {
        return this.targetPosition;
    }

    /**
     * Sets the starting position of the robot on the grid
     * @param block the block where the robot starts
     * @return the grid
     */
    public Grid setStartingPosition(Block block) {
        this.startingPosition = block;
        return this;
    }

    /**
     * Sets the target position of the robot
     * @param block the block where the robot should go to
     * @return the grid
     */
    public Grid setTargetPosition(Block block) {
        this.targetPosition = block;
        return this;
    }

    /**
     * Given a coordinate, returns the block on the grid
     * @param coordinate the coordinate of the grid
     * @return the block, corresponding to the coordinate
     */
    public Block getBlock(Coordinate coordinate) {
        //TODO implement
        return null;
    }

    /**
     * Gets the number of potatoes on the grid
     * @return the number of potatoes on the grid
     */
    public int numPotatoes() {
        return this.numPotatoes;
    }


    @Override
    public String toString() {
        //TODO implement
        return new String();
    }

    public static class Builder {

        private int gridHeight, gridWidth, gridSize;
        private Block[][] grid;
        private Block startingPosition, targetPosition;
        private List<Coordinate> potatoCoordinates;

        /**
         * Creates a new instance of the Grid builder
         */
        public Builder() {
            //TODO implement
        }

        /**
         * Sets the starting position of the robot on the grid
         * @param coordinate the coordinate of the block where the robot starts
         * @return the builder
         */
        public Builder startingPosition(Coordinate coordinate) {
            //TODO implement
            return this;
        }

        /**
         * Sets the target position of the robot
         * @param coordinate the coordinate of the block where the robot should go to
         * @return the builder
         */
        public Builder targetPosition(Coordinate coordinate) {
            //TODO implement
            return this;
        }

        /**
         * Puts a potato on this coordinate on the grid.
         * @param coordinate the coordinate on which a potato should be set
         * @return the builder
         */
        public Builder potatoPosition(Coordinate coordinate) {
            //TODO implement
            return this;
        }

        /**
         * Creates an instance of a Grid, using attributes of the builder
         * @return the grid
         */
        public Grid build() {
            //TODO implement
            return new Grid(this);
        }



    }

}


    