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
	 * @param person - Name of person
	 * @param room - Name of room
	 * @param weapon - Name of weapon
	 */
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
		
	}
	
	/**
	 * Get object as a string.
	 */
	@Override
	public String toString() {
		return person + " in the " + room + " with the " + weapon;
	}
}
