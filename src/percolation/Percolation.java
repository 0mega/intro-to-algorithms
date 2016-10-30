package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author Oleksandr Kruk
 * 
 *         This class implement the Percolation model that allows to manipulate
 *         a grid n by n and test if it percolates or not by opening sites
 *
 */
public class Percolation {

  private static final int OPEN = 1;

  private int[][] gridState;
  private WeightedQuickUnionUF wqu;
  private int virtualTop;
  private int virtualBottom;
  private int gridSize;

  /**
   * Percolation model constructor that initializes the necessary data
   * structures. Produces a grid of n x n. Also initializes the Quick union data
   * structure with two extra nodes to represent the virtual top and bottom
   * nodes
   * 
   * @param n
   *          is the size of the grid
   */
  public Percolation(int n) {
    validatePositive(n);
    gridSize = n;
    virtualTop = n * n;
    virtualBottom = n * n + 1;
    // initialize with extra two nodes top and bottom
    int quickUnionSize = n * n + 2;

    wqu = new WeightedQuickUnionUF(quickUnionSize);
    gridState = new int[n][n];

    // link virtual top and bottom nodes
    for (int i = 0; i < n; i++) {
      wqu.union(virtualTop, i);
    }

    for (int i = n * n - 1; i >= (n * n - n); i--) {
      wqu.union(virtualBottom, i);
    }
  }

  /**
   * Test client for percolation model based on the following 5x5 grid
   * structure:
   * 
   * 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 ^ ^
   * topNode bottomNode
   */
  public static void main(String[] args) {
    /* Test index conversion */
    Percolation perc = new Percolation(5);
    System.out.println("Index for 1,1: " + perc.rowColumnToIndex(1, 1));
    System.out.println("Index for 2,3: " + perc.rowColumnToIndex(2, 3));
    System.out.println("Index for 5,5: " + perc.rowColumnToIndex(5, 5));

    /* Test opening sites */
    perc.open(1, 1);
    perc.open(1, 2);
    perc.open(1, 3);
    System.out.println(perc.wqu.connected(0, 2) ? "Success" : "Failure");

    perc.open(2, 1);
    perc.open(3, 1);
    perc.open(4, 1);
    perc.open(5, 1);

    // Print grid state
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        System.out.print("|" + perc.gridState[i][j] + "|");
      }
      System.out.println();
    }

    // Should percolate
    System.out.println(perc.percolates() ? "Percolates" : "Fail");

    Percolation cornerPercolation = new Percolation(1);
    System.out.println("Percolates: " + cornerPercolation.percolates());
  }

  /**
   * Opens a site on the grid at location i,j where i is the row and j is the
   * column The range is 1 to n for both i and j
   * 
   * @param i
   *          row in the grid
   * @param j
   *          column in the grid
   */
  public void open(int i, int j) {
    validateRange(1, gridState.length, i, j);
    int index = rowColumnToIndex(i, j);
    int right = index + 1;
    int left = index - 1;
    int top = index - gridSize;
    int bottom = index + gridSize;

    if (!isOpen(i, j)) {
      gridState[i - 1][j - 1] = OPEN;

      // open to right
      if (insideGrid(i, j + 1) && isOpen(i, j + 1)) {
        wqu.union(index, right);
      }
      // open to left
      if (insideGrid(i, j - 1) && isOpen(i, j - 1)) {
        wqu.union(index, left);
      }
      // open to top
      if (insideGrid(i - 1, j) && isOpen(i - 1, j)) {
        wqu.union(index, top);
      }
      // open to bottom
      if (insideGrid(i + 1, j) && isOpen(i + 1, j)) {
        wqu.union(index, bottom);
      }
    }
  }

  /**
   * Test if a site is open on the grid at location {@code i}, {@code j} where i
   * is the row and j is the column The range is {@code 1} to {@code n} for both
   * {@code i} and {@code j}
   * 
   * @param i
   *          row in the grid
   * @param j
   *          column in the grid
   */
  public boolean isOpen(int i, int j) {
    validateRange(1, gridSize, i, j);
    return gridState[i - 1][j - 1] == OPEN;
  }

  /**
   * Test if a site is full on the grid at location i,j where i is the row and j
   * is the column. The range is 1 to n for both i and j
   * 
   * @param i
   *          row in the grid
   * @param j
   *          column in the grid
   */
  public boolean isFull(int i, int j) {
    validateRange(1, gridSize, i, j);
    return isOpen(i, j)
        && wqu.connected(virtualTop, rowColumnToIndex(i, j));
  }

  /**
   * Method to test if virtual top node and virtual bottom node are connected.
   * In case these are connected it means that the grid has a path between both
   * node and percolates
   * 
   * @return boolean {@code true} if the grid percolates, {@code false}
   *         otherwise
   */
  public boolean percolates() {
    if (gridSize == 1)
      return isOpen(1, 1) && wqu.connected(virtualTop, virtualBottom);
    return wqu.connected(virtualTop, virtualBottom);
  }

  /**
   * Test if an arbitrary number of integers {@code indexes} are within the
   * specified range of {@code min} and {@code max}
   * 
   * @param min
   * @param max
   * @param indexes
   * 
   */
  private void validateRange(int min, int max, int... indexes) {
    for (int i : indexes) {
      if (i < min || i > max)
        throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Validates if a list of integers is positive
   * 
   * @param numbers
   *          arbitrary list of numbers to be validated
   * 
   */
  private void validatePositive(int... numbers) {
    for (int n : numbers) {
      if (n <= 0)
        throw new IllegalArgumentException();
    }
  }

  /**
   * 
   * @param i
   *          row in the grid
   * @param j
   *          column in the grid
   * @return {@code true} when {@code i, j} indexes refer to a position inside
   *         the grid, {@code false} otherwise
   */
  private boolean insideGrid(int i, int j) {
    int row = i - 1;
    int col = j - 1;
    return row >= 0 && col >= 0 && row < gridSize && col < gridSize;
  }

  /**
   * Translates two dimensional coordinates into one dimensional
   * 
   * @param row
   *          on the grid
   * @param column
   *          on the grid
   * @return index in quick union array representing the {@code i, j} position
   *         on the grid
   */
  private int rowColumnToIndex(int row, int column) {
    int i = row - 1;
    int j = column - 1;
    return i * gridSize + j;
  }
}
