package model;

import static model.BlockState.HOME;
import static model.BlockState.KAREL;
import static model.BlockState.POTATO;
import static model.BlockState.WALL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import util.ExtendedAscii;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Grid implements Serializable {

	private int gridSize;
	private Block[][] grid;
	private Block karel, home;
	private List<Block> potatoes;
	private String name;
	private int numPotatoes = 0;

	/**
	 * Creates a new grid
	 * 
	 * @param gridSize
	 *            the size of the grid
	 */
	public Grid(int gridSize) {
		this.gridSize = gridSize;
		this.grid = new Block[gridSize][gridSize];
		this.potatoes = new ArrayList<Block>();
		initializeEmptyGrid();
	}

	public Grid(Grid grid) {
		this.gridSize = grid.gridSize;
		this.grid = new Block[this.gridSize][this.gridSize];
		this.potatoes = new ArrayList<Block>();
		copyGrid(grid);
		setName(grid.getName());
		numPotatoes = potatoes.size();
	}

	private void copyGrid(Grid grid) {
		for (int i = 0; i < this.gridSize; i++) {
			for (int j = 0; j < this.gridSize; j++) {
				this.grid[i][j] = new Block(j + 1, this.gridSize - i);
				Block block = this.getBlock(this.grid[i][j].coordinates());
				if (grid.getBlock(block.coordinates()).is(WALL)) {
					block.add(WALL);
					addWall(block.coordinates());
				} else if (grid.getBlock(block.coordinates()).is(POTATO)) {
					block.add(POTATO);
					this.potatoes.add(block);
				} else if (grid.getBlock(block.coordinates()).is(KAREL)) {
					block.add(KAREL);
					setKarel(block.coordinates());
				} else if (grid.getBlock(block.coordinates()).is(HOME)) {
					block.add(HOME);
					setHome(block.coordinates());
				}
			}
		}
	}

	/**
	 * Returns the grid size
	 * 
	 * @return the grid size
	 */
	public int getSize() {
		return this.gridSize;
	}

	/**
	 * Sets the name of this grid
	 * 
	 * @param name
	 *            the name of this grid
	 * @return the grid
	 */
	public Grid setName(String name) {
		this.name = name.equals("") ? null : name;
		return this;
	}

	/**
	 * Gets the name of the grid
	 * 
	 * @return the grid name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the starting position of the robot
	 * 
	 * @return the starting position of the robot
	 */
	public Block getKarel() {
		return this.karel;
	}

	/**
	 * Gets the target position of the robot
	 * 
	 * @return the target position of the robot
	 */
	public Block getHome() {
		return this.home;
	}

	/**
	 * Marks a block on the grid as being a wall
	 * 
	 * @param coordinate
	 *            the coordinate of the block on the grid
	 * @return the grid
	 */
	public Grid addWall(Coordinate coordinate) {
		getBlock(coordinate).add(WALL);
		return this;
	}

	/**
	 * Removes a wall from a block on the grid
	 * 
	 * @param coordinate
	 *            the coordinate of the block on the grid
	 * @return the grid
	 */
	public Grid removeWall(Coordinate coordinate) {
		getBlock(coordinate).remove(WALL);
		return this;
	}

	/**
	 * Sets the starting position of the robot on the grid
	 * 
	 * @param coordinate
	 *            the coordinates of the block where the robot starts
	 * @return the grid
	 */
	public Grid setKarel(Coordinate coordinate) {
		this.karel = getBlock(coordinate);
		this.karel.add(KAREL);
		return this;
	}

	/**
	 * Sets the target position of the robot
	 * 
	 * @param coordinate
	 *            the coordinates of home
	 * @return the grid
	 */
	public Grid setHome(Coordinate coordinate) {
		this.home = getBlock(coordinate);
		this.home.add(HOME);
		return this;
	}

	/**
	 * Removes karel from a given block
	 * 
	 * @return the grid
	 */
	public Grid removeKarel() {
		if (this.karel != null) {
			this.karel.remove(KAREL);
		}
		this.karel = null;
		return this;
	}

	public Grid removeHome() {
		if (this.home != null) {
			this.home.remove(HOME);
		}
		this.home = null;
		return this;
	}

	/**
	 * Given a coordinate, returns the block on the grid
	 * 
	 * @param coordinate
	 *            the coordinate of the grid
	 * @return the block, corresponding to the coordinate
	 */
	public Block getBlock(Coordinate coordinate) {
		return this.grid[this.gridSize - coordinate.getY()][coordinate.getX() - 1];
	}

	/**
	 * Marks a block on the grid as having a potato and increments the potato
	 * counter
	 * 
	 * @param coordinate
	 *            the coordinate of the potato block
	 * @return the grid
	 */
	public Grid addPotato(Coordinate coordinate) {
		getBlock(coordinate).add(POTATO);
		this.potatoes.add(getBlock(coordinate));
		this.numPotatoes++;
		return this;
	}

	public Grid addPotatoToGrid(Coordinate coordinate) {
		getBlock(coordinate).add(POTATO);
		return this;
	}

	/**
	 * Marks a block on the grid as NOT having a potato and decrements the
	 * potato counter
	 * 
	 * @param coordinate
	 *            the coordinate of the block, where a potato is being removed
	 * @return the grid
	 */
	public Grid removePotato(Coordinate coordinate) {
		getBlock(coordinate).remove(POTATO);
		this.potatoes.remove(getBlock(coordinate));
		this.numPotatoes--;
		return this;
	}

	public Grid removePotatoFromGrid(Coordinate coordinate) {
		getBlock(coordinate).remove(POTATO);
		return this;
	}

	/**
	 * Gets the number of potatoes on the grid
	 * 
	 * @return the number of potatoes on the grid
	 */
	public int numPotatoes() {
		return this.numPotatoes;
	}

	private void initializeEmptyGrid() {
		for (int i = 0; i < this.gridSize; i++) {
			for (int j = 0; j < this.gridSize; j++) {
				grid[i][j] = new Block(j + 1, this.gridSize - i);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}

		Grid grid = (Grid) o;
		return grid.getName() != null && grid.getName().equals(this.getName());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < this.grid.length; i++) {
			stringBuilder.append("\t|");
			for (int j = 0; j < this.grid[0].length; j++) {
				Block block = this.grid[i][j];

				if (block.is(KAREL)) {
					stringBuilder.append("K");
				} else if (block.is(HOME)) {
					stringBuilder.append("H");
				} else if (block.is(WALL)) {
					stringBuilder.append(ExtendedAscii.getAscii(177));
				} else if (block.is(POTATO)) {
					stringBuilder.append("P");
				} else {
					stringBuilder.append(" ");
				}

				stringBuilder.append("|");

			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

}
