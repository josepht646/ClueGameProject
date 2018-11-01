package clueGame;

/**
 * Class representing the three cards that are the solution to the game.
 * @author Joseph Thurston
 * @author Thomas Depke
 */
public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	/**
	 * Construct the solution with person, room, and weapon names.
	 * @param person
	 * @param room
	 * @param weapon
	 */
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
		
	}
}
