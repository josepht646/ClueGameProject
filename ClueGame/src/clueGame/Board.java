package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.BoardCell;

/**
 * The board class loads two configuration files to generate a clue game board.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class Board extends JPanel {
	private static Board theInstance = new Board();    // Variable used for singleton pattern.
	private String boardConfigFile, roomConfigFile, peopleConfigFile, weaponConfigFile;
	private int numRows;
	private int numColumns;
	private Map<Character, String> legend;
	private char walkwayChar;
	private BoardCell[][] board;
	private Map<BoardCell, Set<BoardCell>> adjList;    // Adjacency list for objects on the board.
	private Set<BoardCell> visited, targets;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	private Solution theAnswer;
	public Random rng = new Random();
	/**
	 * Constructor is private to ensure only one instance can be created.
	 */
	private Board() {}
	
	/**
	 * Sets file names.
	 * @param fileCSV - name of the configuration file for the board
	 * @param legendFile - name of the configuration file for the legend
	 */
	public void setConfigFiles(String fileCSV, String legendFile) {
		boardConfigFile = fileCSV;
		roomConfigFile = legendFile;
	}
	/**
	 * Sets file names.
	 * @param fileCSV - name of the configuration file for the board
	 * @param legendFile - name of the configuration file for the legend
	 * @param weaponFile 
	 * @param peopleFile 
	 */
	public void setConfigFiles(String fileCSV, String legendFile, String peopleFile, String weaponFile) {
		boardConfigFile = fileCSV;
		roomConfigFile = legendFile;
		peopleConfigFile = peopleFile;
		weaponConfigFile = weaponFile;
	}
	/**
	 * Get the singleton board.
	 * @return - Board object representing the single instance of the Board class.
	 */
	public static Board getInstance() {
		return theInstance;
	}
	
	/**
	 * Get number of rows.
	 * @return - Integer representing the number of rows on the board.
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * Get number of columns.
	 * @return - Integer representing the number of columns on the board.
	 */
	public int getNumColumns() {
		return numColumns;
	}
	
	/**
	 * Gets the legend.
	 * @return - Map representing the legend.
	 */
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	/**
	 * Return board cell at location row i, column j.
	 * @param row - Index of row number
	 * @param col - Index of column number
	 * @return - Board cell object
	 */
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
	/**
	 * Get the adjacency list for a specified cell.
	 * @param cell - cell to get the adjacency list for.
	 * @return - Set of Board Cells adjacent to cell
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjList.get(cell);
	}
	
	/**
	 * Get the adjacency list at row and column.
	 * @param row - Index of row number
	 * @param col - Index of column number
	 * @return - Set of Board Cells adjacent to cell at row, col
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		return getAdjList(getCellAt(row, col));
	}
	
	/**
	 * Get the targets list for a cell.
	 * @return - Set containing targets
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/**
	 * Get the players.
	 * @return - ArrayList of players.
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Return the player controlled by the user.
	 * @return - HumanPlayer object
	 */
	public HumanPlayer getHumanPlayer() {
		for (Player p: players) {
			if (p instanceof HumanPlayer) {
				return (HumanPlayer) p;
			}
		}
		return null;
	}
	/**
	 * Loads both configuration files.
	 */
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			if (peopleConfigFile != null) {
				loadConfigFiles();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Loads the legend file.
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 */
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		legend = new HashMap<Character, String>();
		deck = new ArrayList<Card>();
		FileReader reader = new FileReader(roomConfigFile);    // Open the roomConfigFile to read data for the legend.
		Scanner readerScanner = new Scanner(reader);
		while (readerScanner.hasNextLine()) {
			String[] entries = readerScanner.nextLine().split(", ");    // Split each line using commas as delimiters and parse each entry.
			if(entries.length != 3 || entries[0].length() != 1 || entries[1].length() == 0 || (!entries[2].equals("Other") && !entries[2].equals("Card"))) {
				readerScanner.close();
				throw new BadConfigFormatException(roomConfigFile);
			} else {
				legend.put(entries[0].charAt(0), entries[1]);
				if (entries[1].equals("Walkway")) {
					walkwayChar = entries[0].charAt(0);
				}
				if (entries[2].equals("Card")) {
					deck.add(new Card(entries[1], CardType.ROOM));
				}
			}
		}
		readerScanner.close();
	}
	
	/**
	 * Loads the board file.
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 */
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		FileReader reader = new FileReader(boardConfigFile);    // Open the boardConfigFile to read data for the game board.
		Scanner readerScanner = new Scanner(reader);
		numRows = 0;
		numColumns = 0;
		while (readerScanner.hasNextLine()) {    // Go through the file once and count the lines so we can allocate the correct amount of space for the board.
			readerScanner.nextLine();
			++numRows;
		}
		readerScanner.close();
		
		board = new BoardCell[numRows][];
		reader = new FileReader(boardConfigFile);
		readerScanner = new Scanner(reader);
		int row = 0;
		while (readerScanner.hasNextLine()) {
			String[] entries = readerScanner.nextLine().split(",");    // Split each line using commas as delimiters and parse each entry.
			if (numColumns == 0) {    // Make sure each line has same number of entries.
				numColumns = entries.length;
			} else if (entries.length != numColumns) {
				readerScanner.close();
				throw new BadConfigFormatException(boardConfigFile);
			}
			
			board[row] = new BoardCell[numColumns];
			for (int col = 0; col < numColumns; ++col) {    // Add cell for each column in the row.
				if (!legend.containsKey(entries[col].charAt(0))) {    // If cell is not valid, throw an error.
					readerScanner.close();
					throw new BadConfigFormatException(boardConfigFile);
				}
				if (entries[col].length() == 1) {    // Else if cell ID is length 1, it must be a walkway/room/closet and not a door.
					if (entries[col].charAt(0) == walkwayChar) {
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.NONE, true, false);
					} else {
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.NONE, false, false);
					}
				} else if (entries[col].length() == 2) {    // Else if cell ID is length 2, it must be a door or room label.
					char dir = entries[col].charAt(1);
					switch(dir) {
					case 'R':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.RIGHT, false, false);
					break;
					case 'L':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.LEFT, false, false);
					break;
					case 'U':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.UP, false, false);
					break;
					case 'D':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.DOWN, false, false);
					break;
					case 'N':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.NONE, false, true);
					break;
					default:
						readerScanner.close();
						throw new BadConfigFormatException(boardConfigFile);
					}
				} else {    // Else, its not valid.
					readerScanner.close();
					throw new BadConfigFormatException(boardConfigFile);
				}	
			}
			++row;
		}
		readerScanner.close();
		calcAdjacencies();    // Calculate adjacency list once all cells are loaded.
	}
	
	/**
	 * Build the adjacency list.
	 */
	public void calcAdjacencies() {
		adjList = new HashMap<BoardCell, Set<BoardCell>>();
		for (int row = 0; row < board.length; ++row) {
			for (int col = 0; col < board[row].length; ++col) {
				adjList.put(board[row][col], new HashSet<BoardCell>());
				if (!board[row][col].isWalkway() && !board[row][col].isDoorway()) {    // If cell is a room, no positions to move to.
					continue;
				}
				
				if (board[row][col].isWalkway()) {    // If cell is walkway, can move to another walkway or enter door in correct direction.
					addToAdjMatrix(board[row][col], row - 1, col, DoorDirection.DOWN);
					addToAdjMatrix(board[row][col], row, col + 1, DoorDirection.LEFT);
					addToAdjMatrix(board[row][col], row + 1, col, DoorDirection.UP);
					addToAdjMatrix(board[row][col], row, col - 1, DoorDirection.RIGHT);
				} else {    // Cell must be a door (we assume the cell in the door direction is a walkway).
					switch(board[row][col].getDoorDirection()) {
					case DOWN:
						adjList.get(board[row][col]).add(board[row + 1][col]);
						break;
					case LEFT:
						adjList.get(board[row][col]).add(board[row][col - 1]);
						break;
					case NONE:
						break;
					case RIGHT:
						adjList.get(board[row][col]).add(board[row][col + 1]);
						break;
					case UP:
						adjList.get(board[row][col]).add(board[row - 1][col]);
						break;
					default:
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Add a cell to the adjacency list if it lies on the board and is a walkway or is a doorway and matches the direction.
	 * @param currentCell - Current cell in adjacency list
	 * @param row - Row of target cell
	 * @param col - Column of target cell
	 * @param direction - Direction to match in the case of a door
	 */
	private void addToAdjMatrix(BoardCell currentCell, int row, int col, DoorDirection direction) {
		if (row >= 0 && row < board.length && col >= 0 && col < board[0].length && (board[row][col].isWalkway() || board[row][col].isDoorway() && board[row][col].getDoorDirection() == direction)) {
			adjList.get(currentCell).add(board[row][col]);
		}
	}
	
	/**
	 * Calculate the targets list at cell with pathLength steps.
	 * @param cell - Board Cell to calculate targets from
	 * @param pathLength - The number of steps to take when calculating target list
	 */
	public void calcTargets(BoardCell cell, int pathLength) {
		targets.clear();
		visited.clear();
		visited.add(cell);
		findAllTargets(cell, pathLength, cell.getInitial());
	}
	
	/**
	 * Calculate the targets list at row i column j with pathLength steps.
	 * @param row - Index of row number
	 * @param col - Index of column number
	 * @param pathLength - Distance to travel.
	 */
	public void calcTargets(int row, int col, int pathLength) {
		calcTargets(getCellAt(row,col), pathLength);
	}
	
	private void findAllTargets(BoardCell cell, int pathLength, char startingRoom) {
		for (BoardCell myCell : adjList.get(cell)) {
			if (!visited.contains(myCell)) {    // Check if cell has not been visited yet.
				visited.add(myCell);
				if (myCell.isDoorway()) {    // If its a doorway and not the same room where we started, its a target.
					if (myCell.getInitial() != startingRoom) {
						targets.add(myCell);
					}
				} else if (pathLength == 1) {    // Else if we reached end of path, add the cell as a target.
					targets.add(myCell);
				} else {    // Else, move on to the next tiles around the one we are at.
					findAllTargets(myCell, pathLength - 1, startingRoom);
				}
				visited.remove(myCell);
			}
		}
	}
	
	/**
	 * In development.
	 */
	public void selectAnswer() {
		
	}
	
	/**
	 * Handles the suggestion. Compares it to the players ability to disprove it.
	 * @return - Card that disproves suggestion, or null.
	 */
	public Card handleSuggestion(Solution suggestion, Player accuser) {
		ControlGUI.getInstance().setGuess(suggestion.toString());
		for (Player p : players) {
			if (p.getPlayerName().equals(suggestion.person)) {
				p.setRow(accuser.getRow());
				p.setColumn(accuser.getColumn());
			}
		}
		for (Player p : players) {
			if (p != accuser) {
				Card c = p.disproveSuggestion(suggestion);
				if (c != null) {
					ControlGUI.getInstance().setGuessResult(c.getCardName());
					Player.suggestionDisproven = true;
					boolean matchFound = false;
					for (Card c2 : accuser.getSeenCards()) {
						if (c2.getCardName().equals(c.getCardName())) {
							matchFound = true;
							break;
						}
					}
					if (!matchFound) {
						accuser.getSeenCards().add(c);
					}
					return c;
				}
			}
		}
		ControlGUI.getInstance().setGuessResult("None");
		Player.suggestionDisproven = false;
		return null;
	}
	
	/**
	 * Compares person, room, and weapon of accusation to those of the solution.
	 * @param accusation - Solution object representing accusation
	 * @return - True or False
	 */
	public boolean checkAccusation(Solution accusation) {
		return (accusation.person == theAnswer.person) && (accusation.room == theAnswer.room) && (accusation.weapon == theAnswer.weapon);
	}
	
	/**
	 * Loads the people and weapon configuration files.
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 */
	public void loadConfigFiles() throws BadConfigFormatException, FileNotFoundException {
		players = new ArrayList<Player>();
		FileReader peopleReader = new FileReader(peopleConfigFile);
		Scanner readerScanner = new Scanner(peopleReader);
		while (readerScanner.hasNextLine()) {
			String[] entries = readerScanner.nextLine().split(", ");    // Split each line using commas as delimiters and parse each entry.
			if(entries.length != 5 || entries[0].length() == 0 || convertColor(entries[1]) == null|| (!entries[2].equals("C") && !entries[2].equals("H"))) {
				readerScanner.close();
				throw new BadConfigFormatException(peopleConfigFile);
			} else {
				deck.add(new Card(entries[0], CardType.PERSON));
				if (entries[2].equals("C")) {
					players.add(new ComputerPlayer(entries[0], Integer.parseInt(entries[3]), Integer.parseInt(entries[4]), convertColor(entries[1])));
				} else {
					players.add(new HumanPlayer(entries[0], Integer.parseInt(entries[3]), Integer.parseInt(entries[4]), convertColor(entries[1])));
				}
				
			}
		}
		readerScanner.close();
		
		FileReader readWeapons = new FileReader(weaponConfigFile);
		Scanner weaponScanner = new Scanner(readWeapons);
		while (weaponScanner.hasNextLine()) {
			String currentWeapon = weaponScanner.nextLine();
			if(currentWeapon.length() == 0) {
				weaponScanner.close();
				throw new BadConfigFormatException(weaponConfigFile);
			} else {
				deck.add(new Card(currentWeapon, CardType.WEAPON));
				
				
				
			}
		}
		weaponScanner.close();
		dealCards();
	}
	
	/**
	 * Get deck of cards.
	 * @return - ArrayList of Cards
	 */
	public ArrayList<Card> getCards() {
		return deck;
	}

	/**
	 * Deals the cards to the players and creates the solution for the game.
	 */
	private void dealCards() {
		Collections.shuffle(deck);
		Card[] solutionCards = new Card[3];
		
		for (int i = 0; i < deck.size(); ++i) {
			if (solutionCards[0] == null && deck.get(i).getType() == CardType.PERSON) {
				solutionCards[0] = deck.get(i);
			} else if (solutionCards[1] == null && deck.get(i).getType() == CardType.ROOM) {
				solutionCards[1] = deck.get(i);
			} else if (solutionCards[2] == null && deck.get(i).getType() == CardType.WEAPON) {
				solutionCards[2] = deck.get(i);
			}
		}
		setTheAnswer(new Solution(solutionCards[0].getCardName(), solutionCards[1].getCardName(), solutionCards[2].getCardName()));
		Collections.shuffle(deck);
		
		int i = 0;
		for (Player p : players) {
			for (int j = 0; j < 3; ++j) {
				if (deck.get(i) != solutionCards[0] && deck.get(i) != solutionCards[1] && deck.get(i) != solutionCards[2]) {
					p.addCard(deck.get(i));
					
				} else {
					j -= 1;
				}
				i += 1;
			}
		}
	}
	

	/**
	 * Converts string to color
	 * @param strColor -String representing color
	 * @return - color object
	 */
	public Color convertColor(String strColor) {
		 Color color;
		 try {
		 // We can use reflection to convert the string to a color
		 Field field = Class.forName("java.awt.Color").getField(strColor.trim());
		 color = (Color)field.get(null);
		 } catch (Exception e) {
		 color = null; // Not defined
		 }
		 return color;
		}

	/**
	 * Sets the answer to the game.
	 * @param theAnswer - Solution Object
	 */
	public void setTheAnswer(Solution theAnswer) {
		this.theAnswer = theAnswer;
	}

	/**
	 * Gets the answer
	 * @return - Solution Object
	 */
	public Solution getTheAnswer() {
		return theAnswer;
	}
	
	/**
	 * Tells the BoardCells and Players to draw themselves.
	 */
	@Override
	public void paintComponent(Graphics g) {
		for (int i = numRows-1; i >= 0; i--) {
			for (int j = numColumns-1; j >= 0; j--) {
				board[i][j].draw(g);
			}
		}
		for (Player p: players) {
			p.draw(g);
		}
	}
	
	/**
	 * Sets up target list for current players turn and moves players controlled by computer.
	 */
	public void currentPlayerTurn(int currentPlayer, int roll) {
		ControlGUI.getInstance().setGuess("");
		ControlGUI.getInstance().setGuessResult("");
		calcTargets(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn(), roll);
		BoardCell cellLocation = players.get(currentPlayer).pickLocation(getTargets());
		if (players.get(currentPlayer) instanceof ComputerPlayer) {
			Solution accusation = ((ComputerPlayer) players.get(currentPlayer)).makeAccusation();
			if (accusation != null) {
				if (checkAccusation(accusation)) {
					JFrame winMessage = new JFrame();
					JOptionPane.showMessageDialog(winMessage, players.get(currentPlayer).getPlayerName() + " accused the correct person and won the game!" , "Another Player Won", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				} else {
					JFrame loseMessage = new JFrame();
					JOptionPane.showMessageDialog(loseMessage, players.get(currentPlayer).getPlayerName() + " accused the wrong person and lost :(" , "Rip", JOptionPane.INFORMATION_MESSAGE);
					players.remove(ClueGame.getInstance().getCurrentPlayer());
					ClueGame.getInstance().setCurrentPlayer(ClueGame.getInstance().getCurrentPlayer() - 1);
					cellLocation = null;
					Player.suggestionDisproven = true;
					if (players.size() == 0) {
						JFrame gameMessage = new JFrame();
						JOptionPane.showMessageDialog(gameMessage, "The game is over, the murderer was " + theAnswer.toString(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
				}
			}
			if (cellLocation != null) {
				players.get(currentPlayer).setRow(cellLocation.getRow());
				players.get(currentPlayer).setColumn(cellLocation.getColumn());
				if (cellLocation.isDoorway()) {
					handleSuggestion(((ComputerPlayer) players.get(currentPlayer)).createSuggestion(), players.get(currentPlayer));
				}
			}
		}
	}
	
	/**
	 * Gets the BoardCell clicked on by the mouse
	 * @param mouseX - mouse location x
	 * @param mouseY - mouse location y
	 * @return - BoardCell object
	 */
	public BoardCell getTargetClicked(int mouseX, int mouseY) {
		for (BoardCell cell : targets) {
			if (cell.containsClick(mouseX, mouseY)) {
				return cell;
			}
		}
		return null;
	}
}
