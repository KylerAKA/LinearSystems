package lin;

public class Solver {
	public static int rows_i = 3, cols_i = 3, prime = 2;
	
	public static int num_pivots = 2;
	
	public static int[] numbers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
	
	public static int rows_f = 10, cols_f = 10;
	
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
	
	public static boolean[] find_pivot_colms(Matrix A, int p) {
		boolean[] pivot_columns = new boolean[A.n];
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
		}
		return pivot_columns;
	}
	
		
	
	
	
	
	public static void main(String... args) {
		//int rows = 2, cols = 2, p = 2;
		
		int p = 2;
		for(int rows = 2;rows<10;rows++){
			for(int cols = rows;cols<8;cols++){

				if(Math.pow(2,rows*cols)>Math.pow(2,30))
					break;
				
				MatrixPerm mp = new MatrixPerm(rows, cols, p);
				
				int[] pivs = new int[cols+1];
				for(int i=0;i<cols+1;i++){
					pivs[i]=0;
				}
				
				Matrix C = mp.toStart();
				int cnt = 0;
				int total = 0;
				do {
					total++;
					C = mp.getCurrent();
					boolean[] colms = find_pivot_colms(C,p);
		
					int sum = 0;
					for(int i = 0;i<cols;i++){
						if(colms[i])
							sum++;
					}
					
					pivs[sum]++;
					
				} while (mp.permute());
				
				int out = 0;
				
				System.out.println("m: "+rows+", n: "+cols+", p: "+p+"\n");
				
				for(int i=0;i<cols+1;i++){
					System.out.println(i+"\t"+pivs[i]);
					out += Math.pow(2,i)*pivs[i];
				}
				
				System.out.println("\nTotal:\t"+out);
				
				System.out.println("-----------------------");
			}
		}
	}
}















