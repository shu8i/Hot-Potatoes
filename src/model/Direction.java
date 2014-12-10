package model;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public enum Direction {

	UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT");

	private final String direction;

	private Direction(final String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return direction;
	}

}
