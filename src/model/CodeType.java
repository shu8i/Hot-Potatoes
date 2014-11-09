package src.model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class CodeType {
	
    private int type;
    private int color;
    private int format;

    /**
     * Checks whether this code type is deletable or not.
     * @return whether this code type is deletable
     */
    public boolean deleteTable() {
        return true;
    }

    /**
     * Returns the type of this codeblock
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the type of this codeblock
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Gets the color of the codeblock
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets the color of the codeblock
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Gets the format of the codeblock
     * @return the format
     */
    public int getFormat() {
        return format;
    }

    /**
     * Sets the format of the codeblock
     * @param format the format to set
     */
    public void setFormat(int format) {
        this.format = format;
    }
	
}
