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
    		row = matrix[a];
    		return row;
    	}
    	
    	public int[] getColm(int b) {
    		int [] colm = new int[m];
    		for (int i=0; i<=m; i++) {
    			colm[i] = matrix[i][b];
    		}
    		return colm;
    	}
    	
	 public int getNumRow() {
        	return n;
    	}
    	
    	public int netNumCol() {
        	return m;
    	}
	
	public boolean isSquare() {
		return n == m;
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
