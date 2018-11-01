package clueGame;

import java.awt.Color;
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
		return null;
	}
	
	/**
	 * In development.
	 */
	public void makeAccusation() {
		
	}
	
	/**
	 * In development.
	 */
	public void createSuggestion() {
		
	}

	public char getLastRoom() {
		return lastRoom;
	}

	public void setLastRoom(char lastRoom) {
		this.lastRoom = lastRoom;
	}
}
