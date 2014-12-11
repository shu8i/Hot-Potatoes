package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class BackendTest {
    
    @Test
    public void notNullBackend() {
    	Backend test = new Backend();
    	assertNotNull("new backend should have hashmaps", test);
    }
    
    @Test
    public void removeUserFromBackend() {
    	Backend test = new Backend();
    	User testUser = new User("a", "a", false);
    	test.getUsers().put("a", testUser);
    	test.removeUser("a");
    	assertFalse("testUser should not be in the user hashmap anymore", test.getUsers().containsValue(testUser));
    }
    
        
    @Test
    public void addUserToBackend() {
    	Backend test = new Backend();
    	User testUser = new User("a", "a", false);
    	test.addUser(testUser);
    	assertTrue("testUser should be in the backend hashmap now", test.getUsers().containsValue(testUser));
    }
    
    @Test
    public void usersIsNotNull() {
    	Backend test = new Backend();
    	test.getUsers();
    	assertNotNull("test.users should not be null", test.getUsers());
    }

    @Test (expected = IllegalArgumentException.class)
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
    	assertTrue("testGrid should be in the backend hashmap now", test.getGrids().containsValue(testGrid));
    }
    
   @Test
    public void updatedGridInBackend() {
    	Backend test = new Backend();
    	Grid testGrid = new Grid(10);
    	testGrid.setName("name");
    	testGrid.setHome(new Coordinate(1, 1));
    	testGrid.setKarel(new Coordinate(2, 2));
    	test.addGrid(testGrid);
    	testGrid.setHome(new Coordinate(4, 4));
    	test.updateGrid(testGrid);
    	assertTrue("testGrid should be in the backend hashmap now", test.getGrids().containsValue(testGrid));
    }

    @Test
    public void removeGridFromBackend() {
    	Backend test = new Backend();
    	Grid testGrid = new Grid(10);
    	testGrid.setName("name");
    	testGrid.setHome(new Coordinate(1, 1));
    	testGrid.setKarel(new Coordinate(2, 2));
    	test.addGrid(testGrid);
    	test.removeGrid(testGrid);
    	assertFalse("testGrid should not be in the backend hashmap now", test.getGrids().containsValue(testGrid));
    }
    
    @Test
    public void gridsIsNotNull() {
    	Backend test = new Backend();
    	test.getGrids();
    	assertNotNull("test.grids should not be null", test.getGrids());
    }

}