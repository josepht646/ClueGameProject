package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
/**
 * The Clue Game class sets up the game and creates the gui.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class ClueGame extends JPanel {
	private Board board;
	private DetectiveNotesDialog dialog;
	/**
	 * Sets up the board and the dialog.
	 */
	public ClueGame() {
		setBackground (Color.BLACK);
		board = Board.getInstance();
		board.setConfigFiles("ClueLayoutCSV.csv", "ClueRooms.txt", "CluePeople.txt", "ClueWeapons.txt");		
		board.initialize();
		dialog = new DetectiveNotesDialog();
		dialog.pack();
	}
	/**
	 * Draws the board.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		board.paintComponent(g);
	}
	/**
	 * Creates the file menu.
	 * @return - JMenu object.
	 */
	public JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	private JMenuItem createDetectiveNotesItem() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	public JPanel createMyCardsDisplay() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(3,1));
		
		JPanel people = new JPanel();
		JPanel rooms = new JPanel();
		JPanel weapons = new JPanel();
		for (Card c: board.getHumanPlayer().getMyCards()) {
			switch (c.getType()) {
			case PERSON:
				JTextArea text1 = new JTextArea(1,15);
				text1.setText(c.getCardName());
				text1.setEditable(false);
				people.add(text1);
				break;
			case ROOM:
				JTextArea text2 = new JTextArea(1,15);
				text2.setText(c.getCardName());
				text2.setEditable(false);
				rooms.add(text2);
				break;
			case WEAPON:
				JTextArea text3 = new JTextArea(1,15);
				text3.setText(c.getCardName());
				text3.setEditable(false);
				weapons.add(text3);
				break;
			}
		}
		people.setLayout(new GridLayout(0, 1));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		rooms.setLayout(new GridLayout(0, 1));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		weapons.setLayout(new GridLayout(0, 1));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		panel.add(people);
		panel.add(rooms);
		panel.add(weapons);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		return panel;
	}
	/**
	 * Main for the game.
	 * @param args - Not used.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("ClueGame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ClueGame gameBoard = new ClueGame();
		gameBoard.setPreferredSize(new Dimension(gameBoard.board.getNumColumns()*BoardCell.WIDTH, gameBoard.board.getNumRows()*BoardCell.HEIGHT));
		JPanel myCardsDisplay = gameBoard.createMyCardsDisplay();
		ControlGUI gui = new ControlGUI();
		frame.add(myCardsDisplay, BorderLayout.EAST);
		frame.add(gui, BorderLayout.SOUTH);
		frame.add(gameBoard, BorderLayout.WEST);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gameBoard.createFileMenu());
		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setVisible(true);
		JFrame splashScreen = new JFrame();
		JOptionPane.showMessageDialog(splashScreen, "You are " + gameBoard.board.getHumanPlayer().getPlayerName() + ", press okay to begin." , "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		

	}

}