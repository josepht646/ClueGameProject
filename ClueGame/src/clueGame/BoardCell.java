package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The BoardCell class represents a cell in a board with row, column, label, and door direction.
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDir;
	private boolean isWalkway, isName;
	public static final int WIDTH = 25;
	public static final int HEIGHT = 25;
	
	/**
	 * Construct the cell with zeros.
	 */
	public BoardCell() {
		super();
		row = 0;
		column = 0;
		initial = '\0';
		doorDir = DoorDirection.NONE;
		isWalkway = false;
		isName = false;
	}
	
	/**
	 * Construct the cell with specific row, column, label, and door direction
	 * @param row - Index of row
	 * @param column - Index of column
	 * @param initial - Character assigned to cell
	 * @param doorDir - Direction of door
	 * @param isWalkway - If cell is a walkway or not
	 */
	public BoardCell(int row, int column, char initial, DoorDirection doorDir, boolean isWalkway, boolean isName) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDir = doorDir;
		this.isWalkway = isWalkway;
		this.isName = isName;
	}
	
	/**
	 * Set row.
	 * @param row - Index of row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Set column.
	 * @param column - Index of column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * Get row.
	 * @return - Index of cell row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Get column.
	 * @return - Index of cell column
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Get Door Direction.
	 * @return - Direction of door, or NONE if not a door
	 */
	public DoorDirection getDoorDirection() {
		return doorDir;
	}
	
	/**
	 * Get character representing board cell.
	 * @return - Character for the cell
	 */
	public char getInitial() {
		return initial;
	}
	
	/**
	 * Test whether BoardCell is doorway.
	 * @return - True if cell is a doorway
	 */
	public boolean isDoorway() {
		return doorDir != DoorDirection.NONE;
	}
	
	/**
	 * Test whether BoardCell is a walkway.
	 * @return - If cell is a walkway
	 */
	public boolean isWalkway() {
		return isWalkway;
	}
	
	/**
	 * Get data for cell as a string.
	 */
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", doorDir=" + doorDir
				+ ", isWalkway=" + isWalkway + "]";
	}
	
	public void draw(Graphics g) {
		int startX = column*WIDTH;
		int startY = row*HEIGHT;
		if (isWalkway) {
			g.setColor(Color.ORANGE);
			g.fillRect(startX, startY, WIDTH, HEIGHT);
			g.setColor(Color.BLACK);
			g.drawLine(startX, startY, startX + WIDTH - 1, startY);
			g.drawLine(startX, startY, startX, startY+HEIGHT);
			
		} else if (!isDoorway()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(startX, startY, WIDTH, HEIGHT);
			if (isName) {
				Board board = Board.getInstance();
				g.setColor(Color.BLUE);
				g.drawString(board.getLegend().get(initial), startX, startY+HEIGHT );
			}
		} else {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(startX, startY, WIDTH, HEIGHT);
			g.setColor(Color.BLUE);
			switch(doorDir) {
			case UP:
				g.fillRect(startX, startY, WIDTH, HEIGHT / 5);
				break;
			case DOWN:
				g.fillRect(startX, startY + HEIGHT * 4 / 5, WIDTH, HEIGHT / 5);
				break;
			case LEFT:
				g.fillRect(startX, startY, WIDTH / 5, HEIGHT);
				break;
			case RIGHT:
				g.fillRect(startX + WIDTH * 4 / 5, startY, WIDTH / 5, HEIGHT);
				break;
			case NONE:
			}
			
		}
		
		
		
	}
}
