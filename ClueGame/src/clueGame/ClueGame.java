package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
	/**
	 * Main for the game.
	 * @param args - Not used.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("ClueGame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ClueGame gameBoard = new ClueGame();
		gameBoard.setPreferredSize(new Dimension(gameBoard.board.getNumColumns()*BoardCell.WIDTH, gameBoard.board.getNumRows()*BoardCell.HEIGHT));
		
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.SOUTH);
		frame.add(gameBoard, BorderLayout.NORTH);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gameBoard.createFileMenu());
		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setVisible(true);

	}

}
