import java.util.Iterator;


public class Board {
	private int[] blocks;
	private int N; 
	
	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    	int k = 1;
    	N = blocks[0].length;
    	this.blocks = new int[N * N + 1];
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			this.blocks[k++] = blocks[i][j];
    		}
    	}
    	
    }
    
    private int emptyPos() {
        for (int i = 1; i <= N * N; i++) {
            if (blocks[i] == 0) {
                return i;
            }
        }
        return -1;
    }
    
    private int row(int pos) {
        return (pos - 1) / N;
    }
    
    private int col(int pos) {
        return (pos - 1) % N;
    }
    
    // board dimension
    public int dimension() {
    	return N;
    }
    
    // number of blocks out of place
    public int hamming() {
    	int res = 0;
    	for (int i = 1; i < N * N; i++) {
    		if (blocks[i] != i) {
    			res++;
    		}
    	}
    	return res;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
    	int res = 0;
    	for (int i = 1; i <= N * N; i++) {
    		if (blocks[i] > 0 && blocks[i] != i) {
    			
    			res += (Math.abs(row(blocks[i]) - row(i)) + Math.abs(col(blocks[i]) - col(i)));
    		}
    	}
    	return res;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
    	return hamming() == 0;
    }
    
    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
    	int[][] twinBoard = new int[N][N];
    	int k = 1;
    	
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			twinBoard[i][j] = blocks[k++];
    		}
    	}
    	
    	for ( int i = 0; i < N; i++) {
    		if (twinBoard[i][0] != 0 && twinBoard[i][1] != 0) {
    			int tmp = twinBoard[i][0];
    			twinBoard[i][0] = twinBoard[i][1];
    			twinBoard[i][1] = tmp;
    			break;
    		}
    	}
    	
    	return new Board(twinBoard);
    	
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
    	if (y == this) {
    		return true;
    	}
    	
    	if (y == null) {
    		return false;
    	}
    	
    	if (y.getClass() != this.getClass()) {
    		return false;
    	}
    	
    	Board that = (Board) y;
    	
    	if (this.dimension() != that.dimension()) {
    		return false;
    	}
    	
    	for (int i = 1; i < N * N; i++) {
    		if (this.blocks[i] != that.blocks[i]) {
    			return false;
    		}
    	}
    	return true;
    	
    	
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board> () {

            @Override
            public Iterator<Board> iterator() {
                
                return new Iterator<Board>() {
                    private int pos = -1;
                    private int row = row(emptyPos());
                    private int col = col(emptyPos());

                    @Override
                    public boolean hasNext() {
                        if (pos > 3) {
                            return false;
                        }
                        
                        int[] av = availMoves();
                        int i = 0;
                        
                        while (av[i] <= pos) {
                            i++;
                            if (i >= av.length) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override
                    public Board next() {
                        if (hasNext()) {
                            int[] av = availMoves();
                            int i = 0;
                            
                            while (i < 4) {
                                if (av[i] > pos) {
                                    break;
                                }
                                i++;
                            }
                            pos = av[i];
                            return new Board(nextBoard(av[i]));
                        }
                        
                        return null;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Cannot remove an element of an array.");
                    }
                    
                    private int[] availMoves() {
                        //corners
                        if (row == 0 && col == 0) {
                            return new int[] {1, 2};
                        }
                        if (row == 0 && col == N - 1) {
                            return new int[] {2, 3};
                        }
                        if (row == N - 1 && col == 0) {
                            return new int[] {0, 1};
                        }
                        if (row == N - 1 && col == N - 1) {
                            return new int[] {0, 3};
                        }
                        //edges
                        if (row == 0) {
                            return new int[] {1, 2, 3};
                        }
                        if (row == N - 1) {
                            return new int[] {0, 1, 3};
                        }
                        if (col == 0) {
                            return new int[] {0, 1, 2};
                        }
                        if (col == N - 1) {
                            return new int[] {0, 2, 3};
                        }
                        //middle
                        return new int[] {0, 1, 2, 3};
                    }
                    
                    private int[][] nextBoard(int direction) {
                        int[][] res = new int[N][N];
                        int k = 1;
                        for (int i = 0; i < N; i ++) {
                            for (int j = 0; j < N; j++) {
                                res[i][j] = blocks[k++];
                            }
                        }
                        
                        int zeroPos = emptyPos();
                        int zRow = row(zeroPos);
                        int zCol = col(zeroPos);
                        
                        switch(direction) {
                        case 0:
                            res[zRow][zCol] = res[zRow - 1][zCol];
                            res[zRow - 1][zCol] = 0;
                            break;
                        case 1:
                            res[zRow][zCol] = res[zRow][zCol + 1];
                            res[zRow][zCol + 1] = 0;
                            break;
                        case 2:
                            res[zRow][zCol] = res[zRow + 1][zCol];
                            res[zRow + 1][zCol] = 0;
                            break;
                        case 3:
                            res[zRow][zCol] = res[zRow][zCol - 1];
                            res[zRow][zCol - 1] = 0;
                            break;
                        }
                        
                        return res;
                    }
                    
                };
            }
        };
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
    	String res = "";
    	int k = 1;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			res += blocks[k++] + " ";
    		}
    		res += "\n";
    	}
    	return res;
    }

    // unit tests (not graded)
    public static void main(String[] args) { 
        //Board board = new Board(new int[][] { {8,1,3}, {4,0,2}, {7,6,5} });
        Board board = new Board(new int[][] { {3,4,1}, {2,8,7}, {6,5,0} });
        //Board board = new Board(new int[][] { {1,2,3}, {4,5,6}, {7,0,8} });
        StdOut.println(board);
        StdOut.println("Hamming = " + board.hamming());
        StdOut.println("Manhattan = " + board.manhattan());
        StdOut.println("Dimension = " + board.dimension());
        StdOut.println("IsGoal = " + board.isGoal());
        
        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }
        
    	
    }

}
