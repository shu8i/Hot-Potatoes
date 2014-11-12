package model;

import java.io.Serializable;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public enum BlockState implements Serializable {
    WALL("WALL"),
    POTATO("POTATO"),
    HOME("HOME"),
    KAREL("KAREL");

    private final String block_state;

    private BlockState(final String block_state)
    {
        this.block_state = block_state;
    }

    @Override
    public String toString()
    {
        return block_state;
    }
}

