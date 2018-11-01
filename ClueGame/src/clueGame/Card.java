package clueGame;
/**
 * Class representing the Cards in Clue.
 * @author Joseph Thurston
 * @author Thomas Depke
 */
public class Card {
	private String cardName;
	private CardType typeOfCard;
	
	/**
	 * Construct the card with the name and type.
	 */
	public Card(String cardName, CardType typeOfCard) {
		this.cardName = cardName;
		this.typeOfCard = typeOfCard;
	}
	
	/**
	 * Override for object.equals method.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Card)) {
			return false;
		} else {
			Card c = (Card) o;
			return cardName.equals(c.cardName) && typeOfCard == c.typeOfCard;
		}
	}
	
	/**
	 * Get the name of the card.
	 * @return - card name
	 */
	public String getCardName() {
		return cardName;
	}
	
	/**
	 * Get the type of the card.
	 * @return - card type
	 */
	public CardType getType() {
		return typeOfCard;
	}
	
	/**
	 * Convert object to string.
	 */
	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", typeOfCard=" + typeOfCard + "]";
	}
}
