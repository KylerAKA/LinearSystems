package lin;

public class matrix {
	int[][] matrix;
	
	final int n, m;
	
	public matrix(int rows, int cols) {
		n = rows;
		m = cols;
		matrix = new int[n][m];
	}
	
	public matrix(int[][] initial) {
		matrix = initial;
		n = initial.length;
		m = initial[0].length;
	}
	
	public int[] getRow(int a) {
		int[] row = null;
		try {
			row = matrix[a];
		}
		catch (IndexOutOfBoundsException e) {
			System.err.print("Matrix has " + n + " rows, attempted " + a + " on getRow()");
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
			System.err.print("Matrix has " + m + " columns, attempted " + b + " on getColm()");
		}
		return colm;
	}
	
	public boolean isSquare() {
		return n == m;
	}
	
	public int getNumRow() {
		return n;
	}
	
	public int getNumColm() {
		return m;
	}
	
	public void setRow(int a, int[] rr) {
        try {
            matrix[a] = rr;
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Matrix has " + n + " rows, attempted " + a + " on setRow()");
        }
    } 
    public void setColm(int b, int[] rr) {
        try {
            for(int i =0; i<=m; i++) 
                matrix[i][b] = rr[i];
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Matrix has " + m + " coluumns, attempted " + b + " on setColm()");
        }
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
	
	public String toString() {
		String m = "[";
		for (int[] r: matrix) {
			m += "[";
			for (int a: r)
				m += a + "\t";
			m += "],\n";
		}
		m += "]";
		
		return m;
	}
}
