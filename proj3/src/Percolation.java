import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int rows;
    private int cols;
    private int total;
    private WeightedQuickUnionUF myunion;
    private WeightedQuickUnionUF myunion2;
    public static class Node {
        private int row;
        private int col;
        private boolean isopen;
        public Node(int row1, int col1, boolean isopen1) {
            row = row1;
            col = col1;
            isopen = isopen1;
        }
    }
    private Node[][] board;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        rows = N;
        cols = N;
        total = 0;
        myunion = new WeightedQuickUnionUF(N * N + 2);
        myunion2 = new WeightedQuickUnionUF(N * N + 1);
        board = new Node[N + 1][N];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = new Node(i, j, false);
            }
        }
    }

    public void open(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (board[row][col].isopen) {
            return;
        }
        board[row][col].isopen = true;
        total += 1;
        if (row == 0) {
            myunion.union(col, rows * rows);
            myunion2.union(col, rows * rows);
        }
        if (row == rows - 1) {
            myunion.union(row * rows + col, rows * rows + 1);
        }
        if (isValidIndex(row - 1) && board[row - 1][col].isopen) {
            myunion.union(row * rows + col, (row - 1) * rows + col);
            myunion2.union(row * rows + col, (row - 1) * rows + col);
        }
        if (isValidIndex(row + 1) && board[row + 1][col].isopen) {
            myunion.union(row * rows + col, (row + 1) * rows + col);
            myunion2.union(row * rows + col, (row + 1) * rows + col);
        }
        if (isValidIndex(col - 1) && board[row][col - 1].isopen) {
            myunion.union(row * rows + col, row * rows + col - 1);
            myunion2.union(row * rows + col, row * rows + col - 1);
        }
        if (isValidIndex(col + 1) && board[row][col + 1].isopen) {
            myunion.union(row * rows + col, row * rows + col + 1);
            myunion2.union(row * rows + col, row * rows + col + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isValidIndex(row) || !isValidIndex(col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return board[row][col].isopen;
    }

    public boolean isFull(int row, int col) {
        if (!isValidIndex(row) || !isValidIndex(col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return myunion2.connected(row * rows + col, rows * rows);
    }

    public int numberOfOpenSites() {
        return total;
    }

    public boolean percolates() {
        return myunion.connected(rows * rows, rows * rows + 1);
    }

    boolean isValidIndex(int index) {
        return index >= 0 && index < rows;
    }
}
