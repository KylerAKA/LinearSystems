package lin;

public class Solver {
public static int rows = 2, cols = 2, p = 2;
	
	Matrix A;
	
	public Solver(Matrix B) {
		A = B;
	}
	
	public static int find_pivot(Matrix A, int colm_num) {
		for (int i = 0; i < A.getNumRow(); i++)
			if (A.matrix[i][colm_num] == 0)
				continue;
			else
				for (int j = 0; j < colm_num; j++)
					if (A.matrix[i][j] == 0)
						return i;
		return -1;
	}
	
	public static int[] find_pivot_colms(Matrix A) {
		int p = 0;
		int[] pivot_columns = new int[A.getNumColm()];
		int count = 0;
		for (int j = 0; j < A.getNumRow(); j++) {
			int i = find_pivot(A, j);
			if (i == -1) {
				continue;
			}
			else {
				for (int r = i + 1; r < A.getNumRow(); r++) {
					for (int c = A.getNumColm(); c > j - 1; c--) {
						int a = (A.matrix[i][j] * A.matrix[r][c]) - (A.matrix[r][j]
							* A.matrix[r][c]);
						A.setItem(r, c, a);
						if (p != 0)
							A.matrix[r][c] %= p;
					}
				}
			}
			pivot_columns[count] = j;
			count++;
		}
		return pivot_columns;
	}
	
	public static void main(String... args) {
		MatrixPerm mp = new MatrixPerm(rows, cols, p);
		
		Matrix C = mp.toStart();
		
		do {
			C = mp.getCurrent();
			System.out.println(find_pivot_colms(C));
		} while (mp.permute());
	}
