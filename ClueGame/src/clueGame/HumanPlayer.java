package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Human player for the game.
 * @author Joseph Thurston
 * @author Thomas Depke
 */
public class HumanPlayer extends Player {
	/**
	 * Construct the human player.
	 * @param playerName - name
	 * @param row - row position
	 * @param column - column position
	 * @param color - color of the player
	 */
	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	/**
	 * Check if player has any cards that can disprove the suggestion and return one of them.
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
	 * Sets the targets to highlight positions to move to.
	 */
	@Override
	public BoardCell pickLocation(Set<BoardCell> targets) {
		for (BoardCell cell : targets) {
			cell.setTarget(true);
		}
		return null;
	}
}
