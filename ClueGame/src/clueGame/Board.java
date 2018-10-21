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
 * Extra credit is included.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class Board {
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> visited, targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public static final int MAX_BOARD_SIZE = 50;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	/**
	 * Get the singleton board.
	 * @return
	 */
	public static Board getInstance() {
		return theInstance;
	}
	/**
	 * Sets file names.
	 * @param fileCSV
	 * @param legendFile
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
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String[] entries = in.nextLine().split(", ");
			if(entries.length != 3 || entries[0].length() != 1 || entries[1].length() == 0
					|| (!entries[2].equals("Other")&&!entries[2].equals("Card"))) {
				in.close();
				throw new BadConfigFormatException(roomConfigFile);
			} else {
				legend.put(entries[0].charAt(0), entries[1]);
			}
		}
		in.close();
	}
	/**
	 * Loads the board file.
	 * @throws BadConfigFormatException
	 * @throws FileNotFoundException
	 */
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		numRows = 0;
		numColumns = 0;
		while (in.hasNextLine()) {
			in.nextLine();
			++numRows;
		}
		in.close();
		
		board = new BoardCell[numRows][];
		reader = new FileReader(boardConfigFile);
		in = new Scanner(reader);
		int i = 0;
		while (in.hasNextLine()) {
			String[] entries = in.nextLine().split(",");
			if (numColumns == 0) {
				numColumns = entries.length;
			} else if (entries.length != numColumns) {
				in.close();
				throw new BadConfigFormatException(boardConfigFile);
			}
			
			board[i] = new BoardCell[numColumns];
			for (int j = 0; j < numColumns; ++j) {
				if (!legend.containsKey(entries[j].charAt(0))) {
					in.close();
					throw new BadConfigFormatException(boardConfigFile);
				}
				if (entries[j].length() == 1) {
					board[i][j] = new BoardCell(i, j, entries[j].charAt(0), DoorDirection.NONE );
				} else if (entries[j].length() == 2) {
					char dir = entries[j].charAt(1);
					switch(dir) {
					case 'R':
						board[i][j] = new BoardCell(i, j, entries[j].charAt(0), DoorDirection.RIGHT );
					break;
					case 'L':
						board[i][j] = new BoardCell(i, j, entries[j].charAt(0), DoorDirection.LEFT );
					break;
					case 'U':
						board[i][j] = new BoardCell(i, j, entries[j].charAt(0), DoorDirection.UP );
					break;
					case 'D':
						board[i][j] = new BoardCell(i, j, entries[j].charAt(0), DoorDirection.DOWN );
					break;
					case 'N':
						board[i][j] = new BoardCell(i, j, entries[j].charAt(0), DoorDirection.NONE );
					break;
					default:
						in.close();
						throw new BadConfigFormatException(boardConfigFile);
					} 
				} else {
					in.close();
					throw new BadConfigFormatException(boardConfigFile);
				}
				
			}
		
			++i;
		}
		in.close();
		calcAdjacencies();
	}
	/**
	 * Build the adjacency list.
	 */
	public void calcAdjacencies() {
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				adjMatrix.put(board[i][j], new HashSet<BoardCell>());
				if (i - 1 >= 0) {
					adjMatrix.get(board[i][j]).add(board[i - 1][j]);
				}
				if (j + 1 < board[i].length) {
					adjMatrix.get(board[i][j]).add(board[i][j + 1]);
				}
				if (i + 1 < board.length) {
					adjMatrix.get(board[i][j]).add(board[i + 1][j]);
				}
				if (j - 1 >= 0) {
					adjMatrix.get(board[i][j]).add(board[i][j - 1]);
				}
			}
		}
	}
	/**
	 * 
	 * @param cell
	 * @param pathLength
	 */
	public void calcTargets(BoardCell cell, int pathLength) {
		targets.clear();
		visited.clear();
		findAllTargets(cell, pathLength);
	}
	
	private void findAllTargets(BoardCell cell, int i) {
		for (BoardCell myCell : adjMatrix.get(cell)) {
			if (!visited.contains(myCell)) {
				visited.add(myCell);
				if (i == 1) {
					targets.add(myCell);
				} else {
					findAllTargets(myCell, i - 1);
				}
				visited.remove(myCell);
			}
		}
	}
	
	/**
	 * Get the targets list for a cell.
	 * @return
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/**
	 * Get the adjacency list for a specified cell.
	 * @param cell
	 * @return
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Gets the legend.
	 * @return
	 */
	public Map<Character, String> getLegend() {
		return legend;
	}
	/**
	 * Get number of rows.
	 * @return
	 */
	public int getNumRows() {
		return numRows;
	}
	/**
	 * Get number of columns.
	 * @return
	 */
	public int getNumColumns() {
		return numColumns;
	}
	/**
	 * Return board cell at location row i, column j.
	 * @param i
	 * @param j
	 * @return
	 */
	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	public Set<BoardCell> getAdjList(int i, int j) {
		// TODO Auto-generated method stub
		HashSet<BoardCell> marker = new HashSet<BoardCell>();
		marker.add(new BoardCell());
		return marker;
	}
	public void calcTargets(int i, int j, int k) {
		// TODO Auto-generated method stub
		
	}

}
