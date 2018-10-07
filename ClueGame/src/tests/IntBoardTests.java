package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

/**
 * Tests the IntBoard and BoardCell classes.
 * 
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class IntBoardTests {
	private IntBoard board;
	@Before
	/**
	 * Allocate space for the IntBoard before tests.
	 */
	public void beforeAll() {
		board = new IntBoard();
	}
	
	@Test
	/**
	 * Test the adjacency list building.
	 */
	public void testAdjacency0()
	{
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
		
		BoardCell cellBR = board.getCell(3,3);
		Set<BoardCell> testListBR = board.getAdjList(cellBR);
		assertTrue(testListBR.contains(board.getCell(2, 3)));
		assertTrue(testListBR.contains(board.getCell(3, 2)));
		assertEquals(2, testListBR.size());
		
		BoardCell cellRE = board.getCell(1,3);
		Set<BoardCell> testListRE = board.getAdjList(cellRE);
		assertTrue(testListRE.contains(board.getCell(0, 3)));
		assertTrue(testListRE.contains(board.getCell(2, 3)));
		assertTrue(testListRE.contains(board.getCell(1, 2)));
		assertEquals(3, testListRE.size());
		
		BoardCell cellLE = board.getCell(2,0);
		Set<BoardCell> testListLE = board.getAdjList(cellLE);
		assertTrue(testListLE.contains(board.getCell(1, 0)));
		assertTrue(testListLE.contains(board.getCell(3, 0)));
		assertTrue(testListLE.contains(board.getCell(2,1)));
		assertEquals(3, testListLE.size());
		
		BoardCell cellS = board.getCell(1,1);
		Set<BoardCell> testListS = board.getAdjList(cellS);
		assertTrue(testListS.contains(board.getCell(1, 2)));
		assertTrue(testListS.contains(board.getCell(2, 1)));
		assertTrue(testListS.contains(board.getCell(0, 1)));
		assertTrue(testListS.contains(board.getCell(1, 0)));
		assertEquals(4, testListS.size());
		
		BoardCell cellM = board.getCell(2,2);
		Set<BoardCell> testListM = board.getAdjList(cellM);
		assertTrue(testListM.contains(board.getCell(1, 2)));
		assertTrue(testListM.contains(board.getCell(2, 1)));
		assertTrue(testListM.contains(board.getCell(3, 2)));
		assertTrue(testListM.contains(board.getCell(2, 3)));
		assertEquals(4, testListM.size());
	}
	
	@Test
	/**
	 * Test the target list builder.
	 */
	public void testTargets0_3() {
		BoardCell cell = board.getCell(0,0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		
		BoardCell cellO = board.getCell(3,3);
		board.calcTargets(cellO, 1);
		Set<BoardCell> targetsO = board.getTargets();
		assertEquals(2, targetsO.size());
		assertTrue(targetsO.contains(board.getCell(3, 2)));
		assertTrue(targetsO.contains(board.getCell(2, 3)));
	}

}