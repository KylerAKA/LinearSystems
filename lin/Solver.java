package lin;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Solver {
	public static boolean reduce = true;
	
	public static int find_pivot(Matrix A, int colm_num, int p) {
		Rows: for (int i = 0; i < A.m; i++) {
			if (A.matrix[i][colm_num] == 0 || gcdreduce(A, i, colm_num, p))
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
			int i = find_pivot(A, j, p);
			if (i == -1) {
				continue;
			}
			for (int r = i + 1; r < A.m; r++)
				for (int c = A.n - 1; c > j - 1; c--)
					A.matrix[r][c] = ((A.matrix[i][j] * A.matrix[r][c]) - (A.matrix[r][j]
						* A.matrix[i][c])) % p;
			pivot_columns[j] = true;
		}
		return pivot_columns;
	}
	
	public static void main(String... args) {
		watchCounts();
	}
	
	static void watchCounts() {
		// =Output ============================================
		File mainDir = null;
		File nDir = null;
		File rDir = null;
		File ZChart = null;
		PrintWriter Zout = null;
		try { // find charts folder
			mainDir = new File("charts").getCanonicalFile();
		}
		catch (IOException e) {
			System.err.println(e);
			System.exit(0);
		}
		// =====================================================
		
		// =Swing ==============================================
		JFrame f = new JFrame();
		f.setPreferredSize(new Dimension(500, 300));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		JLabel l = new JLabel();
		p.add(l);
		JPanel k = new JPanel();
		k.setLayout(new BoxLayout(k, BoxLayout.PAGE_AXIS));
		p.add(k);
		f.add(p);
		f.pack();
		f.setVisible(true);
		// ====================================================
		
		// =Constants =========================================
		int rows_i = 2, rows_f = 10;
		int cols_i = 2, cols_f = 10;
		int n_i = 2, n_f = 10;
		// ====================================================
		
		// =Number Crunching ==================================
		for (int n = n_i; n <= n_f; n++) {
			// folder for n;
			nDir = new File(mainDir, "p = " + n);
			if (!nDir.exists())
				nDir.mkdir();
			
			for (int rows = rows_i; rows <= rows_f; rows++) {
				
				// folder for r;
				rDir = new File(nDir, "r = " + rows);
				if (!rDir.exists())
					rDir.mkdir();
				
				for (int cols = cols_i; cols <= cols_f; cols++) {
					
					long Tot = (long) Math.pow(n, (rows * cols));
					if (Tot >= Math.pow(2, 30)) // skip long
						continue;
					
					// file for rows x columns
					try {
						ZChart = new File(rDir, rows + "x" + cols);
						if (ZChart.exists())
							ZChart.delete();
						ZChart.createNewFile();
						Zout = new PrintWriter(new BufferedWriter(new FileWriter(ZChart, true)), true);
					}
					catch (IOException e) {
						System.err.println(e);
						System.exit(0);
					}
					
					// prepare permutator
					MatrixPerm mp = new MatrixPerm(rows, cols, n);
					Matrix C = mp.toStart();
					
					int K_max = (cols < rows)? cols: rows;
					
					// labels for each of K pivots
					JLabel[] numKL = new JLabel[K_max + 1];
					for (int i = 0; i <= K_max; i++) {
						numKL[i] = new JLabel();
						k.add(numKL[i]);
					}
					f.revalidate();
					
					// number of matrices with K pivots
					int[] numK = new int[K_max + 1];
					int numP = 0; // number of pivots, per C
					int numC = 0; // number of consistent matrices
					int numM = 0; // number of matrices processed
					
					do { // run through permutations
						C = mp.getCurrent();
						numM++; // matrix processed
						
						boolean[] colsort = find_pivot_colms(C, n);
						if (!colsort[cols - 1])
							numC++; // the matrix is consistent
						for (boolean b: colsort)
							if (b)
								numP++;
						numK[numP]++; // the matrix has k pivots
						numP = 0;
						
						// update swing
						l.setText(rows + "x" + cols + " in mod " + n + ": " + numC + "/" + numM + "/" + Tot);
						for (int i = 0; i <= K_max; i++)
							numKL[i].setText(i + " pivots: " + numK[i]);
						f.repaint();
						
					} while (mp.permute());
					// =Output ======================================
					Zout.printf("Total Matrices: %1$10d \n", Tot);
					Zout.printf("Consistent: %1$10d \n", numC);
					Zout.println("Pivot Dist");
					for (int i = 0; i <= K_max; i++)
						Zout.printf(i + ": %1$10d \n", numK[i]);
					
					k.removeAll();
					
				}
			}
		}
	}
	
	static void showPerms() {
		// =Swing =============================================
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		JScrollPane pscroll =
			new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pscroll.setViewportView(p);
		f.setContentPane(pscroll);
		
		p.setPreferredSize(new Dimension(1000, 700));
		JPanel currentRow = new JPanel();
		currentRow.setLayout(new BoxLayout(currentRow, BoxLayout.LINE_AXIS));
		JTextArea t = new JTextArea();
		
		f.pack();
		f.setVisible(true);
		
		// =Number Crunching ==================================
		int rows = 2, cols = 2, prime = 3;
		
		MatrixPerm mp = new MatrixPerm(rows, cols, prime);
		Matrix C = mp.toStart();
		Matrix A = mp.toStart();
		
		int i = 0;
		int pi = 0;
		
		do {
			C = mp.getCurrent();
			A = mp.getCurrent();
			pi = 0;
			
			// displays if consistent
			boolean[] bools = find_pivot_colms(A, prime);
			for (boolean b: bools)
				if (b)
					pi++;
			
			if (true) {
				t = new JTextArea();
				t.setFont(new Font("Courier", Font.PLAIN, 9));
				t.setText(C.toString());
				t.append("___\n");
				t.append(A.toString());
				for (int j = 0; j < bools.length; j++)
					t.append(((bools[j])? "T": "F") + " ");
				currentRow.add(t);
				i++;
			}
			
			// line wrapping
			if (i >= Math.sqrt(Math.pow(prime, cols * rows))) {
				i = 0;
				
				p.add(currentRow);
				currentRow = new JPanel();
				currentRow.setLayout(new BoxLayout(currentRow, BoxLayout.LINE_AXIS));
				f.revalidate();
				f.repaint();
			}
			
			p.add(currentRow);
			f.revalidate();
			f.repaint();
			
		} while (mp.permute());
	}
	
	static void makeCharts() {
		// =Setup Output =======================================
		File iD = null;
		File pChart = null;
		PrintWriter out = null;
		try { // find charts folder
			iD = new File("charts").getCanonicalFile();
		}
		catch (IOException e) {
			System.err.println(e);
			System.exit(0);
		}
		
		// make charts over the set extents
		int[] numbers = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		int rows_i = 2, cols_i = 2;
		int rows_f = 10, cols_f = 10;
		
		for (int p: numbers) {
			
			try {// create or recreate file to output chart
				pChart = new File(iD, "C(Z^mxn_" + p + ")");
				if (pChart.exists())
					pChart.delete();
				pChart.createNewFile();
				out = new PrintWriter(new BufferedWriter(new FileWriter(pChart, true)), true);
			}
			catch (IOException e) {
				System.err.println(e);
				System.exit(0);
			}
			
			// =Crunch Numbers ===================================
			
			out.print("          |");
			for (int i = cols_i; i <= cols_f; i++)
				out.printf("%1$10d", i);
			out.println();
			out.print("__________|");
			for (int i = cols_i; i <= cols_f; i++)
				out.print("__________");
			out.println();
			
			for (int r = rows_i; r <= rows_f; r++) {
				out.printf("%1$10d |", r);
				
				for (int c = cols_i; c <= cols_f; c++) {
					
					// make permutator
					MatrixPerm mp = new MatrixPerm(r, c, p);
					Matrix C = mp.toStart();
					
					int numC = 0;
					long Tot = (long) Math.pow(p, (r * c));
					if (Tot >= Math.pow(2, 30))
						continue;
					do {
						C = mp.getCurrent();
						if (!find_pivot_colms(C, p)[c - 1])
							numC++;
					} while (mp.permute());
					
					out.printf("%1$10d", numC);
					System.out.printf("%1$10d", numC);
				}
				out.println();
				System.out.println();
			}
			System.out.println("Done!");
			out.close();
		}
	}
	
	/**
	 * Returns true if the element of matrix A at [r,c] is a
	 * zero divisor in p. That is, if for some x,y in Z mod p,
	 * x,y != 0 && x*y = 0, then x and y are zero divisors.
	 * 
	 * @param A a matrix
	 * @param r the element row
	 * @param c the element column
	 * @param p the modulation
	 * @return Whether the row of the matrix can/has be/been
	 *         reduced by multiplying the row of the element
	 *         [r,c] by that element's zero divisor conjugate.
	 */
	static boolean gcdreduce(Matrix A, int r, int c, int p) {
		int b = A.matrix[r][c], e = b;
		int a = p;
		
		while (b > 0) {
			e = b;
			b = a % b;
			a = e;
		}
		
		if (a == 1 || a == p)
			return false;
		
		if (reduce)
			for (int i = 0; i < A.matrix[r].length; i++)
				A.matrix[r][i] = A.matrix[r][i] * a % p;
		
		return true;
	}
	
}
