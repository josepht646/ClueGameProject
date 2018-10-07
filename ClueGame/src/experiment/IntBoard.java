package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The IntBoard class creates the board of cells and builds the adjacency list.
 * 
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class IntBoard {
	private BoardCell[][] grid;
	private HashMap<BoardCell, HashSet<BoardCell>> adjMtx;
	private HashSet<BoardCell> visited, targets;
	
	/**
	 * Construct the board and allocate memory.
	 */
	public IntBoard() {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[4][4];
		for (int i = 0; i < grid.length; ++i) {
			for(int j = 0; j < grid[i].length; ++j) {
				grid[i][j] = new BoardCell(i,j);
				
			}
		}
		calcAdjacencies();
	}
	
	/**
	 * Build the adjacency list.
	 */
	private void calcAdjacencies() {
		adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid[i].length; ++j) {
				adjMtx.put(grid[i][j], new HashSet<BoardCell>());
				if (i - 1 >= 0) {
					adjMtx.get(grid[i][j]).add(grid[i - 1][j]);
				}
				if (j + 1 < grid[i].length) {
					adjMtx.get(grid[i][j]).add(grid[i][j + 1]);
				}
				if (i + 1 < grid.length) {
					adjMtx.get(grid[i][j]).add(grid[i + 1][j]);
				}
				if (j - 1 >= 0) {
					adjMtx.get(grid[i][j]).add(grid[i][j - 1]);
				}
			}
		}
	}
	
	/**
	 * Get cell at row and column.
	 * @param i - Row
	 * @param j - Column
	 * @return - Cell at i'th row and j'th column
	 */
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}
	
	/**
	 * Calculate the targets that are i units away from the specified cell.
	 * @param cell
	 * @param i
	 */
	public void calcTargets(BoardCell cell, int i) {
		targets.clear();
		visited.clear();
		findAllTargets(cell, i);
	}
	
	private void findAllTargets(BoardCell cell, int i) {
		for (BoardCell myCell : adjMtx.get(cell)) {
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
		return adjMtx.get(cell);
	}
}
