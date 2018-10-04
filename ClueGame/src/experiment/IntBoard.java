package experiment;

import java.util.HashMap;
import java.util.HashSet;

public class IntBoard {
	private BoardCell[][] grid;
	private HashMap<BoardCell, HashSet<BoardCell>> adjMtx;
	private HashSet<BoardCell> visited, targets;
	
	public IntBoard() {
		System.out.println("init");
		grid = new BoardCell[4][];
		for (int i = 0; i < grid.length; ++i) {
			grid[i] = new BoardCell[4];
		}
		System.out.println(grid.length);
		System.out.println(grid[0].length);
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
				
				System.out.println(adjMtx.get(grid[i][j]).size());
			}
		}
	}
	
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}
	
	public void calcTargets(BoardCell cell, int i) {
		
	}

	public HashSet getTargets() {
		return null;
	}

	public HashSet<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	public static void main() {
		IntBoard myBoard = new IntBoard();
	}
}
