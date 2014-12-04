package control;

import com.google.common.eventbus.EventBus;
import model.Backend;
import model.Grid;
import model.User;
import view.LoginPanel;
import view.PlayPanel;

import javax.swing.*;
import java.util.HashMap;
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
    public RobotController robotController;
    public CodeController codeController;
    public PlayPanel playPanel;
    private static Backend mBackend = Backend.readDatabase();

	public Controller initRobot(Grid grid)
    {
        this.robotController = new RobotController(grid);
        return this;
    }

    public Controller initPlay(PlayPanel playPanel)
    {
        this.playPanel = playPanel;
        return this;
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
        this.codeController = new CodeController(user, this);
        this.gridController = new GridController();
    }


    public void reinitCodeController()
    {
        this.codeController = new CodeController(user, this);
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

    public static Map<String, Integer> getGridScores(String gridName)
    {
        Map<String, User> users = mBackend.getUsers();
        Map<String, Grid> grids = mBackend.getGrids();
        Map<String, Integer> scores = new HashMap<String, Integer>();
        for (final Map.Entry<String, User> userEntry : users.entrySet())
        {
            Map<String, Integer> gridsPlayed = userEntry.getValue().getGridsPlayed();
            for (final Map.Entry<String, Integer> gridEntry : gridsPlayed.entrySet())
            {
                if (gridEntry.getKey().equals(gridName))
                {
                    scores.put(userEntry.getKey(), 100 * gridEntry.getValue() / grids.get(gridName).numPotatoes() );
                }
            }
        }
        return scores;
    }
}
