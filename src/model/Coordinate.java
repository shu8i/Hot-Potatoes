package model;

import java.io.Serializable;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Coordinate implements Serializable {

    private int x, y;

    /**
     * Creates a new Coordinate object
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate
     * @return the x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the y coordinate
     * @return the y coordinate
     */
    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Coordinate coordinate = (Coordinate) o;
        return coordinate.getX() == this.getX() &&
                coordinate.getY() == this.getY();
    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }
}
