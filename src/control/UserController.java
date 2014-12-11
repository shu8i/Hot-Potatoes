package control;

import model.Code;
import model.Grid;
import model.User;
import view.ActionPanel;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class UserController {

    private User user;

    /**
     * Creates a new user controller
     * @param user the user
     */
    public UserController(User user) {
        this.user = user;
    }

    /**
     * Logs out a user (saves the user file to disk).
     * @return whether the save was successful
     */
    public boolean logout() {
        //TODO implement
        return true;
    }

    /**
     * Attempts to login a user.
     * Calls DatabaseController and loads the list of existing user accounts.
     * If the username/password is valid, it'll get the user's GameController.
     * @param username the username
     * @param password the password
     * @return  null if the login was unsuccessful
     *          GameController of the logged in user, if login was successful
     */
    public static Controller login(String username, String password) {
        //TODO implement
        return null;
    }

    /**
     * Adds a new user
     * @param username the username
     * @param password the password
     * @param isAdmin whether the new user has admin priviliges
     * @return whether the save (to file) was successful
     */
    public static boolean addUser(String username, String password, boolean isAdmin) {
        //TODO implement
        return true;
    }

    /**
     * Removes a user and his/her corresponding files
     * @param username the username of the user to be deleted
     * @return whether the delete (of the user's file) was successful
     */
    public static boolean removeUser(String username) {
        //TODO implement
        return true;
    }

    /**
     * Gets the user's score on a grid, if that grid has been played
     * @param grid the grid for which the score should be pulled
     * @return a score of 0-100%
     */
    public int getGridScore(Grid grid) {
        Integer score = this.user.getGridsPlayed().get(grid.getName());
        return score == null ? 0 : (grid.numPotatoes() != 0 ? 100 * score / grid.numPotatoes() : score);
    }

    public UserController addMacro(String name, Code code, Stack<ActionPanel.PanelMode> panelMode)
            throws IllegalArgumentException
    {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty!");
        if (code.isEmpty() || panelMode == null) throw new IllegalArgumentException("There's nothing to save!");
        this.user.addMacro(name, code, panelMode);
        return this;
    }

    public Code getMacro(String name)
    {
        return this.user.getMacro(name);
    }

    public Stack<ActionPanel.PanelMode> getMacroPanelMode(String name)
    {
        return this.user.getMacroPanelMode(name);
    }

    public Set<String> getMacros()
    {
        return this.user.getMacros();
    }

    public UserController addGridPlayed(Grid grid, int potatoesCollected)
    {
        this.user.addGrid(grid, potatoesCollected);
        return this;
    }

    public Map<String, Code> getSavedCodes()
    {
        return this.user.getSavedCodes();
    }

    public UserController saveCode(String name, Code code, Stack<ActionPanel.PanelMode> mode)
    {
        this.user.saveCode(name, code, mode);
        return this;
    }

    public Stack<ActionPanel.PanelMode> getActionPanelMode(String name)
    {
        return this.user.getcodeActionPanel(name);
    }
}
