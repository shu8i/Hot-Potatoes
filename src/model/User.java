package model;

import view.ActionPanel;

import java.io.Serializable;
import java.util.*;

/**
 * @author Allant Gomez
 * @author Chris Mnich
 * @author Shahab Shekari
 * @author Steven Rengifo
 * @author Zachary Guadagno
 */
public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7392993612295711974L;
	private String username, password;
    private boolean isAdmin;
    private Map<Grid, Integer> gridsPlayed;
    private Map<Grid, Code> codePlayed_inGrid;
    private Map<String, Code> macros;
    private Map<String, Stack<ActionPanel.PanelMode>> macrosActionPanel;

    /**
     * Creates a new user account.
     * @param username the username
     * @param password the password (will be encrypted)
     * @param isAdmin whether this account is designated as an admin account
     */
    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = encrypt(password);
        this.isAdmin = isAdmin;
        this.gridsPlayed = new HashMap<Grid, Integer>();
        this.codePlayed_inGrid = new HashMap<Grid, Code>();
        this.macros = new HashMap<String, Code>();
        this.macrosActionPanel = new HashMap<String, Stack<ActionPanel.PanelMode>>();
    }
    
    /**
     * Get the code that goes with this grid
     * @param grid 
     * @return code played by the user on this grid
     */
    public Code getCodePlayedinGrid(Grid grid) {    	
		return this.codePlayed_inGrid.get(grid);   	
    }
    
    /**
     * Add code to the grid pass as a parameter
     * @param key grid to look for
     * @param value value to be updated
     */
    public void addCodePlayedinGrid(Grid key, Code value) {    	
		this.codePlayed_inGrid.put(key, value);   	
    }
    
    /**
     * reset code to the grid pass as a parameter
     * @param key grid to look for
     */
    public void resetCodePlayedinGrid(Grid key) {    	
		this.codePlayed_inGrid.put(key, new Code());   	
    }
        
    /**
     * Gets a map of grids (worlds) played by the user
     * @return a map of grids (worlds) played by the user
     */
    public Map<Grid, Integer> getGridsPlayed() {
        return this.gridsPlayed;
    }

    /**
     * Changes the current username of this account
     * @param username the new username
     * @return this user account
     */
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Changes the current password of this account
     * @param password the new password (will be encrypted)
     * @return this user account
     */
    public User setPassword(String password) {
        this.password = encrypt(password);
        return this;
    }

    /**
     * Changes the current admin status of this account
     * @param isAdmin the new admin status
     * @return this user account
     */
    public User changeAdminStatus(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }
    
    /**
     * @param grid
     * @return the score of the user in the current grid
     */
    public int getGridScore(Grid grid) {
        Integer score = this.getGridsPlayed().get(grid);
        return score == null ? 0 : score;
    }

    /**
     * @param name of the macro
     * @param code that the macro will include
     * @param panelMode the current mode of the panel
     * @return User with the macro added
     */
    public User addMacro(String name, Code code, Stack<ActionPanel.PanelMode> panelMode)
    {
        this.macros.put(name, code);
        this.macrosActionPanel.put(name, panelMode);
        return this;
    }

    /**
     * @param name
     * @return the code of the macro specified in the name 
     * if it does not exists it will return null
     */
    public Code getMacro(String name)
    {
        return this.macros.get(name);
    }

    /**
     * @param name
     * @return The panel mode
     */
    public Stack<ActionPanel.PanelMode> getMacroPanelMode(String name)
    {
        return this.macrosActionPanel.get(name);
    }

    /**
     * @return Set of string of all macros of this user
     */
    public Set<String> getMacros()
    {
        return this.macros.keySet();
    }

    /**
     * Gets this user account's username
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets this user account's encrypted password
     * @return the encrypted password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets this user account's admin status
     * @return the admin status
     */
    public boolean isAdmin() {
        return this.isAdmin;
    }

    /**
     * Encrypts a plain text password
     * @param password the plain text password
     * @return the encrypted password
     */
    public static String encrypt(String password) {
        return password;        //TODO encrypt password
    }

    @Override
    public String toString() {
        return this.username + (this.isAdmin ? " (admin)" : "");
    }


}
