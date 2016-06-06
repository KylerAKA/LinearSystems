public class Matrix {
    int[][] matrix;
    final int n,m;
    
    public int[] getRow(int a) {
       int[] row = null; 
       row = matrix[a];
       return row;
    }
    public int[] getColm(int b) {
        int [] colm = new int[m];
        for (int i=0; i<=m; i++) {
            colm[i] = matrix[i][b];
        }
        return colm;
    }
    public int getNumRow() {
        return n;
    }
    public int netNumCol() {
        return m;
    }
}
