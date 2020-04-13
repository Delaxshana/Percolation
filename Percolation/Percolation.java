import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] id;
    private int[][] status;
    private int N;
    private int count;
    private WeightedQuickUnionUF wquf;
    private int top;
    private int bottom;

    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException("IllegalArgument");
        N = n;
        count = 0;
        wquf = new WeightedQuickUnionUF(N * N + 2);
        id = new int[N + 1][N + 1];
        status = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                id[i][j] = (i - 1) * N + j - 1;
                status[i][j] = 0;
            }
        }
        top = N * N;
        bottom = N * N + 1;
    }

    public void open(int row, int col) {
        checkRange(row, col);
        if (isOpen(row, col)) ;
        else {
            status[row][col] = 1;
            concat(row, col);
            count++;
        }
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean isOpen(int row, int col) {
        checkRange(row, col);
        return status[row][col] == 1;
    }

    private void concat(int row, int col) {
        if (row != 1 && isOpen(row - 1, col)) {
            union(id[row][col], id[row - 1][col]);
        } else if (row == 1) {
            union(id[row][col], top);
        }
        if (row != N && isOpen(row + 1, col)) {
            union(id[row][col], id[row + 1][col]);
        } else if (row == N) {
            union(id[row][col], bottom);
        }
        if (col != 1 && isOpen(row, col - 1)) {
            union(id[row][col], id[row][col - 1]);
        }
        if (col != N && isOpen(row, col + 1)) {
            union(id[row][col], id[row][col + 1]);
        }
    }

    private void union(int p, int q) {
        if (!wquf.connected(p, q)) {
            wquf.union(p, q);
        }
    }


    public boolean isFull(int row, int col) {
        checkRange(row, col);
        return wquf.connected(id[row][col], top);
    }

    public boolean percolates() {
        return wquf.connected(top, bottom);
    }

    private void checkRange(int i, int j) {
        if (i <= 0 || j <= 0 || i > N || j > N)
            throw new IndexOutOfBoundsException();
    }


}
