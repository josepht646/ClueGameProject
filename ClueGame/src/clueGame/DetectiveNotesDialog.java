package clueGame;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog {
	private Board board;
	private int numWeapons, numRooms, numPeople;
	public DetectiveNotesDialog() {
		board = Board.getInstance();
		setLayout(new GridLayout(3,2));
		JPanel panel = createPeoplePanel();
		
		add(panel);
		panel = createPeopleGuessPanel();
		add(panel);
		panel = createRoomsPanel();
		add(panel);
		panel = createRoomsGuessPanel();
		add(panel);
		panel = createWeaponsPanel();
		add(panel);
		panel = createWeaponsGuessPanel();
		add(panel);
	}
	
	private JPanel createWeaponsGuessPanel() {
		JPanel panel = new JPanel();
		String[] weaponNames = new String[numWeapons + 1];
		weaponNames[0] = "Unsure";
		int index = 1;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.WEAPON) {
				weaponNames[index] = c.getCardName();
				index++;
			}
		}
		panel.add(new JComboBox(weaponNames));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		return panel;
	}

	private JPanel createRoomsGuessPanel() {
		JPanel panel = new JPanel();
		String[] roomNames = new String[numRooms + 1];
		roomNames[0] = "Unsure";
		int index = 1;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.ROOM) {
				roomNames[index] = c.getCardName();
				index++;
			}
		}
		panel.add(new JComboBox(roomNames));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		return panel;
	}

	private JPanel createPeopleGuessPanel() {
		JPanel panel = new JPanel();
		String[] peopleNames = new String[numPeople + 1];
		peopleNames[0] = "Unsure";
		int index = 1;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.PERSON) {
				peopleNames[index] = c.getCardName();
				index++;
			}
		}
		panel.add(new JComboBox(peopleNames));
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		return panel;
	}

	private JPanel createWeaponsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		numWeapons = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.WEAPON) {
				numWeapons += 1;
				panel.add(new JCheckBox(c.getCardName()));
			}
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		return panel;
	}

	private JPanel createRoomsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		numRooms = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.ROOM) {
				numRooms += 1;
				panel.add(new JCheckBox(c.getCardName()));
			}
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		return panel;
	}

	private JPanel createPeoplePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		numPeople = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.PERSON) {
				numPeople += 1;
				panel.add(new JCheckBox(c.getCardName()));
			}
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		return panel;
	}

}
