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

    private String username, password;
    private boolean isAdmin;
    private Map<String, Integer> gridsPlayed;
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
        this.gridsPlayed = new HashMap<String, Integer>();
        this.macros = new HashMap<String, Code>();
        this.macrosActionPanel = new HashMap<String, Stack<ActionPanel.PanelMode>>();
    }

    /**
     * Gets a map of grids (worlds) played by the user
     * @return a map of grids (worlds) played by the user
     */
    public Map<String, Integer> getGridsPlayed() {
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

    public User addMacro(String name, Code code, Stack<ActionPanel.PanelMode> panelMode)
    {
        this.macros.put(name, code);
        this.macrosActionPanel.put(name, panelMode);
        return this;
    }

    public Code getMacro(String name)
    {
        return this.macros.get(name);
    }

    public Stack<ActionPanel.PanelMode> getMacroPanelMode(String name)
    {
        return this.macrosActionPanel.get(name);
    }

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

    public User addGrid(Grid grid, int potatoesCollected)
    {
        if (this.gridsPlayed.get(grid.getName()) != null && this.gridsPlayed.get(grid.getName()) < potatoesCollected)
        {
            this.gridsPlayed.remove(grid.getName());
            this.gridsPlayed.put(grid.getName(), potatoesCollected);
        } else if (this.gridsPlayed.get(grid.getName()) == null)
        {
            this.gridsPlayed.put(grid.getName(), potatoesCollected);
        }
        return this;
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
        return this.username + (isAdmin ? " (admin)" : "");
    }


}
