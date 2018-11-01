package clueGame;

import java.awt.Color;

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
	 * In development
	 */
	@Override
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}

}
