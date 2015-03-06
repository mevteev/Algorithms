public class Percolation {
  private boolean[] isOpen;
  private boolean[] connectedTop;
  private boolean[] connectedBottom;
  private int nn;
//  private int count;
  
  // private QuickFindUF qf;
  private WeightedQuickUnionUF qf;
  

  // create N-by-N grid, with all sites blocked
  public Percolation(int ngrid) {
    if (ngrid <= 0) {
      throw new java.lang.IllegalArgumentException();
    }

    nn = ngrid;
    
    //count = nn * nn;

    isOpen = new boolean[nn * nn + 2];
    connectedTop = new boolean[nn * nn];
    connectedBottom = new boolean[nn * nn];
    isOpen[nn * nn] = true;
    isOpen[nn * nn + 1] = true;

    for (int i = 0; i < nn * nn; i++) {
      isOpen[i] = false;
      connectedTop[i] = false;
      connectedBottom[i] = false;
    }
    
    for (int i = 1; i <= nn; i++) {
      connectedTop[getLinearPos(1, i)] = true;
      connectedBottom[getLinearPos(nn, i)] = true;
    }

    // qf = new QuickFindUF(N*N);
    qf = new WeightedQuickUnionUF(nn * nn + 2);
  }

  private int getLinearPos(int row, int column) {
    return (row - 1) * nn + (column - 1);
  }

  private void union(int p1, int p2) {
    if (!isOpen[p1] || !isOpen[p2]) {
      return;
    }
    if (!qf.connected(p1, p2)) {
      qf.union(p1, p2);
    }
  }

  // open site (row i, column j) if it is not open already
  public void open(int row, int column) {
    boolean top;
    boolean bottom;
    if (row < 1 || row > nn || column < 1 || column > nn) {
      throw new java.lang.IndexOutOfBoundsException();
    }
    
    //int oldCount = qf.count();

    if (!isOpen(row, column)) {
      top = false;
      bottom = false;
      isOpen[getLinearPos(row, column)] = true;
      if (row - 1 >= 1 && isOpen(row - 1, column)) {
        top = connectedTop[qf.find(getLinearPos(row - 1, column))] || connectedTop[qf.find(getLinearPos(row, column))];
        bottom = connectedBottom[qf.find(getLinearPos(row - 1, column))] || connectedBottom[qf.find(getLinearPos(row, column))];
        union(getLinearPos(row, column), getLinearPos(row - 1, column));
        if (top) {
          connectedTop[qf.find(getLinearPos(row, column))] = true;
        }
        if (bottom) {
          connectedBottom[qf.find(getLinearPos(row, column))] = true;
        }
      }
      if (row + 1 <= nn && isOpen(row + 1, column)) {
        top = connectedTop[qf.find(getLinearPos(row + 1, column))] || connectedTop[qf.find(getLinearPos(row, column))];
        bottom = connectedBottom[qf.find(getLinearPos(row + 1, column))] || connectedBottom[qf.find(getLinearPos(row, column))];
        union(getLinearPos(row, column), getLinearPos(row + 1, column));
        if (top) {
          connectedTop[qf.find(getLinearPos(row, column))] = true;
        }
        if (bottom) {
          connectedBottom[qf.find(getLinearPos(row, column))] = true;
        }
      }
      if (column - 1 >= 1 && isOpen(row, column - 1)) {
        top = connectedTop[qf.find(getLinearPos(row, column - 1))] || connectedTop[qf.find(getLinearPos(row, column))];
        bottom = connectedBottom[qf.find(getLinearPos(row, column - 1))] || connectedBottom[qf.find(getLinearPos(row, column))];
        union(getLinearPos(row, column), getLinearPos(row, column - 1));
        if (top) {
          connectedTop[qf.find(getLinearPos(row, column))] = true;
        }
        if (bottom) {
          connectedBottom[qf.find(getLinearPos(row, column))] = true;
        }
      }
      if (column + 1 <= nn && isOpen(row, column + 1)) {
        top = connectedTop[qf.find(getLinearPos(row, column + 1))] || connectedTop[qf.find(getLinearPos(row, column))];
        bottom = connectedBottom[qf.find(getLinearPos(row, column + 1))] || connectedBottom[qf.find(getLinearPos(row, column))];
        union(getLinearPos(row, column), getLinearPos(row, column + 1)); 
        if (top) {
          connectedTop[qf.find(getLinearPos(row, column))] = true;
        }
        if (bottom) {
          connectedBottom[qf.find(getLinearPos(row, column))] = true;
        }
      }
      
      if (connectedTop[qf.find(getLinearPos(row, column))] && connectedBottom[qf.find(getLinearPos(row, column))]) {
        union(getLinearPos(row, column), nn * nn);
        union(getLinearPos(row, column), nn * nn + 1);
      }
      

      
//      if (row == 1) {
//        union(getLinearPos(row, column), nn * nn);
//      }
//      if (row == nn || (qf.connected(getLinearPos(row, column), nn * nn + 1) &&
//          qf.connected(getLinearPos(row, column), nn * nn))) {
//        union(getLinearPos(row, column), nn * nn + 1);
//      }
//      if (oldCount != qf.count() && isFull(row, column)) {
//        for (int i = 1; i <= nn; i++) {
//          if (isOpen(nn, i) && qf.connected(getLinearPos(row, column), getLinearPos(nn, i))) {
//            union(getLinearPos(row, column), nn * nn + 1);
//            break;
//          }
//        }
//      }
    }
  }

  // is site (row i, column j) open?
  public boolean isOpen(int row, int col) {
    if (row < 1 || row > nn || col < 1 || col > nn) {
      throw new java.lang.IndexOutOfBoundsException();
    }

    return isOpen[getLinearPos(row, col)];
  }

  // is site (row i, column j) full?
  public boolean isFull(int row, int col) {
    if (row < 1 || row > nn || col < 1 || col > nn) {
      throw new java.lang.IndexOutOfBoundsException();
    }

    if (isOpen(row, col)) {
//      if (qf.connected(getLinearPos(row, col), nn * nn)) {
//        return true;
//      }
      return connectedTop[qf.find(getLinearPos(row, col))];
    }
    return false;
  }

  // does the system percolate?
  public boolean percolates() {
    return qf.connected(nn * nn, nn * nn + 1);
  }

  private void printarr() {
    for (int i = 1; i <= nn; i++) {
      for (int j = 1; j <= nn; j++) {
        StdOut.print(isOpen[getLinearPos(i, j)] + "\t");
      }
      StdOut.println();
    }
  }

  // test client (optional)
  public static void main(String[] args) {
    Percolation per = new Percolation(3);
    per.open(1, 1);
    //per.open(2, 1);
    per.open(3, 1);

    per.open(2, 3);
    per.open(3, 3);
    per.open(3, 2);

    per.printarr();

    StdOut.println(per.percolates());
  }
}
