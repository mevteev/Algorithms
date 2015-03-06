import java.util.Comparator;
import java.util.Iterator;


public class Solver {
    
    MinPQ<SearchNode> mpq; 
    boolean solved = false;
    int moves = 0;
    SearchNode goal;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.NullPointerException();
        }
        
        mpq = new MinPQ<SearchNode>(new ManhattanComparator());
        
        mpq.insert(new SearchNode(initial, 0, null));
        
        SearchNode sn;
        sn = mpq.min();
        
        while (!sn.board.isGoal())
        {
        
            sn = mpq.delMin();
            for (Board b:sn.board.neighbors()) {
                mpq.insert(new SearchNode(b, sn.move + 1, sn));
            }
        }
        goal = sn;
        moves = sn.move;
        solved = true;
        
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return solved;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return new Iterable<Board> () {

            @Override
            public Iterator<Board> iterator() {
                if (moves < 0) {
                    return null;
                }
                
                final Board[] sol = new Board[moves + 1];
                SearchNode currentNode = goal;
                int i = 0;
                while (currentNode != null) {
                    sol[i++] = currentNode.board;
                    currentNode = currentNode.parent;
                }

                
                return new Iterator<Board>() {
                    
                    

                    
                    int cur = moves + 1;

                    @Override
                    public boolean hasNext() {
                        return cur > 0;
                    }

                    @Override
                    public Board next() {
                        if (hasNext()) {
                            return sol[--cur];
                        }
                        return null;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Cannot remove an element of an array.");
                        
                    }
                };
            }
        };
    }
    
    private class ManhattanComparator implements Comparator<SearchNode> {
        ManhattanComparator() {}
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return (o1.priority() - o2.priority());
        }
    }
    
    private class SearchNode {
        private Board board;
        private int move;
        private SearchNode parent;
        
        public SearchNode(Board board, int move, SearchNode parent) {
            this.board = board;
            this.move = move;
            this.parent = parent;
        }
        
        public int priority() {
            return move + board.manhattan();
        }
        
        public Board board() {
            return board;
        }
        
    }
    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}
