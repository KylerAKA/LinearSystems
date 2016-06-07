package lin;

public class MatrixPerm extends Permutator<Matrix> {
	
	final int n, m, p;
	
	public MatrixPerm(int rows, int cols, int Zfield) {
		n = rows;
		m = cols;
		p = Zfield;
	}
	
	@Override
	Matrix toStart() {
		crn = new Matrix(n, m);
		return crn;
	}
	
	@Override
	boolean permute() {
		for (int r = 0; r < n; r++)
			for (int c = 0; c < m; c++)
				if (crn.matrix[r][c] < p - 1) {
					// System.out.print(r + " " + c);
					// System.out.println(": " + crn.matrix[r][c] + 1);
					return (crn.matrix[r][c]++) < p;
				}
				else
					crn.matrix[r][c] = 0;
		return false;
	}
	
	@Override
	Matrix getCurrent(){
		return new Matrix(crn.matrix);
	}
}