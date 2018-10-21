package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
/**
 * 
 * @author Joseph Thurston
 * @author Thomas Depke
 */
public class FileInitTest {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 22;
	private static Board board;
	@BeforeClass
	/**
	 * Set up the board before testing.
	 */
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use config files
		board.setConfigFiles("ClueLayoutCSV.csv", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	@Test
	/**
	 * Ensure the correct board size.
	 */
	public void testBoardSize() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	@Test
	/**
	 * Ensure correctly loaded legend file.
	 */
	public void testLegend() {
		Map<Character, String> legend = board.getLegend();
		assertEquals("Court Yard", legend.get('C'));
		assertEquals("Attic", legend.get('A'));
		assertEquals("Dungeon", legend.get('D'));
		assertEquals("Tower", legend.get('T'));
		assertEquals("Cave", legend.get('V'));
		assertEquals("Lake", legend.get('L'));
		assertEquals("Queen\'s Bedroom", legend.get('Q'));
		assertEquals("Bank Vault", legend.get('B'));
		assertEquals("Internet Chat room", legend.get('I'));
		assertEquals("Dumbwaiter", legend.get('K'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	/**
	 * Test whether door cells are in the proper direction based on
	 * their formatting.
	 * Color white in excel file.
	 */
	public void testDoorDirections() {
		BoardCell room = board.getCellAt(16, 16);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		room = board.getCellAt(16, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(12, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getCellAt(5, 7);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCellAt(10, 10);
		assertFalse(room.isDoorway());
		
		room = board.getCellAt(14, 14);
		assertFalse(room.isDoorway());
	}
	
	@Test
	/**
	 * Ensure the correct number of doorways based on CSV file.
	 */
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(12, numDoors);
	}
	
	@Test
	/**
	 * Ensure cells correspond to assigned rooms.
	 */
	public void testRoomInitials() {
		assertEquals('C', board.getCellAt(0, 0).getInitial()); //Test top left corner.
		assertEquals('B', board.getCellAt(22, 21).getInitial()); //Test bottom right corner.
		assertEquals('V', board.getCellAt(22, 0).getInitial()); //Test bottom left corner.
		assertEquals('I', board.getCellAt(0, 21).getInitial()); //Test top right corner.
		//Random cells:
		assertEquals('A', board.getCellAt(4, 8).getInitial());
		assertEquals('L', board.getCellAt(9, 0).getInitial());
		//Walkway and Dumbwaiter:
		assertEquals('W', board.getCellAt(14, 14).getInitial());
		assertEquals('K', board.getCellAt(10,10).getInitial());
	}
}
