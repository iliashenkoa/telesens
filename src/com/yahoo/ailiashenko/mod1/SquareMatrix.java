package com.yahoo.ailiashenko.mod1;

import java.util.Random;

public abstract class SquareMatrix {

	public abstract double getValueAt(int row, int column);

	public abstract int getRowCount();

	public abstract int getColumnCount();

	public abstract SquareMatrix createMatrix(int row, int column);

	public abstract void setValueAt(int rowIndex, int columnIndex, double value);

	public final boolean isSquare() {
		return (getRowCount() == getColumnCount());
	}

	public final double computeDeterminant() {
		if (!isSquare()) {
			throw new IllegalArgumentException("not square");
		}
		return determinant(this);
	}

	private static double determinant(SquareMatrix squareMatrix) {
		int squareMatrixSize = squareMatrix.getRowCount();
		if (squareMatrixSize == 1) {
			return squareMatrix.getValueAt(0, 0);
		} else {
			double det = 0.0;
			for (int j = 0; j < squareMatrixSize; j++) {
				SubMatrix minorMatrix = new SubMatrix(squareMatrix, 0, j);
				det += Math.pow(-1, j) * squareMatrix.getValueAt(0, j) * determinant(minorMatrix);
			}
			return det;
		}
	}

	public SquareMatrix multiply(SquareMatrix bMatrix) {
		if (this.getColumnCount() != bMatrix.getRowCount()) {
			return null;
		}
		double sum;
		SquareMatrix resMatrix = createMatrix(this.getRowCount(), bMatrix.getColumnCount());
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < bMatrix.getColumnCount(); j++) {
				sum = 0.0;
				for (int k = 0; k < this.getColumnCount(); k++) {
					sum += this.getValueAt(i, k) * bMatrix.getValueAt(k, j);
				}
				resMatrix.setValueAt(i, j, sum);
			}
		}
		return resMatrix;
	}

	public SquareMatrix inversion() {
		SquareMatrix resMatrix = createMatrix(this.getRowCount(), this.getColumnCount());
		double determ = determinant(this);
		double minor;
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				minor = determinant(new SubMatrix(this, i, j));
				if (determ != 0) {
					resMatrix.setValueAt(j, i, (1 / determ) * Math.pow(-1, (double) i + j) * minor);
				}
			}
		}
		return resMatrix;
	}

	public SquareMatrix insertRandom() {
		int min = 10;
		int max = 150;
		Random r = new Random();
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				double x = r.nextInt((max - min) + 1);
				this.setValueAt(i, j, x);
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[rowCount=" + getRowCount() + ", columnCount=" + getColumnCount() + "]";
	}

	public void print(boolean round) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.getRowCount(); i++) {
			for (int j = 0; j < this.getColumnCount(); j++) {
				if (round) {
					sb.append(Math.round(this.getValueAt(i, j)) + " ");
				} else {
					sb.append(this.getValueAt(i, j) + " ");
				}
			}
			sb.append(System.lineSeparator());
		}
		System.out.println(sb.toString());
	}

}
