package control;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import model.Backend;
import model.Code;
import model.Grid;
import model.User;
import view.LoginPanel;
import view.PlayPanel;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Controller {

	private User user;
	/**
	 * Controller for the users
	 */
	public UserController userController;
	/**
	 * Controller for the grids
	 */
	public GridController gridController;
	/**
	 * Controller for the robot
	 */
	public RobotController robotController;
	/**
	 * Controller for the code
	 */
	public CodeController codeController;
	/**
	 * Playpanel view
	 */
	public PlayPanel playPanel;

	/**
	 * Current grid being played
	 */
	private Grid current_grid;

	private static Backend mBackend = Backend.readDatabase();

	/**
	 * @param grid
	 * @return Controller for this grid
	 */
	public Controller initRobot(Grid grid) {
		this.setCurrent_grid(grid);
		this.robotController = new RobotController(grid);
		return this;
	}

	/**
	 * @param playPanel
	 * @return Controller for the playpanel
	 */
	public Controller initPlay(PlayPanel playPanel) {
		this.playPanel = playPanel;
		return this;
	}

	/**
	 * Checks whether a user exists.
	 * 
	 * @param username
	 *            the username to be checked for
	 * @return whether the user exists or not
	 */
	public static boolean doesUserExist(String username) {
		return mBackend.getUsers().get(username) != null;
	}

	/**
	 * Checks whether a password provided by the user is correct
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return whether the password matches the user account
	 */
	public static boolean doesPasswordMatch(String username, String password) {
		return doesUserExist(username)
				&& mBackend.getUsers().get(username).getPassword()
						.equals(password);
	}

	/**
	 * Logs the user in. Assumes that the username exists and password is valid.
	 * 
	 * @param username
	 *            the username
	 */
	public void login(String username) {
		this.user = mBackend.getUsers().get(username);
		this.userController = new UserController(this.user);
		this.codeController = new CodeController(this.user, this);
		this.gridController = new GridController();
	}

	/**
	 * Saves the database.
	 */
	public static void saveDatabase() {
		mBackend.saveDatabase();
	}

	/**
	 * Logs the user out.
	 * 
	 * @param loginPanel
	 * @param currentPanel
	 * @param <T>
	 */
	public static <T extends JPanel> void logout(LoginPanel loginPanel,
			T currentPanel) {
		Controller.saveDatabase();
		currentPanel.setVisible(false);
		loginPanel.parent().setJMenuBar(null);
		loginPanel.parent().setTitle("Hot Potatoes");
		loginPanel.resetTextFields();
		loginPanel.setVisible(true);
		loginPanel.parent().pack();
		loginPanel.parent().setLocationRelativeTo(null);
	}

	/**
	 * Gets a list of users.
	 * 
	 * @return a list of users
	 */
	public static Map<String, User> getUsers() {
		return mBackend.getUsers();
	}

	/**
	 * Removes a user account from the system. Assumes that the user exists.
	 * 
	 * @param username
	 *            the username of the account that should be deleted
	 * @return the gamecontroller
	 */
	public Controller deleteUser(String username) {
		mBackend.removeUser(username);
		return this;
	}

	/**
	 * Adds a new user to the system
	 * 
	 * @param user
	 *            the user
	 * @return the gamecontroller
	 */
	public Controller addUser(User user) {
		mBackend.addUser(user);
		return this;
	}

	/**
	 * Adds a new grid to the system
	 * 
	 * @param grid
	 *            the grid
	 * @return the gamecontroller
	 */
	public Controller addGrid(Grid grid) {
		mBackend.addGrid(grid);
		return this;
	}

	/**
	 * Removes a grid from the system
	 * 
	 * @param grid
	 *            the grid to be removed
	 * @return the gamecontroller
	 */
	public Controller removeGrid(Grid grid) {
		mBackend.removeGrid(grid);
		return this;
	}

	/**
	 * Updates a grid that has been edited
	 * 
	 * @param grid
	 *            the updated grid
	 * @return the gamecontroller
	 */
	public Controller updateGrid(Grid grid) {
		mBackend.updateGrid(grid);
		return this;
	}

	/**
	 * Returns the grids in the system
	 * 
	 * @return a map of grid name -> grid
	 */
	public static Map<String, Grid> getGrids() {
		return mBackend.getGrids();
	}

	/**
	 * Checks whether there is at least one admin available in the system.
	 * 
	 * @return true/false
	 */
	public static boolean doesAdminExist() {
		for (final Map.Entry<String, User> entry : mBackend.getUsers()
				.entrySet()) {
			if (entry.getValue().isAdmin()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param gridName the name of the grid
	 * @return the maximum score of all users.
	 */
	public static Map<String, Integer> getGridScores(String gridName) {
		Map<String, User> users = mBackend.getUsers();
		Map<String, Grid> grids = mBackend.getGrids();
		Map<String, Integer> scores = new HashMap<String, Integer>();
		for (final Map.Entry<String, User> userEntry : users.entrySet()) {
			Map<String, Integer> gridsPlayed = userEntry.getValue()
					.getGridsPlayed();
			for (final Map.Entry<String, Integer> gridEntry : gridsPlayed
					.entrySet()) {
				if (gridEntry.getKey().equals(gridName)) {
					scores.put(userEntry.getKey(), 100 * gridEntry.getValue()
							/ grids.get(gridName).numPotatoes());
				}
			}
		}
		return scores;
	}

	/**
	 * @return Grid // the current grid being played.
	 */
	public Grid getCurrent_grid() {
		return this.current_grid;
	}

	/**
	 * @param current_grid
	 */
	public void setCurrent_grid(Grid current_grid) {
		this.current_grid = current_grid;
	}

	/**
	 * When a grid is selected checks if the user had code before and if it did
	 * then loads it, otherwise is set to blanck code.
	 */
	public void setCode() {
		this.codeController.setCode(this.user.getCodePlayedinGrid(this
				.getCurrent_grid()));
		if (this.codeController.getCode() == null) {
			this.codeController.setCode(new Code());
		}
	}
}
