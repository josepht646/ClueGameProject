package clueGame;

/**
 * The BoardCell class represents a cell in a board with row and column.
 * 
 * @author Joseph Thurston
 * @author Thomas Depke
 *
 */
public class BoardCell {
	private int row;
	private int column;
	private char initial;
	
	/**
	 * Construct the cell with zeros.
	 */
	public BoardCell() {
		super();
		row = 0;
		column = 0;
	}
	
	/**
	 * Construct the cell with specific row and column.
	 * @param row
	 * @param column
	 */
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
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
		return false;
	}
	/**
	 * Get Door Direction.
	 * @return
	 */
	public DoorDirection getDoorDirection() {
		return DoorDirection.NONE;
	}
	/**
	 * Get character representing board cell.
	 * @return
	 */
	public char getInitial() {
		return '0';
	}



}
