package clueGame;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * GuessDialog is a dialog window for making a suggestion.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class GuessDialog extends JDialog {
	private Board board;
	private boolean submitted = false;
	private JTextField currentRoomText;
	private JButton submit, cancel;
	private JComboBox<String> weaponsList, peopleList;
	private Solution response = new Solution("","","");
	
	/**
	 * Construct the object.
	 */
	public GuessDialog() {
		board = Board.getInstance();
		setLayout(new GridLayout(4,2));
		setTitle("Make a Guess");
		JLabel label = new JLabel("Your room");
		add(label);
		currentRoomText = new JTextField(20);
		currentRoomText.setEditable(false);
		add(currentRoomText);
		label = new JLabel("Person");
		add(label);
		add(createPersonGuessPanel());
		label = new JLabel("Weapon");
		add(label);
		add(createWeaponsGuessPanel());
		submit = new JButton("Submit");
		submit.addActionListener(new SubmitListener());
		cancel = new JButton("Cancel");
		cancel.addActionListener(new SubmitListener());
		add(submit);
		add(cancel);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
	}
	
	private class SubmitListener implements ActionListener {
		/**
		 * Action for button clicked.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submit) {
				response.person = peopleList.getSelectedItem().toString();
				response.weapon = weaponsList.getSelectedItem().toString();
				submitted = true;
			} else {
				submitted = false;
			}
			setVisible(false);
			if (submitted) {
				board.handleSuggestion(response, board.getHumanPlayer());
			}
		}
	}
	
	/**
	 * Set the name of the room.
	 * @param roomName - String object
	 */
	public void setRoomName(String roomName) {
		response.room = roomName;
		currentRoomText.setText(roomName);
	}
	
	/**
	 * Get the suggestion response.
	 * @return - Solution object
	 */
	public Solution getResponse() {
		return response;
	}

	private JPanel createWeaponsGuessPanel() {
		int numWeapons = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.WEAPON) {
				numWeapons += 1;
			
			}
		}
		JPanel panel = new JPanel();
		String[] weaponNames = new String[numWeapons];
		int index = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.WEAPON) {
				weaponNames[index] = c.getCardName();
				index++;
			}
		}
		weaponsList = new JComboBox<String>(weaponNames);
		panel.add(weaponsList);
		
		return panel;
	}
	
	private JPanel createPersonGuessPanel() {
		int numPeople = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.PERSON) {
				numPeople += 1;
			
			}
		}
		JPanel panel = new JPanel();
		String[] names = new String[numPeople];
		int index = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.PERSON) {
				names[index] = c.getCardName();
				index++;
			}
		}
		peopleList = new JComboBox<String>(names);
		panel.add(peopleList);
		
		return panel;
	}
	
	/**
	 * Check if suggestion is submitted.
	 * @return - Boolean value
	 */
	public boolean isSubmitted() {
		return submitted;
	}
}
