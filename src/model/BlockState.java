package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public enum BlockState
{
    UNBLOCKED("UNBLOCKED"),
    BLOCKED("BLOCKED"),
    START("START"),
    POTATO("POTATO"),
    TARGET("TARGET"),
    ROBOT("ROBOT");

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

