package model;

import util.Constants;

import java.awt.*;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class CodeType {
	
    public enum Type {IF, WHILE, ELSE, END, ACTION}
    private Type type;
    private Color color;

    /**
     * Sets the type of this codeblock
     * @param type the type to set
     * @return the code type
     */
    public CodeType(Type type) {
        this.type = type;

        switch(this.type) {
            case WHILE:
            case IF:
            case ELSE:
            case END:
                this.color = Constants.CONDITIONALS;
                break;
            case ACTION:
                this.color = Constants.ACTIONS;
                break;
            default: break;
        }
    }

    /**
     * Returns the type of this codeblock
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the color of the codeblock
     * @return the color
     */
    public Color getColor() {
        return color;
    }
	
}
