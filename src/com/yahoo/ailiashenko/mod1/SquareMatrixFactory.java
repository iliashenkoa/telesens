package com.yahoo.ailiashenko.mod1;

public class SquareMatrixFactory {
	private SquareMatrixFactory() {
	}

	public static SquareMatrix getMatrix(String type, int rowI, int columnI) {
		if ("storage1d".equalsIgnoreCase(type))
			return new Storage1D(rowI, columnI);
		else if ("storage2d".equalsIgnoreCase(type))
			return new Storage2D(rowI, columnI);
		return null;
	}
}
