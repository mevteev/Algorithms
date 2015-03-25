
public class SAP {
    
    Digraph G;
    
    BreadthFirstDirectedPaths bfdp;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = G;

    }
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        
        BreadthFirstDirectedPaths bfpV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfpW = new BreadthFirstDirectedPaths(G, w);        
        
        int i = ancestor(v, w);
        
        
        if (i != -1 && bfpV.hasPathTo(i) && bfpW.hasPathTo(i)) {
            return bfpV.distTo(i) + bfpW.distTo(i);
        }
        
        return -1;
        
    }
    
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfpV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfpW = new BreadthFirstDirectedPaths(G, w);
        
        int length = Integer.MAX_VALUE;
        int ancestor = -1; 
        
        for (int i = 0; i < G.V(); i++) {
            if (!bfpV.hasPathTo(i) || !bfpW.hasPathTo(i)) {
                continue;
            }
            
            int newLen = bfpV.distTo(i) + bfpW.distTo(i);
            if (newLen < length) {
                length = newLen;
                ancestor = i;
            }
        }

        
        return ancestor;
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfpV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfpW = new BreadthFirstDirectedPaths(G, w);        
        
        int i = ancestor(v, w);
        
        
        if (i != -1 && bfpV.hasPathTo(i) && bfpW.hasPathTo(i)) {
            return bfpV.distTo(i) + bfpW.distTo(i);
        }
        
        return -1;
    }
    
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfpV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfpW = new BreadthFirstDirectedPaths(G, w);
        
        int length = Integer.MAX_VALUE;
        int ancestor = -1; 
        
        for (int i = 0; i < G.V(); i++) {
            if (!bfpV.hasPathTo(i) || !bfpW.hasPathTo(i)) {
                continue;
            }
            
            int newLen = bfpV.distTo(i) + bfpW.distTo(i);
            if (newLen < length) {
                length = newLen;
                ancestor = i;
            }
        }

        
        return ancestor;
    }
    
    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }


}
