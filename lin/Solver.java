

public class Solver {
    matrix A;
    public Solver(matrix B) {
        A = B;
    }
    
    public int find_pivot(matrix A, int colm_num) {
        for (int i = 0; i < A.getNumRow(); i++)
            if (A.matrix[i][colm_num] == 0)
                continue;
            else
                for (int j=0; j < colm_num; j++)
                    if (A.matrix[i][j] == 0) return i;
        return -1;
    }
    public int[] find_pivot_colms(matrix A) {
        int p = 0;
        int[] pivot_columns = new int[A.getNumColm()];
        int count = 0;
        for (int j = 0; j < A.getNumRow();j++) {
            int i = find_pivot(A, j);
            if (i == -1) {continue;}
            else {
                for (int r = i+1; r < A.getNumRow();r++) {
                    for (int c = A.getNumColm(); c > j-1; c--) {
                        int a = (A.matrix[i][j]*A.matrix[r][c])-(A.matrix[r][j]
                                *A.matrix[r][c]);
                        A.setItem(r,c,a);
                        if (p !=0)
                            A.matrix[r][c] %=p;
                    }
                }
            }
            pivot_columns[count] = j;
            count++;
        }
        return pivot_columns;
    }
}
