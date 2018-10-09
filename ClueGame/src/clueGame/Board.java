package clueGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * 
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
	private Set<BoardCell> targets;
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
		
	}
	
	public void loadRoomConfig() {
		
	}
	
	public void loadBoardConfig() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	/**
	 * 
	 * @param cell
	 * @param pathLength
	 */
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}

	public void initialize() {
		
	}

	public Map<Character, String> getLegend() {
		return new HashMap<Character, String>();
	}
	/**
	 * Get number of rows.
	 * @return
	 */
	public int getNumRows() {
		return 0;
	}
	/**
	 * Get number of columns.
	 * @return
	 */
	public int getNumColumns() {
		return 0;
	}
	/**
	 * Return board cell at location row i, column j.
	 * @param i
	 * @param j
	 * @return
	 */
	public BoardCell getCellAt(int i, int j) {
		return new BoardCell();
	}

}
