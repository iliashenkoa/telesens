package com.yahoo.ailiashenko.mod1;

public class Demo {
	public static void main(String[] args) {

		SquareMatrix a = SquareMatrixFactory.getMatrix("Storage1D", 4, 4);
		SquareMatrix b = SquareMatrixFactory.getMatrix("Storage1D", 4, 4);
		SquareMatrix c = SquareMatrixFactory.getMatrix("Storage2D", 4, 4);

		a.insertRandom();
		b.insertRandom();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				c.setValueAt(i, j, 8);
			}
		}

		c.setValueAt(0, 0, 3);
		c.setValueAt(0, 1, 5);
		c.setValueAt(1, 0, 6);
		c.setValueAt(1, 1, 4);
		c.setValueAt(2, 1, 7);
		c.setValueAt(3, 0, 7);
		c.setValueAt(3, 1, 14);
		c.setValueAt(3, 2, 3);

		System.out.println(a.toString());
		a.print(false);
		System.out.println(b.toString());
		b.print(false);
		System.out.println("Mul of a and b: ");
		a.multiply(b).print(false);

		System.out.println(c.toString());
		c.print(false);
		System.out.println("Determinant of c matrix: " + c.computeDeterminant());

		SquareMatrix d = c.inversion();
		System.out.println("Inversion: ");
		d.print(false);
		System.out.println("A^-1*A = E ? : ");
		d.multiply(c).print(true);

	}
}
