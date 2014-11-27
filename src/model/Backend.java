package model;

import util.Constants;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shahab Shekari on 11/9/14.
 */
public class Backend implements Serializable {

//    static final long serialVersionUID = 1L;
    private Map<String, User> users;
    private Map<String, Grid> grids;

    public Backend() {
        this.users = new HashMap<String, User>();
        this.grids = new HashMap<String, Grid>();
    }

    /**
     * Reads a backend object from file
     * @return  the backend object
     */
    public static Backend readDatabase() {
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream( new FileInputStream(Constants.DATABASE) );
            return (Backend)objectInputStream.readObject();
        } catch( Exception e ) {
            e.printStackTrace();
            System.out.println( "Error: couldn't load database <" + Constants.DATABASE + ">" );
            return new Backend();
        }
    }

    public void saveDatabase() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( new FileOutputStream(Constants.DATABASE) );
            objectOutputStream.writeObject(this);
        } catch( IOException e ) {
            e.printStackTrace();
            System.out.println( "Error: couldn't save database <" + Constants.DATABASE + ">" );
        }
    }

    public void removeUser(String username) {
        this.users.remove( username );
    }

    public void addUser( User user ) {
        this.users.put(user.getUsername(), user);
    }

    public Map<String, User> getUsers() {
        return users;
    }

    /**
     * Adds a grid to the grids class
     * @param grid the grid to be added
     */
    public void addGrid(Grid grid) {
        if (grid.getName() == null) {
            throw new IllegalArgumentException("Grid name missing.");
        }

        if (this.grids.get(grid.getName()) != null) {
            throw new IllegalArgumentException("\"" + grid.getName() + "\" already exists.");
        }

        if (grid.getKarel() == null || grid.getHome() == null) {
            throw new IllegalArgumentException("Karel and/or Home missing.");
        }
        this.grids.put(grid.getName(), grid);
    }

    /**
     * Updates a grid, by removes the old grid and adds the new grid.
     * @param grid the grid to be updated
     */
    public void updateGrid(Grid grid) {
        this.grids.remove(grid.getName());
        this.grids.put(grid.getName(), grid);
    }

    /**
     * Removes a grid from the grids class.
     * @param grid the grid to be removed
     */
    public void removeGrid(Grid grid) {
        this.grids.remove(grid.getName());
    }

    /**
     * Returns the grids in the system
     * @return a map of grid name -> grid
     */
    public Map<String, Grid> getGrids() {
        return this.grids;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (final Map.Entry<String, User> entry : this.getUsers().entrySet()) {
            stringBuilder.append(entry.getValue().getUsername() + "\n");
        }
        return stringBuilder.toString().isEmpty() ? "No users Exist" : stringBuilder.toString();
    }

}
