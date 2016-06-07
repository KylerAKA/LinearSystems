package lin;

public class Solver {
	public static int rows = 2, cols = 10, p = 2;
	
	public static int find_pivot(Matrix A, int colm_num) {
		Rows: for (int i = 0; i < A.m; i++) {
			if (A.matrix[i][colm_num] == 0)
				continue;
			for (int j = 0; j < colm_num; j++)
				if (A.matrix[i][j] != 0)
					continue Rows;
			return i;
		}
		return -1;
	}
	
	public static boolean[] find_pivot_colms(Matrix A) {
		int p = 0;
		boolean[] pivot_columns = new boolean[A.n];
		int count = 0;
		for (int j = 0; j < A.n; j++) {
			int i = find_pivot(A, j);
			if (i == -1) {
				continue;
			}
			else {
				for (int r = i + 1; r < A.m; r++) {
					for (int c = A.n - 1; c > j - 1; c--) {
						A.matrix[r][c] = (A.matrix[i][j] * A.matrix[r][c]) - (A.matrix[r][j]
							* A.matrix[i][c]);
						if (p != 0)
							A.matrix[r][c] %= p;
					}
				}
			}
			pivot_columns[j] = true;
			count++;
		}
		return pivot_columns;
	}
	
	public static void main(String... args) {
		
		MatrixPerm mp = new MatrixPerm(rows, cols, p);
		
		Matrix C = mp.toStart();
		int numM = 0;
		int numC = 0;
		
		do {
			C = mp.getCurrent();
			numM++;
			// System.out.println(C);
			
			boolean[] colms = find_pivot_colms(C);
			if (!colms[cols - 1])
				numC++;
			
			// for (boolean b: colms)
			// System.out.print(((b)? "pivot": "not") + "\t");
			// System.out.println("\n--------------");
			
		} while (mp.permute());
		
		System.out.println(numC + "/" + numM + ": " + (float) numC / numM);
	}
}
