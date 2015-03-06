public class PercolationStats {
//  private double[] times;
  private double[] fractions;
  

//  private int nn;
  private int openitems;
//  private int[] opensites;
  
  // perform T independent experiments on an N-by-N grid
  public PercolationStats(int ngrid, int texp) {
    int randomx;
    int randomy;
    
    Percolation[] per;    

    if (ngrid <= 0 || texp <= 0) {
      throw new java.lang.IllegalArgumentException();
    }
    
//    nn = ngrid * ngrid;

//    times = new double[tt];
    fractions = new double[texp];
//    opensites = new int[texp];
    
    
    per = new Percolation[texp];
    
    for (int i = 0; i < texp; i++) {
      per[i] = new Percolation(ngrid);
      openitems = 0;
      
      while (!per[i].percolates()) {
        randomx = (int) (StdRandom.uniform() * ngrid + 1);
        randomy = (int) (StdRandom.uniform() * ngrid + 1);
        
        if (!per[i].isOpen(randomx, randomy)) {
          per[i].open(randomx, randomy);
          openitems++;
        }
      }
      fractions[i] = openitems / (double) (ngrid * ngrid);
      // StdOut.println(i);
    }    
//    StdOut.println("mean                    = " + mean());
//    StdOut.println("mean                    = " + StdStats.mean(fractions));
//    StdOut.println("stddev                  = " + stddev());
//    StdOut.println("stddev                  = " + StdStats.stddev(fractions));
//    StdOut.println("95% confidence interval = " + confidenceLo() + " , "
//        + confidenceHi());    
    
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(fractions);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(fractions);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - 1.96 * stddev() / Math.sqrt(fractions.length);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + 1.96 * stddev() / Math.sqrt(fractions.length);
  }
  
  // test client (described below)
  public static void main(String[] args) {
//    int ntest = StdIn.readInt();
//    int ttest = StdIn.readInt();

//    PercolationStats ps = new PercolationStats(ntest, ttest);

    // Stopwatch sw = new Stopwatch();
//    for (int i = 0; i < ttest; i++) {
//      Percolation pqf = new Percolation(ntest);
//
//      while (!pqf.percolates()) {
//        pqf.open((int) (StdRandom.uniform() * ntest + 1), 
//            (int) (StdRandom.uniform() * ntest + 1));
//      }
//      // ps.fractions[i] = pqf.openSites()/(double)(N*N);
//      // StdOut.println(i);
//    }
//    StdOut.println("mean                    = " + ps.mean());
//    StdOut.println("mean                    = " + StdStats.mean(fractions));
//    StdOut.println("stddev                  = " + ps.stddev());
//    StdOut.println("95% confidence interval = " + ps.confidenceLo() + " , "
//        + ps.confidenceHi());
  }
}
