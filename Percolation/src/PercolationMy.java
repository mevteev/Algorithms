public class PercolationMy {
    private int[] arr;
    private int[] sz;
    private int N;
    
    private int getLinearPos(int i, int j) {
        return (i-1)*N + (j-1);
    }
    
    private int root(int i, int j) {
        int p = getLinearPos(i,j);
        while(p != arr[p]) p = arr[p];
        return p;
    }
    
    private void union(int i, int j, int k, int l)
    {
        if (k<1 || l<1 || k>N || l>N) { return; }
        if (!isOpen(i,j) || !isOpen(k,l)) {return;}
        
        int a = root(i,j);
        int b = root(k,l);
        
        if (a==b) return;
        if (sz[a] < sz[b]) { arr[a] = b; sz[b] += sz[a]; }
        else {arr[b] = a; sz[a] += sz[b]; }
    }
    
 
    
// create N-by-N grid, with all sites blocked
    public PercolationMy(int NN) {
    	if (NN <= 0) {
    		throw new java.lang.IllegalArgumentException();
    	}
    	
        N = NN;
        arr = new int[N*N];
        sz = new int[N*N];
        
        for(int i=0;i<N*N;i++) {
            arr[i]=-1; sz[i] = 0;
        }
    }
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
    	if (i<1 || i>N || j<1 || j>N) {
    		throw new java.lang.IndexOutOfBoundsException();
    	}
    	
        if (!isOpen(i,j)) {
            sz[getLinearPos(i,j)] = 1;
            arr[getLinearPos(i,j)] = getLinearPos(i,j);
            
            union(i,j,i-1,j);
            union(i,j,i+1,j);
            union(i,j,i,j-1);
            union(i,j,i,j+1);
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
    	if (i<1 || i>N || j<1 || j>N) {
    		throw new java.lang.IndexOutOfBoundsException();
    	}
    	
        return arr[getLinearPos(i,j)] != -1;
    }
    
    public boolean isFull(int i, int j) {
    	if (i<1 || i>N || j<1 || j>N) {
    		throw new java.lang.IndexOutOfBoundsException();
    	}
    	
    	if (isOpen(i,j)) {
	    	for(int k = 1; k <= N; k++) {
	    		if (isOpen(1,k) && root(i,j) == root(1,k)) {
	    			return true;
	    		}
	    	}
    	}
		return false;
    }
    
    
    public boolean percolates() {
    	for(int k = 1; k <= N; k++) {
    		if (isFull(N,k)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void printarr() {
        for (int i = 1; i<= N; i++) {
            for (int j = 1; j<=N; j++) {
                System.out.print(arr[getLinearPos(i,j)]+"\t");
            }
            System.out.println();
        }
    }
    
//    public static void main(String[] args) {
//        PercolationMy per = new PercolationMy(3);
//        per.open(1,1);
//        per.open(2,1);
//        per.open(3,1);
//        
//        per.open(2,3);
//        per.open(3,3);
//
//        per.open(3,2);
//        
//        per.printarr();
//        
//        System.out.println(per.percolates());
//        
//    }
}