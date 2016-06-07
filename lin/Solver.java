package lin;

public class Solver {

	
//	Matrix A;
	
//	public Solver(Matrix B) {
//		A = B;
//	}
	
	public static boolean all(Matrix A,int row,int colm_num){
		for (int j = 0; j < colm_num; j++)
			if (A.matrix[row][j] != 0)
				return false;
		return true;
	}
	
	
	public static int find_pivot(Matrix A, int colm_num) {
		for (int i = 0; i < A.getNumRow(); i++){
			if (A.matrix[i][colm_num] == 0)
				continue;
			if(all(A,i,colm_num))
				return i;
		}
		return -1;
	}
	
	public static int[] find_pivot_colms(Matrix A) {
		int p = 0;
		int[] pivot_columns = new int[A.getNumColm()];
		//int count = 0;
		for (int j = 0; j < A.getNumColm(); j++) {
			int i = find_pivot(A, j);
			if (i == -1) {
				continue;
			}
			else {
				for (int r = i + 1; r < A.getNumRow(); r++) {
					for (int c = A.getNumColm()-1; c > j - 1; c--) {
						int a = (A.matrix[i][j] * A.matrix[r][c]) - (A.matrix[r][j]* A.matrix[i][c]);
						A.setItem(r, c, a);
						if (p != 0)
							A.matrix[r][c] %= p;
					}
				}
			}
			pivot_columns[j] = 1;
			//count++;
		}
		return pivot_columns;
	}
	
	public static void main(String... args) {
		int rows = 2, cols = 2, p = 2;
		
		MatrixPerm mp = new MatrixPerm(rows, cols, p);
		
		Matrix C = mp.toStart();
		int cnt = 0;
		int total = 0;
		do {
			total++;
			C = mp.getCurrent();
			System.out.println(C);
			int[] colms = find_pivot_colms(C);
			String out = "";
			for(int i : colms){
				out+= i;
			}
			System.out.println(out);
			if(colms[cols-1]==0){
				cnt++;
				System.out.println("eh");
			}
			System.out.println("----------");
		} while (mp.permute());
		System.out.println(cnt);
		System.out.println(total);
	}
}















