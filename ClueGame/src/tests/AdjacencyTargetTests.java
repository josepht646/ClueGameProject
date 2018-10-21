package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

/**
 * Test adjacency lists and targets at various positions on the board.
 * 
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class AdjacencyTargetTests {
	private static Board board;
	
	/**
	 * Initialize the board.
	 */
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayoutCSV.csv", "ClueRooms.txt");		
		board.initialize();
	}
	
	
	/**
	 * Test adjacency inside rooms.
	 * Color orange in excel file.
	 */
	@Test
	public void insideRoomAdjTest() {
		// Test in the middle of a room.
		Set<BoardCell> testList = board.getAdjList(9, 2);
		assertEquals(0, testList.size());
		
		// Test bottom left corner
		testList = board.getAdjList(22, 0);
		assertEquals(0, testList.size());
		
		// Test bottom right corner
		testList = board.getAdjList(22, 21);
		assertEquals(0, testList.size());
		
		// Test in a room next to a doorway.
		testList = board.getAdjList(11, 17);
		assertEquals(0, testList.size());
		
		// Test in a room surrounded by walkway.
		testList = board.getAdjList(5, 12);
		assertEquals(0, testList.size());
	}
	
	/**
	 * Test adjacency in doorways.
	 * Color purple in excel file.
	 */
	@Test
	public void roomExitAdjTest()
	{
		// Test door facing up 
		Set<BoardCell> testList = board.getAdjList(17, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(16, 11)));
		
		// Test door facing down
		testList = board.getAdjList(3, 13);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 13)));
		
		// Test door facing left when there is a walkway below.
		testList = board.getAdjList(7, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 16)));
	}
	
	/**
	 * Test adjacency next to doorways.
	 * Color green in excel file.
	 */
	@Test
	public void nextToDoorwayAdjTest() {
		// Test beside a door facing right.
		Set<BoardCell> testList = board.getAdjList(16, 5);
		assertTrue(testList.contains(board.getCellAt(16, 4)));
		assertTrue(testList.contains(board.getCellAt(16, 6)));
		assertTrue(testList.contains(board.getCellAt(15, 5)));
		assertTrue(testList.contains(board.getCellAt(17, 5)));
		assertEquals(4, testList.size());
		
		// Test next to a door facing down.
		testList = board.getAdjList(13, 3);
		assertTrue(testList.contains(board.getCellAt(13, 2)));
		assertTrue(testList.contains(board.getCellAt(13, 4)));
		assertTrue(testList.contains(board.getCellAt(12, 3)));
		assertTrue(testList.contains(board.getCellAt(14, 3)));
		assertEquals(4, testList.size());
		
		// Test next to a door facing left.
		testList = board.getAdjList(7, 16);
		assertTrue(testList.contains(board.getCellAt(7, 15)));
		assertTrue(testList.contains(board.getCellAt(7, 17)));
		assertTrue(testList.contains(board.getCellAt(6, 16)));
		assertTrue(testList.contains(board.getCellAt(8, 16)));
		assertEquals(4, testList.size());

		// Test next to a door facing up.
		testList = board.getAdjList(16, 10);
		assertTrue(testList.contains(board.getCellAt(16, 9)));
		assertTrue(testList.contains(board.getCellAt(16, 11)));
		assertTrue(testList.contains(board.getCellAt(15, 10)));
		assertTrue(testList.contains(board.getCellAt(17, 10)));
		assertEquals(4, testList.size());
	}
	
	/**
	 * Test adjacency at various walkway positions.
	 * Color light purple in excel file.
	 */
	@Test
	public void walkwayAdjTest() {
		// Test walkway at left edge of board.
		Set<BoardCell> testList = board.getAdjList(6, 0);
		assertTrue(testList.contains(board.getCellAt(5, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 0)));
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertEquals(3, testList.size());
		
		// Test walkway surrounded by walkway.
		testList = board.getAdjList(13, 5);
		assertTrue(testList.contains(board.getCellAt(12, 5)));
		assertTrue(testList.contains(board.getCellAt(14, 5)));
		assertTrue(testList.contains(board.getCellAt(13, 4)));
		assertTrue(testList.contains(board.getCellAt(13, 6)));
		assertEquals(4, testList.size());
		
		// Test walkway next to dumbwaiter.
		testList = board.getAdjList(14, 7);
		assertTrue(testList.contains(board.getCellAt(14, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 8)));
		assertTrue(testList.contains(board.getCellAt(15, 7)));
		assertEquals(3, testList.size());
		
		// Test walkway next to doorway.
		testList = board.getAdjList(4, 13);
		assertTrue(testList.contains(board.getCellAt(3, 13)));
		assertTrue(testList.contains(board.getCellAt(5, 13)));
		assertEquals(2, testList.size());
		
		// Test walkway at bottom edge of board.
		testList = board.getAdjList(22, 14);
		assertTrue(testList.contains(board.getCellAt(21, 14)));
		assertEquals(1, testList.size());
		
		// Test walkway at top edge of board.
		testList = board.getAdjList(0, 15);
		assertTrue(testList.contains(board.getCellAt(0, 16)));
		assertTrue(testList.contains(board.getCellAt(1, 15)));
		assertEquals(2, testList.size());
		
		// Test walkway at right edge of board.
		testList = board.getAdjList(8, 21);
		assertTrue(testList.contains(board.getCellAt(8, 20)));
		assertEquals(1, testList.size());
	}
	
	/**
	 * Test targets at one step.
	 * Color light blue in excel file.
	 */
	@Test
	public void targetsOneStep() {
		// Test walkway at top edge.
		board.calcTargets(0, 5, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 6)));
		assertTrue(targets.contains(board.getCellAt(1, 5)));	
		
		// Test walkway next to doorway.
		board.calcTargets(5, 6, 1);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 6)));	
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
	}
	
	/**
	 * Test targets at three steps.
	 * Color light blue in excel file.
	 */
	@Test
	public void targetsThreeSteps() {
		// Test doorway.
		board.calcTargets(17, 10, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 8)));
		assertTrue(targets.contains(board.getCellAt(15, 9)));
		assertTrue(targets.contains(board.getCellAt(14, 10)));
		assertTrue(targets.contains(board.getCellAt(15, 11)));
		assertTrue(targets.contains(board.getCellAt(16, 12)));
		
		// Test walkway at top edge.
		board.calcTargets(0, 5, 3);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 5)));
		
		// Test next to doorway.
		board.calcTargets(5, 6, 3);
		targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 6)));
		assertTrue(targets.contains(board.getCellAt(4, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		assertTrue(targets.contains(board.getCellAt(3, 5)));
		assertTrue(targets.contains(board.getCellAt(4, 4)));
		assertTrue(targets.contains(board.getCellAt(5, 3)));
		assertTrue(targets.contains(board.getCellAt(6, 4)));
		assertTrue(targets.contains(board.getCellAt(8, 6)));
		assertTrue(targets.contains(board.getCellAt(7, 7)));
		assertTrue(targets.contains(board.getCellAt(6, 8)));
	}
	
	/**
	 * Test targets at four steps.
	 * Color light blue in excel file.
	 */
	@Test
	public void targetsFourSteps() {
		// Test walkway surrounded by walkway and shortcut to doorway.
		board.calcTargets(15, 14, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(18, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 14)));
		assertTrue(targets.contains(board.getCellAt(14, 15)));
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(16, 16)));
		assertTrue(targets.contains(board.getCellAt(16, 15)));
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		assertTrue(targets.contains(board.getCellAt(16, 13)));
		assertTrue(targets.contains(board.getCellAt(15, 12)));
		assertTrue(targets.contains(board.getCellAt(14, 13)));
		assertTrue(targets.contains(board.getCellAt(11, 14)));
		assertTrue(targets.contains(board.getCellAt(12, 15)));
		assertTrue(targets.contains(board.getCellAt(13, 16)));
		assertTrue(targets.contains(board.getCellAt(14, 17)));
		assertTrue(targets.contains(board.getCellAt(15, 18)));
		assertTrue(targets.contains(board.getCellAt(19, 14)));
		assertTrue(targets.contains(board.getCellAt(16, 11)));
		assertTrue(targets.contains(board.getCellAt(15, 10)));
		assertTrue(targets.contains(board.getCellAt(14, 11)));
	}

}
