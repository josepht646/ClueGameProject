package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;
import clueGame.*;
/**
 * Tests for game piece setup.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class GameSetupTests {
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
	
	/**
	 * Test that the deck in Board contains the correct cards that were loaded from the file.
	 */
	@Test
	public void testCards() {
		assertEquals(21, board.getCards().size());
		assertTrue(board.getCards().contains(new Card("Colonel Mustard", CardType.PERSON)));
		assertTrue(board.getCards().contains(new Card("Candlestick", CardType.WEAPON)));
		assertTrue(board.getCards().contains(new Card("Attic", CardType.ROOM)));
		assertTrue(board.getCards().contains(new Card("Reverend Green", CardType.PERSON)));
		assertTrue(board.getCards().contains(new Card("Lead pipe", CardType.WEAPON)));
		assertTrue(board.getCards().contains(new Card("Dungeon", CardType.ROOM)));
	}
	
	/**
	 * Test that dealing cards gives every player three cards and none of the players have the same card.
	 */
	@Test
	public void testDealing() {
		HashSet<Card> playerCards = new HashSet<Card>();
		int numPlayers = 0, numWeapons = 0, numRooms = 0;
		for (Player player : board.getPlayers()) {
			assertEquals(player.getMyCards().size(), 3);
			assertEquals(player.getSeenCards().size(), 0);
			for (int i = 0; i < 3; i++) {
				playerCards.add(player.getMyCards().get(i));
				switch(player.getMyCards().get(i).getType()) {
				case PERSON:
					numPlayers += 1;
					break;
				case WEAPON:
					numWeapons += 1;
					break;
				case ROOM:
					numRooms += 1;
					break;
				}
				
			}
		}
		assertEquals(playerCards.size(), 18);
		assertEquals(numPlayers, 5);
		assertEquals(numWeapons, 5);
		assertEquals(numRooms, 8);
	}
	
	/**
	 * Test that all of the players loaded from the file and we have 6 in total.
	 */
	@Test
	public void testPlayers() {
		assertEquals(board.getPlayers().size(), 6);
		assertEquals(board.getPlayers().get(1).getPlayerName(), "Professor Plum");
		assertEquals(board.getPlayers().get(1).getColor(), Color.MAGENTA);
		assertEquals(board.getPlayers().get(1).getRow(), 0);
		assertEquals(board.getPlayers().get(1).getColumn(), 6);
		
		assertEquals(board.getPlayers().get(5).getPlayerName(), "Mrs White");
		assertEquals(board.getPlayers().get(5).getColor(), Color.WHITE);
		assertEquals(board.getPlayers().get(5).getRow(), 22);
		assertEquals(board.getPlayers().get(5).getColumn(), 14);
	}
	
	
}
