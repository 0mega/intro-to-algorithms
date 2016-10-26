package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private static final int FULL = 1;
	private static final int OPEN = 2;

	private int[][] gridState;
	WeightedQuickUnionUF wqu;
	private int virtualTop;
	private int virtualBottom;
	private int gridSize;
	
	public static void main(String[] args) {
		/* Test index conversion */
		
		
		Percolation perc = new Percolation(5);
		System.out.println("Index for 1,1: " + perc.rowColumnToIndex(1, 1));
		System.out.println("Index for 2,3: " + perc.rowColumnToIndex(2, 3));
		System.out.println("Index for 5,5: " + perc.rowColumnToIndex(5, 5));
//		perc.open(1, 1);
//		perc.open(1, 2);
//		if(perc.wqu.connected(0, 1)) {
//			System.out.println("Success");
//		} else {
//			System.out.println("Fail");
//		}
	}
	
	public Percolation(int n) {
		validatePositive(n);
		gridSize = n;
		// initialize with extra two nodes top and bottom
		int quickUnionSize = n*n+2;
		System.out.println("QU size: " + quickUnionSize);
		wqu = new WeightedQuickUnionUF(quickUnionSize);
		System.out.println("Number of components: " + wqu.count());
		gridState = new int[n][n];
		
		//link virtual top and bottom nodes
		for(int i = 1; i == n; i++) {
			wqu.union(virtualTop, i);
		}
		
		virtualBottom = n^2;
		for(int i = virtualBottom; i > (n*n-n); i--) {
			wqu.union(virtualBottom, i);
		}
	}
	
	public void open(int i, int j) {
		validateRange(1, gridState.length, i, j);
		if(!isOpen(i, j)) {
			if(i == 1 && j == 1) {
				gridState[i][j] = OPEN;
				wqu.union(i, j);
			}
			if(i > 1 && isOpen(i-1, j)) {
				wqu.union(i*j, (i-1)*j);
			}
		}
	}
	
	public boolean isOpen(int i, int j) {
		return gridState[i][j] == OPEN;
	}
	
	public boolean isFull(int i, int j) {
		return gridState[i-1][j-1] == FULL;
	}
	
	public boolean percolates() {
		return false;
	}
	
	private void validateRange(int min, int max, int... indexes) {
		for(int i : indexes) {
			if(i < min || i > max)
				throw new IndexOutOfBoundsException();
		}
	}
	
	private void validatePositive(int... numbers) {
		for(int n : numbers) {
			if(n <= 0) throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 0  1  2  3  4
	 * 5  6  7  8  9
	 * 10 11 12 13 14
	 * 15 16 17 18 19
	 * 20 21 22 23 24 25 26
	 */
	
	private int rowColumnToIndex(int row, int column) {
		int i = row-1;
		int j = column-1;
		return i * gridSize + j;
	}
}
