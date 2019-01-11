package com.yahoo.ailiashenko.mod1;

public class Storage2D extends SquareMatrix {
	private final double[][] data;
	private final int rowCount;
	private final int columnCount;

	public Storage2D(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;

		this.data = new double[rowCount][columnCount];
	}

	@Override
	public double getValueAt(int rowIndex, int columnIndex) {
		return this.data[rowIndex][columnIndex];
	}

	@Override
	public int getRowCount() {
		return this.rowCount;
	}

	@Override
	public int getColumnCount() {
		return this.columnCount;
	}

	public void setValueAt(int rowIndex, int columnIndex, double value) {
		this.data[rowIndex][columnIndex] = value;
	}

	@Override
	public SquareMatrix createMatrix(int row, int column) {
		return new Storage2D(row, column);
	}

}
