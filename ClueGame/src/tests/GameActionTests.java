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
	
	@Test
	public void testCreateSuggestion() {
		ComputerPlayer jason = (ComputerPlayer) board.getPlayers().get(0);
		jason.setSeenCards(board.getPlayers().get(1).getMyCards());
		Solution suggestion = jason.createSuggestion();
		assertEquals(suggestion.room, board.getLegend().get(board.getCellAt(jason.getRow(), jason.getColumn()).getInitial()));
		assertFalse(jason.getSeenCards().contains(new Card(suggestion.weapon, CardType.WEAPON)));
		assertFalse(jason.getSeenCards().contains(new Card(suggestion.person, CardType.PERSON)));
		
		assertFalse(jason.getMyCards().contains(new Card(suggestion.weapon, CardType.WEAPON)));
		assertFalse(jason.getMyCards().contains(new Card(suggestion.person, CardType.PERSON)));
		ArrayList<Card> weaponsToAdd = new ArrayList<Card>();
		weaponsToAdd.add(new Card("Dagger", CardType.WEAPON));
		weaponsToAdd.add(new Card("Revolver", CardType.WEAPON));
		weaponsToAdd.add(new Card("Spade", CardType.WEAPON));
		ArrayList<Card> peopleToAdd = new ArrayList<Card>();
		peopleToAdd.add(new Card("Colonel Mustard", CardType.PERSON));
		peopleToAdd.add(new Card("Professor Plum", CardType.PERSON));
		peopleToAdd.add(new Card("Reverend Green", CardType.PERSON));
		jason.setSeenCards(weaponsToAdd);
		jason.setMyCards(peopleToAdd);
		boolean weaponCandlestick = false, weaponLeadPipe = false, weaponRope = false, personWhite = false, personScarlett = false, personPeacock = false;
		for (int i = 0; i < 1000; ++i) {
			suggestion = jason.createSuggestion();
			if (suggestion.weapon.equals("Candlestick")) {
				weaponCandlestick = true;
			}
			if (suggestion.weapon.equals("Lead pipe")) {
				weaponLeadPipe = true;
			}
			if (suggestion.weapon.equals("Rope")) {
				weaponRope = true;
			}
			if (suggestion.person.equals("Ms Scarlett")) {
				personScarlett = true;
			}
			if (suggestion.person.equals("Mrs Peacock")) {
				personPeacock = true;
			}
			if (suggestion.person.equals("Mrs White")) {
				personWhite = true;
			}
			
		}
		assertTrue(weaponCandlestick && weaponRope && weaponLeadPipe);
		assertTrue(personWhite && personScarlett && personPeacock);
	}
	
	@Test
	public void testDisproveSuggestion() {
		ComputerPlayer jason = new ComputerPlayer();
		ArrayList<Card> jasonCards = new ArrayList<Card>();
		ArrayList<Card> jasonSeen = new ArrayList<Card>();
		jasonCards.add(new Card("Professor Plum", CardType.PERSON));
		jasonCards.add(new Card("Dungeon", CardType.ROOM));
		jasonCards.add(new Card("Dagger", CardType.WEAPON));
		jasonSeen.add(new Card("Colonel Mustard", CardType.PERSON));
		
		jason.setMyCards(jasonCards);
		jason.setSeenCards(jasonSeen);
		
		Solution suggestion = new Solution("Colonel Mustard", "Attic", "Rope");
		
		assertEquals(null, jason.disproveSuggestion(suggestion));
		
		suggestion.person = "Professor Plum";
		
		assertEquals(new Card("Professor Plum", CardType.PERSON), jason.disproveSuggestion(suggestion));
		
		suggestion.room = "Dungeon";
		suggestion.weapon = "Dagger";
		
		boolean disproveRoom = false, disprovePerson = false, disproveWeapon = false;
		for (int i = 0; i < 1000; i++) {
			if(jason.disproveSuggestion(suggestion).getCardName().equals("Professor Plum")) {
				disprovePerson = true;
			}
			if (jason.disproveSuggestion(suggestion).getCardName().equals("Dungeon")) {
				disproveRoom = true;
			}
			if (jason.disproveSuggestion(suggestion).getCardName().equals("Dagger")) {
				disproveWeapon = true;
			}
		}
		assertTrue(disproveRoom && disproveWeapon && disprovePerson);
	}
	
	@Test
	public void testHandleSuggestion() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card("Dungeon", CardType.ROOM));
		board.getPlayers().get(0).setMyCards(cards);
		cards = new ArrayList<Card>();
		cards.add(new Card("Dagger", CardType.WEAPON));
		board.getPlayers().get(1).setMyCards(cards);
		cards = new ArrayList<Card>();
		cards.add(new Card("Colonel Mustard", CardType.PERSON));
		board.getPlayers().get(2).setMyCards(cards);
		cards = new ArrayList<Card>();
		cards.add(new Card("Attic", CardType.ROOM));
		board.getPlayers().get(3).setMyCards(cards);
		cards = new ArrayList<Card>();
		cards.add(new Card("Mrs White", CardType.PERSON));
		board.getPlayers().get(4).setMyCards(cards);
		cards = new ArrayList<Card>();
		cards.add(new Card("Lead pipe", CardType.WEAPON));
		board.getPlayers().get(5).setMyCards(cards);
		
		Solution suggestion = new Solution("Professor Plum", "Court Yard", "Rope");
		assertEquals(null, board.handleSuggestion(suggestion, board.getPlayers().get(0)));
		
		suggestion.room = "Dungeon";
		assertEquals(null, board.handleSuggestion(suggestion, board.getPlayers().get(0)));
		
		suggestion.room = "Court Yard";
		suggestion.weapon = "Dagger";
		assertEquals(new Card("Dagger", CardType.WEAPON), board.handleSuggestion(suggestion, board.getPlayers().get(0)));
		
		assertEquals(null, board.handleSuggestion(suggestion, board.getPlayers().get(1)));
		
		suggestion.room = "Attic";
		suggestion.weapon = "Lead pipe";
		assertEquals(new Card("Attic", CardType.ROOM), board.handleSuggestion(suggestion, board.getPlayers().get(1)));
		
		suggestion.weapon = "Dagger";
		suggestion.room = "Dungeon";
		assertEquals(new Card("Dungeon", CardType.ROOM), board.handleSuggestion(suggestion, board.getPlayers().get(3)));
	}
}
