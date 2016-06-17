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
		watchCount();
	}
	
	static void watchCount() {
		JFrame f = new JFrame();
		f.setPreferredSize(new Dimension(1000, 100));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel l = new JLabel();
		f.add(l);
		f.pack();
		f.setVisible(true);
		
		MatrixPerm mp = new MatrixPerm(rows_i, cols_i, prime);
		Matrix C = mp.toStart();
		
		int numM = 0;
		int numC = 0;
		long Tot = (long) Math.pow(prime, (rows_i * cols_i));
		int numP = 0;
		
		do {
			C = mp.getCurrent();
			numM++;
			for (boolean b: find_pivot_colms(C, prime))
				if (b)
					numP++;
			
			if (numP == num_pivots) // counts pivots
				numC++;
			numP = 0;
			
			l.setText(numC + "/" + numM + "/" + Tot);
			f.repaint();
		} while (mp.permute());
		
	}
	
	static void showPerms() {
		MatrixPerm mp = new MatrixPerm(rows_i, cols_i, prime);
		Matrix C = mp.toStart();
		// Swing
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		JScrollPane pscroll =
			new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pscroll.setViewportView(p);
		f.setContentPane(pscroll);
		
		p.setPreferredSize(new Dimension(1000, 700));
		JPanel currentRow = new JPanel();
		currentRow.setLayout(new BoxLayout(currentRow, BoxLayout.LINE_AXIS));
		JTextArea t = new JTextArea();
		
		f.pack();
		f.setVisible(true);
		int i = 0;
		
		do {
			C = mp.getCurrent();
			if (!find_pivot_colms(mp.getCurrent(), prime)[cols_i - 1]) {
				t = new JTextArea();
				t.setFont(new Font("Courier", Font.PLAIN, 9));
				t.setText(C.toString());
				currentRow.add(t);
				i++;
			}
			if (i >= Math.pow(2, cols_i * rows_i) / 36) {
				p.add(currentRow);
				currentRow = new JPanel();
				i = 0;
				currentRow.setLayout(new BoxLayout(currentRow, BoxLayout.LINE_AXIS));
				f.revalidate();
				f.repaint();
			}
			p.add(currentRow);
			f.revalidate();
			f.repaint();
			
		} while (mp.permute());
	}
	
	static void makePrimeCharts() {
		File iD = null; // Create the output file
		File pChart = null;
		PrintWriter out = null;
		try {
			iD = new File("charts").getCanonicalFile();
		}
		catch (IOException e) {
			System.err.println(e);
			System.exit(0);
		}
		
		for (int p: numbers) {
			
			try {
				pChart = new File(iD, "Inconistent_count_" + p);
				if (pChart.exists())
					pChart.delete();
				pChart.createNewFile();
				out = new PrintWriter(new BufferedWriter(new FileWriter(pChart, true)), true);
			}
			catch (IOException e) {
				System.err.println(e);
				System.exit(0);
			}
			
			if (out == null) {
				System.err.println("Stream to File could not be opened!");
				return;
			}
			
			for (int r = rows_i; r <= rows_f; r++) {
				for (int c = cols_i; c <= cols_f; c++) {
					
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
					
					out.printf("%1$10d", Tot - numC);
					System.out.printf("%1$10d", Tot - numC);
				}
				out.println();
				System.out.println();
			}
			System.out.println("Done!");
			out.close();
		}
	}
}
