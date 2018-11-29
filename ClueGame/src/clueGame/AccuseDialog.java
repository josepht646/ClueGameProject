package clueGame;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * AccuseDialog is a dialog window for making an accusation.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class AccuseDialog  extends JDialog {
	private Board board;
	private boolean submitted = false;
	private JButton submit, cancel;
	private JComboBox<String> roomsList, weaponsList, peopleList;
	private Solution response = new Solution("","","");
	
	/**
	 * Construct the object.
	 */
	public AccuseDialog() {
		board = Board.getInstance();
		setLayout(new GridLayout(4,2));
		setTitle("Make an Accusation");
		JLabel label = new JLabel("Room");
		add(label);
		add(createRoomsGuessPanel());
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
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submit) {
				response.person = peopleList.getSelectedItem().toString();
				response.weapon = weaponsList.getSelectedItem().toString();
				response.room = roomsList.getSelectedItem().toString();
				submitted = true;
			} else {
				submitted = false;
			}
			setVisible(false);
			if (submitted) {
				if (board.checkAccusation(response)) {
					JFrame winMessage = new JFrame();
					JOptionPane.showMessageDialog(winMessage, "You accused the correct person and won the game!" , "You Win", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				} else {
					JFrame loseMessage = new JFrame();
					JOptionPane.showMessageDialog(loseMessage, "You accused the wrong person and lost :(" , "Rip in Pepperoni", JOptionPane.INFORMATION_MESSAGE);
					ClueGame.getInstance().setHumanPlayerTurn(false);
					for (BoardCell cell : board.getTargets()) {
						cell.setTarget(false);
					}
					board.getPlayers().remove(ClueGame.getInstance().getCurrentPlayer());
					ClueGame.getInstance().setCurrentPlayer(ClueGame.getInstance().getCurrentPlayer() - 1);
					Player.suggestionDisproven = true;
				}
			}
		}
	}
	
	/**
	 * Get the accusation response.
	 * @return - Solution object
	 */
	public Solution getResponse() {
		return response;
	}
	
	private JPanel createRoomsGuessPanel() {
		int numRooms = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.ROOM) {
				numRooms += 1;
			}
		}
		JPanel panel = new JPanel();
		String[] roomNames = new String[numRooms];
		int index = 0;
		for(Card c: board.getCards() ) {
			if (c.getType() == CardType.ROOM) {
				roomNames[index] = c.getCardName();
				index++;
			}
		}
		roomsList = new JComboBox<String>(roomNames);
		panel.add(roomsList);
		
		return panel;
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
	 * Check if accusation is submitted.
	 * @return - boolean value
	 */
	public boolean isSubmitted() {
		return submitted;
	}
}