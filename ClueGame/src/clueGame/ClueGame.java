package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	private ControlGUI gui;
	private int currentPlayer, roll;
	private boolean humanPlayerTurn = true;
	private Random rng = new Random();
	private GuessDialog guessDialog;
	private AccuseDialog accuseDialog;
	private static ClueGame theInstance = new ClueGame();
	
	/**
	 * Get the singleton.
	 * @return - ClueGame object
	 */
	public static ClueGame getInstance() {
		return theInstance;
	}

	/**
	 * Sets up the board and the dialog.
	 * Sets initial turn data.
	 */
	public ClueGame() {
		setBackground (Color.BLACK);
		board = Board.getInstance();
		board.setConfigFiles("ClueLayoutCSV.csv", "ClueRooms.txt", "CluePeople.txt", "ClueWeapons.txt");		
		board.initialize();
		
		// Human Player starts game.
		for (int i = 0; i < board.getPlayers().size(); i++) {
			if (board.getPlayers().get(i) instanceof HumanPlayer) {
				currentPlayer = i;
			}
		}
		roll = rng.nextInt(6) + 1;
		dialog = new DetectiveNotesDialog();
		guessDialog = new GuessDialog();
		accuseDialog = new AccuseDialog();
		accuseDialog.pack();
		dialog.pack();
		guessDialog.pack();
		gui = ControlGUI.getInstance();
		gui.setRoll(String.valueOf(roll));
		gui.setCurrentPlayer(board.getPlayers().get(currentPlayer).getPlayerName());
		addMouseListener(new CellListener());
		gui.nextPlayer.addActionListener(new NextListener());
		gui.accuse.addActionListener(new AccuseListener());
	}
	
	/**
	 * Set the human player turn to true or false.
	 * @param humanPlayerTurn - Boolean value
	 */
	public void setHumanPlayerTurn(boolean humanPlayerTurn) {
		this.humanPlayerTurn = humanPlayerTurn;
	}

	/**
	 * Gets the gui displaying Control Panel.
	 * @return - ControlGUI object
	 */
	public ControlGUI getGui() {
		return gui;
	}

	/**
	 * Draws the board.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		board.paintComponent(g);
	}
	
	private class NextListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gui.setRoll(String.valueOf(roll));
			gui.setCurrentPlayer(board.getPlayers().get(currentPlayer).getPlayerName());
			if (board.getPlayers().get(currentPlayer) instanceof HumanPlayer) {
				humanPlayerTurn = true;
				board.currentPlayerTurn(currentPlayer, roll);
				repaint();
			} else {
				board.currentPlayerTurn(currentPlayer, roll);
				repaint();
				currentPlayer = (currentPlayer + 1) % board.getPlayers().size();
				roll = rng.nextInt(6) + 1;
			}
		}
	}
	
	private class AccuseListener implements ActionListener {
		/**
		 * Action for button press.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (humanPlayerTurn && !accuseDialog.isSubmitted()) {
				accuseDialog.setVisible(true);
				repaint();
			} else {
				JFrame infoScreen = new JFrame();
				JOptionPane.showMessageDialog(infoScreen, "You must wait for your next turn to make an accusation.", "Error" , JOptionPane.INFORMATION_MESSAGE);
			}
		}
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
	
	/**
	 * Creates JPanel displaying the player's cards.
	 * @return - JPanel component
	 */
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
	
	private class CellListener implements MouseListener {
		/**
		 * Action for cell clicked.
		 */
		public void mousePressed (MouseEvent event) {
			if (humanPlayerTurn) {
				BoardCell clicked = board.getTargetClicked(event.getX(), event.getY());
				if (clicked != null) {
					board.getHumanPlayer().setRow(clicked.getRow());
					board.getHumanPlayer().setColumn(clicked.getColumn());
					for (BoardCell cell : board.getTargets()) {
						cell.setTarget(false);
					}
					roll = rng.nextInt(6) + 1;
					repaint();
					if (clicked.isDoorway()) {
						guessDialog.setRoomName(board.getLegend().get(board.getCellAt(clicked.getRow(), clicked.getColumn()).getInitial()));
						guessDialog.setVisible(true);
					}
					currentPlayer = (currentPlayer + 1) % board.getPlayers().size();
					humanPlayerTurn = false;
					repaint();
				} else {
					JFrame infoScreen = new JFrame();
					JOptionPane.showMessageDialog(infoScreen, "You cannot go there", "Error" , JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		public void mouseExited (MouseEvent event) {}
		public void mouseClicked (MouseEvent event) {}		
	}
	
	/**
	 * Set the index of the current player.
	 * @param currentPlayer - Integer object
	 */
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Gets index of current player.
	 * @return - int
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Gets the current die roll.
	 * @return - int
	 */
	public int getCurrentRoll() {
		return roll;
	}
	
	/**
	 * Main for the game.
	 * @param args - Not used.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("ClueGame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ClueGame gameBoard = ClueGame.getInstance();
		gameBoard.setPreferredSize(new Dimension(gameBoard.board.getNumColumns()*BoardCell.WIDTH, gameBoard.board.getNumRows()*BoardCell.HEIGHT));
		gameBoard.board.currentPlayerTurn(gameBoard.getCurrentPlayer(), gameBoard.getCurrentRoll());
		JPanel myCardsDisplay = gameBoard.createMyCardsDisplay();
		
		frame.add(myCardsDisplay, BorderLayout.EAST);
		frame.add(gameBoard.getGui(), BorderLayout.SOUTH);
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
