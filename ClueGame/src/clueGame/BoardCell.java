package clueGame;

/**
 * The BoardCell class represents a cell in a board with row, column, label, and door direction.
 * 
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDir;
	private boolean isWalkway;
	
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
	}
	
	/**
	 * Construct the cell with specific row, column, label, and door direction
	 * @param row - Index of row
	 * @param column - Index of column
	 * @param initial - Character assigned to cell
	 * @param doorDir - Direction of door
	 * @param isWalkway - If cell is a walkway or not
	 */
	public BoardCell(int row, int column, char initial, DoorDirection doorDir, boolean isWalkway) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDir = doorDir;
		this.isWalkway = isWalkway;
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
	 * Get data for cell as a string.
	 */
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", doorDir=" + doorDir
				+ ", isWalkway=" + isWalkway + "]";
	}
}
