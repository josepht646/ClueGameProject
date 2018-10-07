package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] grid;
	private HashMap<BoardCell, HashSet<BoardCell>> adjMtx;
	private HashSet<BoardCell> visited, targets;
	
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
	
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}
	
	public void calcTargets(BoardCell cell, int i) {
		for (BoardCell myCell : adjMtx.get(cell)) {
			if (!visited.contains(myCell)) {
				visited.add(myCell);
				if (i == 1) {
					targets.add(myCell);
				} else {
					calcTargets(myCell, i - 1);
				}
				visited.remove(myCell);
			}
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	public static void main() {
		IntBoard myBoard = new IntBoard();
	}
}
