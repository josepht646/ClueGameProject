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
	
	/**
	 * Construct the cell with zeros.
	 */
	public BoardCell() {
		super();
		row = 0;
		column = 0;
		initial = '\0';
		doorDir = DoorDirection.NONE;
	}
	
	/**
	 * Construct the cell with specific row, column, label, and door direction
	 * @param row
	 * @param column
	 * @param initial
	 * @param doorDir
	 */
	public BoardCell(int row, int column, char initial, DoorDirection doorDir) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDir = doorDir;
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



}
