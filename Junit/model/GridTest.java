package model;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * @author Chris Mnich
 * 
 * JUnit tests for Grid
 * 
 * @see Grid
 *
 */
public class GridTest {

	@Test
	public void testGridInt() 
	{
		
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		assertEquals(testGrid1.getSize(), 20);
		assertNotNull(testGrid1);
		assertEquals(testGrid2.getSize(), 15);
		assertNotNull(testGrid2);
		assertEquals(testGrid3.getSize(), 45);
		assertNotNull(testGrid3);
		assertEquals(testGrid4.getSize(), 10);
		assertNotNull(testGrid4);
		assertEquals(testGrid5.getSize(), 50);
		assertNotNull(testGrid5);
		
	}

	@Test
	public void testGridGrid() 
	{
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Grid testGrid6 = new Grid(testGrid1);
		Grid testGrid7 = new Grid(testGrid2);
		Grid testGrid8 = new Grid(testGrid3);
		Grid testGrid9 = new Grid(testGrid4);
		Grid testGrid10 = new Grid(testGrid5);
		
		assertEquals(testGrid1.getSize(), testGrid6.getSize());
		assertNotNull(testGrid6);
		assertEquals(testGrid2.getSize(), testGrid7.getSize());
		assertNotNull(testGrid7);
		assertEquals(testGrid3.getSize(), testGrid8.getSize());
		assertNotNull(testGrid8);
		assertEquals(testGrid4.getSize(), testGrid9.getSize());
		assertNotNull(testGrid9);
		assertEquals(testGrid5.getSize(), testGrid10.getSize());
		assertNotNull(testGrid10);
	}

	@Test
	public void testGetSize() {
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		assertEquals(testGrid1.getSize(), 20);
		assertEquals(testGrid2.getSize(), 15);
		assertEquals(testGrid3.getSize(), 45);
		assertEquals(testGrid4.getSize(), 10);
		assertEquals(testGrid5.getSize(), 50);
	}

	@Test
	public void testSetName() 
	{
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		Grid testGrid6 = new Grid(11);
		
		testGrid1.setName("Grid1");
		testGrid2.setName("Grid2");
		testGrid3.setName("Grid3");
		testGrid4.setName("Grid4");
		testGrid5.setName("Grid5");
		
		assertEquals(testGrid1.getName(), "Grid1");
		assertEquals(testGrid2.getName(), "Grid2");
		assertEquals(testGrid3.getName(), "Grid3");
		assertEquals(testGrid4.getName(), "Grid4");
		assertEquals(testGrid5.getName(), "Grid5");
		assertEquals(testGrid6.getName(), "");
	}

	@Test
	public void testGetName() {
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		Grid testGrid6 = new Grid(11);
		
		testGrid1.setName("Grid1");
		testGrid2.setName("Grid2");
		testGrid3.setName("Grid3");
		testGrid4.setName("Grid4");
		testGrid5.setName("Grid5");
		
		assertEquals(testGrid1.getName(), "Grid1");
		assertEquals(testGrid2.getName(), "Grid2");
		assertEquals(testGrid3.getName(), "Grid3");
		assertEquals(testGrid4.getName(), "Grid4");
		assertEquals(testGrid5.getName(), "Grid5");
		assertEquals(testGrid6.getName(), "");
	}

	@Test
	public void testGetKarel() 
	{
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.setKarel(coor1);
		testGrid2.setKarel(coor2);
		testGrid3.setKarel(coor3);
		testGrid4.setKarel(coor4);
		testGrid5.setKarel(coor5);
		
		assertTrue(testGrid1.getKarel().coordinates().equals(coor1));
		assertTrue(testGrid2.getKarel().coordinates().equals(coor2));
		assertTrue(testGrid3.getKarel().coordinates().equals(coor3));
		assertTrue(testGrid4.getKarel().coordinates().equals(coor4));
		assertTrue(testGrid5.getKarel().coordinates().equals(coor5));
	}

	@Test
	public void testGetHome() {
		
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.setHome(coor1);
		testGrid2.setHome(coor2);
		testGrid3.setHome(coor3);
		testGrid4.setHome(coor4);
		testGrid5.setHome(coor5);
		
		assertTrue(testGrid1.getHome().coordinates().equals(coor1));
		assertTrue(testGrid2.getHome().coordinates().equals(coor2));
		assertTrue(testGrid3.getHome().coordinates().equals(coor3));
		assertTrue(testGrid4.getHome().coordinates().equals(coor4));
		assertTrue(testGrid5.getHome().coordinates().equals(coor5));
	}

	@Test
	public void testAddWall() {
		
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.addWall(coor1);
		testGrid2.addWall(coor2);
		testGrid3.addWall(coor3);
		testGrid4.addWall(coor4);
		testGrid5.addWall(coor5);
		
		assertTrue(testGrid1.getBlock(coor1).is(BlockState.WALL));
		assertTrue(testGrid2.getBlock(coor2).is(BlockState.WALL));
		assertTrue(testGrid3.getBlock(coor3).is(BlockState.WALL));
		assertTrue(testGrid4.getBlock(coor4).is(BlockState.WALL));
		assertTrue(testGrid5.getBlock(coor5).is(BlockState.WALL));
	}

	@Test
	public void testRemoveWall() {
		
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.addWall(coor1);
		testGrid2.addWall(coor2);
		testGrid3.addWall(coor3);
		testGrid4.addWall(coor4);
		testGrid5.addWall(coor5);
		
		testGrid1.removeWall(coor1);
		testGrid2.removeWall(coor2);
		testGrid3.removeWall(coor3);
		testGrid4.removeWall(coor4);
		testGrid5.removeWall(coor5);
		
		assertFalse(testGrid1.getBlock(coor1).is(BlockState.WALL));
		assertFalse(testGrid2.getBlock(coor2).is(BlockState.WALL));
		assertFalse(testGrid3.getBlock(coor3).is(BlockState.WALL));
		assertFalse(testGrid4.getBlock(coor4).is(BlockState.WALL));
		assertFalse(testGrid5.getBlock(coor5).is(BlockState.WALL));
	}

	@Test
	public void testSetKarel() 
	{
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.setKarel(coor1);
		testGrid2.setKarel(coor2);
		testGrid3.setKarel(coor3);
		testGrid4.setKarel(coor4);
		testGrid5.setKarel(coor5);
		
		assertTrue(testGrid1.getBlock(coor1).is(BlockState.KAREL));
		assertTrue(testGrid2.getBlock(coor2).is(BlockState.KAREL));
		assertTrue(testGrid3.getBlock(coor3).is(BlockState.KAREL));
		assertTrue(testGrid4.getBlock(coor4).is(BlockState.KAREL));
		assertTrue(testGrid5.getBlock(coor5).is(BlockState.KAREL));
	}

	@Test
	public void testSetHome() {
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.setHome(coor1);
		testGrid2.setHome(coor2);
		testGrid3.setHome(coor3);
		testGrid4.setHome(coor4);
		testGrid5.setHome(coor5);
		
		assertTrue(testGrid1.getBlock(coor1).is(BlockState.HOME));
		assertTrue(testGrid2.getBlock(coor2).is(BlockState.HOME));
		assertTrue(testGrid3.getBlock(coor3).is(BlockState.HOME));
		assertTrue(testGrid4.getBlock(coor4).is(BlockState.HOME));
		assertTrue(testGrid5.getBlock(coor5).is(BlockState.HOME));
	}

	@Test
	public void testRemoveKarel() {
		
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.setKarel(coor1);
		testGrid2.setKarel(coor2);
		testGrid3.setKarel(coor3);
		testGrid4.setKarel(coor4);
		testGrid5.setKarel(coor5);
		
		testGrid1.removeKarel();
		testGrid2.removeKarel();
		testGrid3.removeKarel();
		testGrid4.removeKarel();
		testGrid5.removeKarel();
		
		assertFalse(testGrid1.getBlock(coor1).is(BlockState.KAREL));
		assertFalse(testGrid2.getBlock(coor2).is(BlockState.KAREL));
		assertFalse(testGrid3.getBlock(coor3).is(BlockState.KAREL));
		assertFalse(testGrid4.getBlock(coor4).is(BlockState.KAREL));
		assertFalse(testGrid5.getBlock(coor5).is(BlockState.KAREL));
	}

	@Test
	public void testRemoveHome() {
		
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.setHome(coor1);
		testGrid2.setHome(coor2);
		testGrid3.setHome(coor3);
		testGrid4.setHome(coor4);
		testGrid5.setHome(coor5);
		
		testGrid1.removeHome();
		testGrid2.removeHome();
		testGrid3.removeHome();
		testGrid4.removeHome();
		testGrid5.removeHome();
		
		assertFalse(testGrid1.getBlock(coor1).is(BlockState.HOME));
		assertFalse(testGrid2.getBlock(coor2).is(BlockState.HOME));
		assertFalse(testGrid3.getBlock(coor3).is(BlockState.HOME));
		assertFalse(testGrid4.getBlock(coor4).is(BlockState.HOME));
		assertFalse(testGrid5.getBlock(coor5).is(BlockState.HOME));
	}

	@Test
	public void testAddPotato() {
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.addPotato(coor1);
		testGrid2.addPotato(coor2);
		testGrid3.addPotato(coor3);
		testGrid4.addPotato(coor4);
		testGrid5.addPotato(coor5);
		
		assertTrue(testGrid1.getBlock(coor1).is(BlockState.POTATO));
		assertTrue(testGrid2.getBlock(coor2).is(BlockState.POTATO));
		assertTrue(testGrid3.getBlock(coor3).is(BlockState.POTATO));
		assertTrue(testGrid4.getBlock(coor4).is(BlockState.POTATO));
		assertTrue(testGrid5.getBlock(coor5).is(BlockState.POTATO));
	}

	@Test
	public void testRemovePotato() {
		Grid testGrid1 = new Grid(20);
		Grid testGrid2 = new Grid(15);
		Grid testGrid3 = new Grid(45);
		Grid testGrid4 = new Grid(10);
		Grid testGrid5 = new Grid(50);
		
		Coordinate coor1 = new Coordinate(4,5);
		Coordinate coor2 = new Coordinate(7,8);
		Coordinate coor3 = new Coordinate(1,1);
		Coordinate coor4 = new Coordinate(2,8);
		Coordinate coor5 = new Coordinate(38,29);
		
		testGrid1.addPotato(coor1);
		testGrid2.addPotato(coor2);
		testGrid3.addPotato(coor3);
		testGrid4.addPotato(coor4);
		testGrid5.addPotato(coor5);
		
		testGrid1.removePotato(coor1);
		testGrid2.removePotato(coor2);
		testGrid3.removePotato(coor3);
		testGrid4.removePotato(coor4);
		testGrid5.removePotato(coor5);
		
		assertFalse(testGrid1.getBlock(coor1).is(BlockState.POTATO));
		assertFalse(testGrid2.getBlock(coor2).is(BlockState.POTATO));
		assertFalse(testGrid3.getBlock(coor3).is(BlockState.POTATO));
		assertFalse(testGrid4.getBlock(coor4).is(BlockState.POTATO));
		assertFalse(testGrid5.getBlock(coor5).is(BlockState.POTATO));
	}

}
