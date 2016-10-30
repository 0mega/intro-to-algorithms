package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 
 * @author Oleksandr Kruk
 * 
 *         Class for running Percolation model experiments and produce
 *         statistical results
 *
 */
public class PercolationStats {

  private int expTrials;
  private double[] thresholds;

  /**
   * PercolationStats Constructor initializes the {@link Percolation} model and
   * runs the desired number of experiments
   * 
   * @param n
   *          - grid size
   * @param trials
   *          - number of experiments to perform
   */
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0)
      throw new IllegalArgumentException();

    expTrials = trials;
    performTests(n, trials);
  }

  /**
   * Test method that reads two arguments from command line the {@code n} as
   * grid size and {@code T} as number of experiments
   * 
   * @param args
   */
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(n, trials);
    System.out.println("mean:                    " + stats.mean());
    System.out.println("stddev:                  " + stats.stddev());
    System.out.println("95% confidence interval: " + stats.confidenceLo()
        + ", " + stats.confidenceHi());
  }

  /**
   * Calculates sample mean of percolation thresholds for all experiments
   * 
   * @return double - sample mean
   */
  public double mean() {
    return StdStats.mean(thresholds);
  }

  /**
   * Calculates sample mean of percolation thresholds for all experiments
   * 
   * @return double - sample standard deviation
   */
  public double stddev() {
    return StdStats.stddev(thresholds);
  }

  /**
   * Calculates low endpoint of 95% confidence interval
   * 
   * @return double - low confidence
   */
  public double confidenceLo() {
    return mean() - (1.96 * stddev() / Math.sqrt(expTrials));
  }

  /**
   * Calculates high endpoint of 95% confidence interval
   * 
   * @return double - high confidence
   */
  public double confidenceHi() {
    return mean() + (1.96 * stddev() / Math.sqrt(expTrials));
  }

  private void performTests(int n, int trials) {
    thresholds = new double[trials];
    for (int i = 0; i < trials; i++) {
      thresholds[i] = simulatePercolation(n);
    }
  }

  private double simulatePercolation(int n) {
    Percolation percolation = new Percolation(n);
    double openSitesCount = 0.0;
    while (!percolation.percolates()) {
      int i = StdRandom.uniform(1, n + 1);
      int j = StdRandom.uniform(1, n + 1);
      while (percolation.isOpen(i, j)) {
        i = StdRandom.uniform(1, n + 1);
        j = StdRandom.uniform(1, n + 1);
      }
      percolation.open(i, j);
      openSitesCount++;
    }

    double threshold = openSitesCount / (n * n);
    return threshold;
  }
}
