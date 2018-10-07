package experiment;

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

}
