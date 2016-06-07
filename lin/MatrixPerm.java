package lin;

public class MatrixPerm extends Permutator<Matrix> {
	
	final int n, m, p;
	
	public MatrixPerm(int rows, int cols, int Zfield) {
		m = rows;
		n = cols;
		p = Zfield;
	}
	
	@Override
	Matrix toStart() {
		crn = new Matrix(m, n);
		return crn;
	}
	
	@Override
	boolean permute() {
		for (int r = 0; r < m; r++)
			for (int c = 0; c < n; c++)
				if (crn.matrix[r][c] < p - 1)
					return (crn.matrix[r][c]++) < p;
				else
					crn.matrix[r][c] = 0;
		return false;
	}
	
	@Override
	Matrix getCurrent() {
		return new Matrix(crn.matrix);
	}
}
