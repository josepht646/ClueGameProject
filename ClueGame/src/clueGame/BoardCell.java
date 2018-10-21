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
	 * @param row
	 * @param column
	 * @param initial
	 * @param doorDir
	 * @param isWalkway
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
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Set column.
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * Get row.
	 * @return
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Get column.
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	/**
	 * Test whether BoardCell is doorway.
	 * @return
	 */
	public boolean isDoorway() {
		return doorDir != DoorDirection.NONE;
	}
	/**
	 * Test whether BoardCell is a walkway.
	 * @return
	 */
	public boolean isWalkway() {
		return isWalkway;
	}
	
	/**
	 * Get Door Direction.
	 * @return
	 */
	public DoorDirection getDoorDirection() {
		return doorDir;
	}
	/**
	 * Get character representing board cell.
	 * @return
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
