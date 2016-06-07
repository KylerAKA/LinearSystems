package lin;

public class Matrix {
	int[][] matrix;
	
	final int n, m;
	
	public Matrix(int rows, int cols) {
		m = rows;
		n = cols;
		matrix = new int[n][m];
	}
	
	public Matrix(int[][] initial) {
		m = initial.length;
		n = initial[0].length;
		for (int r = 0; r < m; r++)
			for (int c = 0; c < n; c++)
				matrix[r][c] = initial[r][c];
	}
	
	public int[] getRow(int a) {
		int[] row = null;
		try {
			row = matrix[a];
		}
		catch (IndexOutOfBoundsException e) {
			System.err.print("Matrix has " + m + " rows, attempted " + a + " on getRow()");
		}
		return row;
	}
	
	public int[] getColm(int b) {
		int[] colm = new int[n];
		try {
			for (int i = 0; i <= n; i++) {
				colm[i] = matrix[i][b];
			}
		}
		catch (IndexOutOfBoundsException e) {
			System.err.print("Matrix has " + n + " columns, attempted " + b + " on getColm()");
		}
		return colm;
	}
	
	public boolean isSquare() {
		return n == m;
	}
	
	public int getNumRow() {
		return m;
	}
	
	public int getNumColm() {
		return n;
	}
	
	public boolean setItem(int r, int c, int a) {
		try {
			matrix[r][c] = a;
		}
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	
	public int getItem(int r, int c) {
		try {
			return matrix[r][c];
		}
		catch (IndexOutOfBoundsException e) {
			System.err.print("Matrix is (" + m + "x" + n + "), attempted [" + r + ", " + c
				+ "] on getItem()");
		}
		return -1;
	}
	
	public String toString() {
		String s = "[";
		for (int[] r: matrix) {
			s += "[";
			for (int a: r)
				s += a + "\t";
			s += "],\n";
		}
		s += "]";
		
		return s;
	}
}
