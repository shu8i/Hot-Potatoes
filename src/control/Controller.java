package control;

import model.Backend;
import model.Grid;
import model.User;
import view.LoginPanel;

import javax.swing.*;
import java.util.Map;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class Controller
{

    private User user;
    public UserController userController;
    public GridController gridController;
    public CodeController codeController;
    private static Backend mBackend = Backend.readDatabase();

	/**
	 * Constructor method, initializes all fields to default values
	 */
	public Controller(){
		//TODO implement
	}
	
	/**
	 * Constructor method, initializes world with given grid
	 * @param grid - A nonempty grid that will be used to play the given world
	 */
	public Controller(Grid grid){
		//TODO implement
	}
	
	/**
	 * Runs the code that the user has inputed into the code view
	 */
	public void run(){
		
	}
	
	/**
	 * Allows the user to edit a block of code they inserted into the code view
	 */
	public void editCodeBlock(){
		
	}
	
	/**
	 * Removes a designated code block from the code view
	 */
	public void removeCodeBlock(){
		
	}
	
	/**
	 * Adds a code block to the code view
	 */
	public void addCodeBlock(){
		
	}
	
	/**
	 * Loads up previous state of level 
	 * @return true if previous save file found, false if no previous save file exists
	 */
	public boolean load(){
		return true;
	}
	
	/**
	 * Saves the user's current progress in the given level
	 * @return  true if saved successfully, false if unsuccessful
	 */
	public boolean save(){
		return true;
	}
	
	/**
	 * Updates the display of the grid, code view, action panel
	 */
	public void update(){
		
	}

    /**
     * Checks whether a user exists.
     * @param username  the username to be checked for
     * @return  whether the user exists or not
     */
    public boolean doesUserExist( String username ) {
        return mBackend.getUsers().get(username) != null;
    }

    /**
     * Checks whether a password provided by the user is correct
     * @param username the username
     * @param password the password
     * @return whether the password matches the user account
     */
    public boolean doesPasswordMatch(String username, String password) {
        return doesUserExist(username) && mBackend.getUsers().get(username).getPassword().equals(password);
    }

    /**
     * Logs the user in.
     * Assumes that the username exists and password is valid.
     * @param username the username
     */
    public void login(String username) {
        this.user = mBackend.getUsers().get(username);
        this.userController = new UserController(user);
        this.codeController = new CodeController(user);
    }

    /**
     * Saves the database.
     */
    public void saveDatabase() {
        mBackend.saveDatabase();
    }

    /**
     * Logs the user out.
     */
	public <T extends JPanel> void logout(LoginPanel loginPanel, T currentPanel) {
        this.saveDatabase();
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
     * @return a list of users
     */
    public static Map<String, User> getUsers() {
        return mBackend.getUsers();
    }

    /**
     * Removes a user account from the system.
     * Assumes that the user exists.
     * @param username the username of the account that should be deleted
     * @return the gamecontroller
     */
    public Controller deleteUser(String username) {
        mBackend.removeUser(username);
        return this;
    }

    /**
     * Adds a new user to the system
     * @param user the user
     * @return the gamecontroller
     */
    public Controller addUser(User user) {
        mBackend.addUser(user);
        return this;
    }

    /**
     * Adds a new grid to the system
     * @param grid the grid
     * @return the gamecontroller
     */
    public Controller addGrid(Grid grid) {
        mBackend.addGrid(grid);
        return this;
    }

    /**
     * Removes a grid from the system
     * @param grid the grid to be removed
     * @return the gamecontroller
     */
    public Controller removeGrid(Grid grid) {
        mBackend.removeGrid(grid);
        return this;
    }

    /**
     * Updates a grid that has been edited
     * @param grid the updated grid
     * @return the gamecontroller
     */
    public Controller updateGrid(Grid grid) {
        mBackend.updateGrid(grid);
        return this;
    }
    /**
     * Returns the grids in the system
     * @return a map of grid name -> grid
     */
    public Map<String, Grid> getGrids() {
        return mBackend.getGrids();
    }

    /**
     * Checks whether there is at least one admin available in the system.
     * @return true/false
     */
    public boolean doesAdminExist() {
        for(final Map.Entry<String, User> entry : mBackend.getUsers().entrySet()) {
            if (entry.getValue().isAdmin()) {
                return true;
            }
        }
        return false;
    }
}