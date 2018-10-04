package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import experiment.BoardCell;
import experiment.IntBoard;

class IntBoardTests {
	private IntBoard board;
	@Before
	public void beforeAll() {
		board = new IntBoard();
	}
	
	@Test
	public void testAdjacency0()
	{
		BoardCell cell = board.getCell(0,0);
		HashSet<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	/*@Test
	public void testTargets0_3() {
		BoardCell cell = board.getCell(0,0);
		board.calcTargets(cell, 3);
		HashSet targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}*/

}
