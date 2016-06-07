package lin;

public class Matrix {
	int[][] matrix;
	
	final int n, m;
	
	public Matrix(int rows, int cols) {
		m = rows;
		n = cols;
		matrix = new int[m][n];
	}
	
	public Matrix(int[][] initial) {
		m = initial.length;
		n = initial[0].length;
		matrix = new int[m][n];
		for (int r = 0; r < m; r++)
			for (int c = 0; c < n; c++)
				matrix[r][c] = initial[r][c];
	}
	
	public int[] getRow(int a) {
		int[] row = null;
		row = matrix[a];
		return row;
	}
	
	public int[] getColm(int b) {
		int[] colm = new int[n];
		for (int i = 0; i <= n; i++)
			colm[i] = matrix[i][b];
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
	
	public void setItem(int r, int c, int a) {
		matrix[r][c] = a;
	}
	
	public int getItem(int r, int c) {
		return matrix[r][c];
	}
	
	public String toString() {
		String s = "";
		for (int[] r: matrix) {
			s += "[";
			for (int a: r)
				s += a + "\t";
			s += "]\n";
		}
		
		return s;
	}
}
