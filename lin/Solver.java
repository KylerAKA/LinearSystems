package lin;

public class Solver {
	public static int rows_i = 2, cols_i = 2, p = 2;
	
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
		/* JFrame f = new JFrame();
		 * f.setPreferredSize(new Dimension(1000, 100));
		 * f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * JLabel l = new JLabel();
		 * f.add(l);
		 * f.pack();
		 * f.setVisible(true); */
		
		for (int r = rows_i; r <= rows_f; r++) {
			for (int c = cols_i; c <= cols_f; c++) {
				// System.out.println(r + "x" + c + " in Z" + p);
				
				MatrixPerm mp = new MatrixPerm(r, c, p);
				Matrix C = mp.toStart();
				
				int numM = 0;
				int numC = 0;
				long Tot = (long) Math.pow(p, (r * c));
				// System.out.println("Tot:" + Tot);
				if (Tot >= Math.pow(2, 30))
					continue;
				
				long start = System.currentTimeMillis();
				do {
					C = mp.getCurrent();
					numM++;
					if (!find_pivot_colms(C)[c - 1])
						numC++;
					
					// l.setText(numC + "/" + numM + "/" + Tot);
					// f.repaint();
					
				} while (mp.permute());
				System.out.printf("%1$10d", numC);
				// System.out.println(numC + "/ " + Tot + " = "
				// + (float) numC / Tot);
				// System.out.println(System.currentTimeMillis() - start);
			}
			System.out.println();
		}
	}
}
