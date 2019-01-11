package com.yahoo.ailiashenko.mod1;

public class SubMatrix extends SquareMatrix {
	private final SquareMatrix matrix;
	private final int excludingRowIndex;
	private final int excludingColumnIndex;

	public SubMatrix(SquareMatrix matrix, int excludingRowIndex, int excludingColumnIndex) {
		super();
		this.matrix = matrix;
		this.excludingRowIndex = excludingRowIndex;
		this.excludingColumnIndex = excludingColumnIndex;
	}

	@Override
	public double getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < this.excludingRowIndex) {
			if (columnIndex < this.excludingColumnIndex) {
				return this.matrix.getValueAt(rowIndex, columnIndex);
			} else {
				return this.matrix.getValueAt(rowIndex, columnIndex + 1);
			}
		} else {
			if (columnIndex < this.excludingColumnIndex) {
				return this.matrix.getValueAt(rowIndex + 1, columnIndex);
			} else {
				return this.matrix.getValueAt(rowIndex + 1, columnIndex + 1);
			}
		}
	}

	@Override
	public int getRowCount() {
		return this.matrix.getRowCount() - 1;
	}

	@Override
	public int getColumnCount() {
		return this.matrix.getColumnCount() - 1;
	}

	@Override
	public void setValueAt(int rowIndex, int columnIndex, double value) {
	}

	@Override
	public SquareMatrix createMatrix(int row, int column) {
		return new SubMatrix(this, row, column);
	}

}
