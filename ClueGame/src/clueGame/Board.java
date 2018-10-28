package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

/**
 * The board class loads two configuration files to generate a clue game board.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class Board {
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private char walkwayChar;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;    // Adjacency list for objects on the board.
	private Set<BoardCell> visited, targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();    // Variable used for singleton pattern.
	
	private Board() {}    // Constructor is private to ensure only one can be created.
	
	/**
	 * Get the singleton board.
	 * @return - Board object representing the single instance of the Board class.
	 */
	public static Board getInstance() {
		return theInstance;
	}
	
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
	 * Loads the legend file.
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 */
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		legend = new HashMap<Character, String>();
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
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.NONE, true);
					} else {
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.NONE, false);
					}
				} else if (entries[col].length() == 2) {    // Else if cell ID is length 2, it must be a door or room label.
					char dir = entries[col].charAt(1);
					switch(dir) {
					case 'R':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.RIGHT, false);
					break;
					case 'L':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.LEFT, false);
					break;
					case 'U':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.UP, false);
					break;
					case 'D':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.DOWN, false);
					break;
					case 'N':
						board[row][col] = new BoardCell(row, col, entries[col].charAt(0), DoorDirection.NONE, false);
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
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				adjMatrix.put(board[i][j], new HashSet<BoardCell>());
				if (!board[i][j].isWalkway() && !board[i][j].isDoorway()) {    // If cell is a room, no positions to move to.
					continue;
				}
				
				if (board[i][j].isWalkway()) {    // If cell is walkway, can move to another walkway or enter door in correct direction.
					if (i - 1 >= 0 && (board[i-1][j].isWalkway() || (board[i-1][j].isDoorway() && board[i-1][j].getDoorDirection() == DoorDirection.DOWN))) {
						adjMatrix.get(board[i][j]).add(board[i - 1][j]);
					}
					if (j + 1 < board[i].length && (board[i][j+1].isWalkway() || (board[i][j+1].isDoorway() && board[i][j+1].getDoorDirection() == DoorDirection.LEFT))) {
						adjMatrix.get(board[i][j]).add(board[i][j + 1]);
					}
					if (i + 1 < board.length && (board[i+1][j].isWalkway() || (board[i+1][j].isDoorway() && board[i+1][j].getDoorDirection() == DoorDirection.UP))) {
						adjMatrix.get(board[i][j]).add(board[i + 1][j]);
					}
					if (j - 1 >= 0 && (board[i][j-1].isWalkway() || (board[i][j-1].isDoorway() && board[i][j-1].getDoorDirection() == DoorDirection.RIGHT))) {
						adjMatrix.get(board[i][j]).add(board[i][j - 1]);
					}
				} else {    // Cell must be a door (we assume the cell in the door direction is a walkway).
					switch(board[i][j].getDoorDirection()) {
					case DOWN:
						adjMatrix.get(board[i][j]).add(board[i + 1][j]);
						break;
					case LEFT:
						adjMatrix.get(board[i][j]).add(board[i][j - 1]);
						break;
					case NONE:
						break;
					case RIGHT:
						adjMatrix.get(board[i][j]).add(board[i][j + 1]);
						break;
					case UP:
						adjMatrix.get(board[i][j]).add(board[i - 1][j]);
						break;
					default:
						break;
					}
				}
			}
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
	 * Recursive function to find all possible positions for player to move to (the targets list).
	 * @param cell - Board Cell to calculate targets from
	 * @param pathLength - The number of steps to take when calculating target list
	 * @param startingRoom - The character of the room where the player starts
	 */
	private void findAllTargets(BoardCell cell, int pathLength, char startingRoom) {
		for (BoardCell myCell : adjMatrix.get(cell)) {
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
	 * Get the targets list for a cell.
	 * @return - Set containing targets
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/**
	 * Get the adjacency list for a specified cell.
	 * @param cell - cell to get the adjacency list for.
	 * @return - Set of Board Cells adjacent to cell
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMatrix.get(cell);
	}
	
	/**
	 * Loads both configuration files.
	 */
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the legend.
	 * @return - Map representing the legend.
	 */
	public Map<Character, String> getLegend() {
		return legend;
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
	 * Return board cell at location row i, column j.
	 * @param row - Index of row number
	 * @param col - Index of column number
	 * @return - Board cell object
	 */
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
	/**
	 * Get the adjacency list at row i column j.
	 * @param row - Index of row number
	 * @param col - Index of column number
	 * @return - Set of Board Cells adjacent to cell at i,j
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		return getAdjList(getCellAt(row,col));
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
}
