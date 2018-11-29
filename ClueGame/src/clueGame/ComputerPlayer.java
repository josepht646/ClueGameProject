package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Computer player for the game.
 * @author Joseph Thurston
 * @author Thomas Depke
 */
public class ComputerPlayer extends Player {
	private char lastRoom = '\0';
	

	/**
	 * Construct the computer player.
	 * @param playerName - name
	 * @param row - row position
	 * @param column - column position
	 * @param color - color of the player
	 */
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	/**
	 * Default Computer Player Constructor.
	 */
	public ComputerPlayer() {
		super();
	}

	/**
	 * Disproves suggestion if possible.
	 * @param suggestion - Solution object representing the suggestion to disprove.
	 * @return - Card object that disproves suggestion or null.
	 */
	@Override
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		if (getMyCards().contains(new Card(suggestion.person, CardType.PERSON))) {
			matchingCards.add(new Card(suggestion.person, CardType.PERSON));
		}
		if (getMyCards().contains(new Card(suggestion.weapon, CardType.WEAPON))) {
			matchingCards.add(new Card(suggestion.weapon, CardType.WEAPON));
		}
		if (getMyCards().contains(new Card(suggestion.room, CardType.ROOM))) {
			matchingCards.add(new Card(suggestion.room, CardType.ROOM));
		}
		if (matchingCards.size() == 0) {
			return null;
		}
		Collections.shuffle(matchingCards);
		return matchingCards.get(0);
	}
	
	/**
	 * Picks a location on the board to move to.
	 * @param targets - Set of BoardCell objects the player can move to.
	 * @return - BoardCell to move to.
	 */
	@Override
	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> roomsToMoveTo = new ArrayList<BoardCell>();
		for (BoardCell cell : targets) {
			if (cell.isDoorway() && cell.getInitial() != lastRoom) {
				roomsToMoveTo.add(cell);
			}
		}
		if (roomsToMoveTo.size() != 0) {
			Collections.shuffle(roomsToMoveTo);
			lastRoom = roomsToMoveTo.get(0).getInitial();
			return roomsToMoveTo.get(0);
		}
		int i = 0;
		Board board = Board.getInstance();
		int location = board.rng.nextInt(targets.size());
		for (BoardCell cell : targets) {
			if (i == location) {
				return cell;
			}
			i++;
		}
		return null;
	}
	
	/**
	 * In development.
	 */
	public Solution makeAccusation() {
		if (!suggestionDisproven && checkCards(lastSuggestion)) {
			return lastSuggestion;
		}
		return null;
	}
	
	private boolean checkCards(Solution s) {
		for (Card c : getMyCards()) {
			if (c.getCardName().equals(s.person) || c.getCardName().equals(s.weapon) || c.getCardName().equals(s.room)) {
				return false;
			}
		}
		for (Card c : getSeenCards()) {
			if (c.getCardName().equals(s.person) || c.getCardName().equals(s.weapon) || c.getCardName().equals(s.room)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Creates a suggestion based on the players location and the cards it has encountered.
	 * @return - Solution object representing the suggestion.
	 */
	public Solution createSuggestion() {
		Board board = Board.getInstance();
		ArrayList<Card> deck = board.getCards();
		Collections.shuffle(deck);
		Solution suggestion = new Solution(null, board.getLegend().get(board.getCellAt(getRow(), getColumn()).getInitial()), null);
		Card lastPerson = null, lastWeapon = null;
		for (Card c : deck) {
			if (c.getType() == CardType.PERSON) {
				lastPerson = c;
			} else if (c.getType() == CardType.WEAPON) {
				lastWeapon = c;
			} else {
				continue;
			}
			if (!getMyCards().contains(c) && !getSeenCards().contains(c)) {
				if (c.getType() == CardType.PERSON && suggestion.person == null) {
					suggestion.person = c.getCardName();
				} else if (c.getType() == CardType.WEAPON && suggestion.weapon == null) {
					suggestion.weapon = c.getCardName();
				}
			}
		}
		if (suggestion.person == null) {
			suggestion.person = lastPerson.getCardName();
		}
		if (suggestion.weapon == null) {
			suggestion.weapon = lastWeapon.getCardName();
		}
		lastSuggestion = suggestion;
		return suggestion;
	}
	
	/**
	 * Gets the last room the player was in.
	 * @return - Char representing the last room.
	 */
	public char getLastRoom() {
		return lastRoom;
	}

	/**
	 * Set the last room the player was in.
	 * @param lastRoom - Char representing a room.
	 */
	public void setLastRoom(char lastRoom) {
		this.lastRoom = lastRoom;
	}
}
