import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] frac;
    private double Mean;
    private double Stddev;
    private int T;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        validate(n, trials);
        frac = new double[trials];
        T = trials;
        int row, col;
        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                per.open(row, col);
            }
            frac[i] = per.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    private void validate(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("IllegalArgument");
        }
    }

    public double mean() {
        Mean = StdStats.mean(frac);
        return Mean;
    }

    public double stddev() {
        Stddev = StdStats.stddev(frac);
        return Stddev;
    }

    public double confidenceLo() {
        return Mean - 1.96 * Math.sqrt(Stddev) / Math.sqrt(T);
    }

    public double confidenceHi() {
        return Mean + 1.96 * Math.sqrt(Stddev) / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats perStats = new PercolationStats(n, T);
        StdOut.printf("mean                    = %.8f\n", perStats.mean());
        StdOut.printf("stddev                  = %.8f\n", perStats.stddev());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]", perStats.confidenceLo(), perStats.confidenceHi());
    }
}
