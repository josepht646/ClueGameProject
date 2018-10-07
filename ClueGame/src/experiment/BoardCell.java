package experiment;

public class BoardCell {
	private int row;
	private int column;
	
	
	public BoardCell() {
		super();
		row = 0;
		column = 0;
	}

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
