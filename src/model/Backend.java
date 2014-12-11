package model;

//import static org.junit.Assert.*;
import util.Constants;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.junit.Test;

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
            System.out.println( "Error: couldn't load database <" + Constants.DATABASE + ">" );
            return new Backend();
        }
    }
    
 /*   @Test
    public void notNullBackend() {
    	Backend test = new Backend();
    	assertNotNull("new backend should have hashmaps", test);
    } */

    public void saveDatabase() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( new FileOutputStream(Constants.DATABASE) );
            objectOutputStream.writeObject(this);
        } catch( IOException e ) {
            System.out.println( "Error: couldn't save database <" + Constants.DATABASE + ">" );
        }
    }

    public void removeUser(String username) {
        this.users.remove( username );
    }
    
 /*   @Test
    public void removeUserFromBackend() {
    	Backend test = new Backend();
    	User testUser = new User("a", "a", false);
    	test.users.put("a", testUser);
    	test.removeUser("a");
    	assertFalse("testUser should not be in the user hashmap anymore", test.users.containsValue(testUser));
    } */

    public void addUser( User user ) {
        this.users.put(user.getUsername(), user);
    }
    
/*    @Test
    public void addUserToBackend() {
    	Backend test = new Backend();
    	User testUser = new User("a", "a", false);
    	test.addUser(testUser);
    	assertTrue("testUser should be in the backend hashmap now", test.users.containsValue(testUser));
    } */

    public Map<String, User> getUsers() {
        return users;
    }
    
/*    @Test
    public void usersIsNotNull() {
    	Backend test = new Backend();
    	Map<String, User> testUsers = new HashMap<String, User>();
    	test.users = testUsers;
    	assertNotNull("test.users should not be null", test.getUsers());
    } */

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
    
/*    @Test (expected = IllegalArgumentException.class)
    public void gridNameMissing() {
    	Backend test = new Backend();
    	Grid testGrid = new Grid(10);
    	test.addGrid(testGrid);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void sameGridName() {
    	Backend test = new Backend();
    	Grid testGrid1 = new Grid(10);
    	Grid testGrid2 = new Grid(15);
    	testGrid1.setName("name");
    	testGrid2.setName("name");
    	test.addGrid(testGrid1);
    	test.addGrid(testGrid2);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void karelAndHomeMissing() {
    	Backend test = new Backend();
    	Grid testGrid = new Grid(10);
    	testGrid.removeHome();
    	testGrid.removeKarel();
    	test.addGrid(testGrid);
    }
    
    @Test
    public void addGridToBackend() {
    	Backend test = new Backend();
    	Grid testGrid = new Grid(10);
    	testGrid.setName("name");
    	testGrid.setHome(new Coordinate(1, 1));
    	testGrid.setKarel(new Coordinate(2, 2));
    	test.addGrid(testGrid);
    	assertTrue("testGrid should be in the backend hashmap now", test.grids.containsValue(testGrid));
    } */

    /**
     * Updates a grid, by removes the old grid and adds the new grid.
     * @param grid the grid to be updated
     */
    public void updateGrid(Grid grid) {
        this.grids.remove(grid.getName());
        this.grids.put(grid.getName(), grid);
    }
    
 /*   @Test
    public void updatedGridInBackend() {
    	Backend test = new Backend();
    	Grid testGrid = new Grid(10);
    	testGrid.setName("name");
    	testGrid.setHome(new Coordinate(1, 1));
    	testGrid.setKarel(new Coordinate(2, 2));
    	test.addGrid(testGrid);
    	testGrid.setHome(new Coordinate(4, 4));
    	test.updateGrid(testGrid);
    	assertTrue("testGrid should be in the backend hashmap now", test.grids.containsValue(testGrid));
    } */

    /**
     * Removes a grid from the grids class.
     * @param grid the grid to be removed
     */
    public void removeGrid(Grid grid) {
        this.grids.remove(grid.getName());
    }
    
 /*   @Test
    public void removeGridFromBackend() {
    	Backend test = new Backend();
    	Grid testGrid = new Grid(10);
    	testGrid.setName("name");
    	testGrid.setHome(new Coordinate(1, 1));
    	testGrid.setKarel(new Coordinate(2, 2));
    	test.addGrid(testGrid);
    	test.removeGrid(testGrid);
    	assertFalse("testGrid should not be in the backend hashmap now", test.grids.containsValue(testGrid));
    } */

    /**
     * Returns the grids in the system
     * @return a map of grid name -> grid
     */
    public Map<String, Grid> getGrids() {
        return this.grids;
    }
    
 /*   @Test
    public void gridsIsNotNull() {
    	Backend test = new Backend();
    	Map<String, Grid> testGrids = new HashMap<String, Grid>();
    	test.grids = testGrids;
    	assertNotNull("test.grids should not be null", test.getGrids());
    } */

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (final Map.Entry<String, User> entry : this.getUsers().entrySet()) {
            stringBuilder.append(entry.getValue().getUsername() + "\n");
        }
        return stringBuilder.toString().isEmpty() ? "No users Exist" : stringBuilder.toString();
    }

}
