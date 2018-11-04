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
	
	public ComputerPlayer() {
		super();
	}

	/**
	 * In development.
	 */
	@Override
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	/**
	 * In development.
	 * @param targets
	 * @return
	 */
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
	public Card[] makeAccusation() {
		return null;
	}
	
	/**
	 * In development.
	 */
	public Solution createSuggestion() {
		Board board = Board.getInstance();
		ArrayList<Card> deck = board.getCards();
		Collections.shuffle(deck);
		Solution suggestion = new Solution("", board.getLegend().get(board.getCellAt(getRow(), getColumn()).getInitial()), "");
		for (Card c : deck) {
			if (!getMyCards().contains(c) && !getSeenCards().contains(c) && c.getType() != CardType.ROOM) {
				if (c.getType() == CardType.PERSON && suggestion.person.equals("")) {
					suggestion.person = c.getCardName();
				} else if (c.getType() == CardType.WEAPON && suggestion.weapon.equals("")) {
					suggestion.weapon = c.getCardName();
				} else {
					break;
				}
			}
		}
		return suggestion;
	}
	
	public char getLastRoom() {
		return lastRoom;
	}

	public void setLastRoom(char lastRoom) {
		this.lastRoom = lastRoom;
	}
}
