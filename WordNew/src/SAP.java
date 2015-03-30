
public class SAP {
    
    private Digraph G;
    
    private int lastAncestorCall = -1;
    private int lastVCall = -1;
    private int lastWCall = -1;
    
    private int lastAncestorCallArr = -1;
    private Iterable<Integer> lastVCallArr = null;
    private Iterable<Integer> lastWCallArr = null;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new java.lang.NullPointerException();
        }
        
        this.G = new Digraph(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                this.G.addEdge(v, w);
            }
        }


    }
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        
        BreadthFirstDirectedPaths bfpV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfpW = new BreadthFirstDirectedPaths(G, w);
        
        if (v == lastVCall && w == lastWCall) {
            return lastAncestorCall;
        }
        
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
        
        if (ancestor != -1 && bfpV.hasPathTo(ancestor) && bfpW.hasPathTo(ancestor)) {
            lastAncestorCall = bfpV.distTo(ancestor) + bfpW.distTo(ancestor);
            lastVCall = v;
            lastWCall = w;
        }

        
        return ancestor;
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.NullPointerException();
        }
        
        if (v == lastVCallArr && w == lastWCallArr) {
            return lastAncestorCallArr;
        }
        
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
        if (v == null || w == null) {
            throw new java.lang.NullPointerException();
        }
        
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
        
        if (ancestor != -1 && bfpV.hasPathTo(ancestor) && bfpW.hasPathTo(ancestor)) {
            lastAncestorCallArr = bfpV.distTo(ancestor) + bfpW.distTo(ancestor);
            lastVCallArr = v;
            lastWCallArr = w;
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
