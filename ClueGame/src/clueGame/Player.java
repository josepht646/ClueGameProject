package clueGame;

import java.awt.Color;
import java.util.ArrayList;
/**
 * Represents players in the game Clue.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards, seenCards;
	
	/**
	 * Construct the player object
	 * @param playerName - String representing player's name
	 * @param row - Starting row
	 * @param column - Starting column
	 * @param color - Game piece color
	 */
	public Player(String playerName, int row, int column, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		myCards = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
	}
	
	/**
	 * Default Player constructor.
	 */
	public Player() {
	}

	/**
	 * Sets the cards the player has.
	 * @param myCards - ArrayList of Cards
	 */
	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}

	/**
	 * Set the cards the player has seen.
	 * @param seenCards - ArrayList of Cards
	 */
	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}

	/**
	 * Get the player's name.
	 * @return - String representing player's name
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Get the row location.
	 * @return - integer representing row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Get the column location.
	 * @return - integer representing column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Get the Player's color.
	 * @return - Color object
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Get the player's cards.
	 * @return - ArrayList of player's cards.
	 */
	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	/**
	 * Get the cards seen by the player.
	 * @return - ArrayList of cards the player has seen.
	 */
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	
	/**
	 * Adds card to the player's cards.
	 * @param c - Card to add
	 */
	public void addCard(Card c) {
		myCards.add(c);
	}

	/**
	 * Disproves suggestion if possible.
	 * @param suggestion - Solution object representing suggestion to disprove
	 * @return - Card that disproves suggestion or null.
	 */
	public abstract Card disproveSuggestion(Solution suggestion);
}
