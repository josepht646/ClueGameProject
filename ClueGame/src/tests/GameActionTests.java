package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	public static Board board;
	
	/**
	 * Setup files and load data.
	 */
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayoutCSV.csv", "ClueRooms.txt", "CluePeople.txt", "ClueWeapons.txt");		
		board.initialize();
	}
	
	@Test
	public void testRandomTargetSelection() {
		ComputerPlayer testPlayer = new ComputerPlayer();
		board.calcTargets(7, 14, 2);
		boolean loc_6_13 = false, loc_7_12 = false, loc_6_15 = false, loc_7_16 = false, loc_9_14 = false, loc_8_15 = false;
		for (int i = 0; i < 200; i++) {
			BoardCell selected = testPlayer.pickLocation(board.getTargets());
			if (selected ==board.getCellAt(6, 13))
				loc_6_13 = true;
			else if (selected == board.getCellAt(7, 12))
				loc_7_12 = true;
			else if (selected == board.getCellAt(6, 15))
				loc_6_15 = true;
			else if (selected == board.getCellAt(7, 16))
				loc_7_16 = true;
			else if (selected == board.getCellAt(9, 14))
				loc_9_14 = true;
			else if (selected == board.getCellAt(8, 15))
				loc_8_15 = true;
			else
				fail("Invalidtarget selected");
			}
		assertTrue(loc_6_13 && loc_7_12 && loc_6_15 && loc_7_16 && loc_9_14 && loc_8_15);
	}
	
	@Test
	public void testLastRoomTargetSelection() {
		ComputerPlayer testPlayer = new ComputerPlayer();
		testPlayer.setLastRoom('C');
		board.calcTargets(11, 15, 1);
		assertEquals(testPlayer.pickLocation(board.getTargets()), board.getCellAt(11, 16));
		testPlayer.setLastRoom('D');
		boolean loc_11_16 = false, loc_11_14 = false, loc_10_15 = false, loc_12_15 = false;
		for (int i = 0; i < 100; i++) {
			BoardCell selected = testPlayer.pickLocation(board.getTargets());
			if (selected ==board.getCellAt(11, 16))
				loc_11_16 = true;
			else if (selected == board.getCellAt(11, 14))
				loc_11_14 = true;
			else if (selected == board.getCellAt(10, 15))
				loc_10_15 = true;
			else if (selected == board.getCellAt(12, 15))
				loc_12_15 = true;
			else
				fail("Invalidtarget selected");
			}
		assertTrue(loc_11_16 && loc_11_14 && loc_10_15 && loc_12_15);
	}
	
	@Test
	public void testAccusations() {
		assertTrue(board.checkAccusation(board.getTheAnswer()));
		Solution wrongAnswer = new Solution(board.getTheAnswer().person, "saodfhs", board.getTheAnswer().weapon);
		assertFalse(board.checkAccusation(wrongAnswer));
		wrongAnswer.person = "saofd";
		wrongAnswer.room = board.getTheAnswer().room;
		assertFalse(board.checkAccusation(wrongAnswer));
		wrongAnswer.weapon = "saofd";
		wrongAnswer.person = board.getTheAnswer().person;
		assertFalse(board.checkAccusation(wrongAnswer));
	}
}
