
public class HeightedQuickUnionUF extends WeightedQuickUnionUF {
    
    protected int[] hg;

    public HeightedQuickUnionUF(int N) {
        super(N);
        
        hg = new int[N];
        
        for (int i = 0; i < N; i++) {
            hg[i] = 1;
        }
    }
    
    protected void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make shorter root point to taller one
        if   (hg[rootP] < hg[rootQ]) { id[rootP] = rootQ; hg[rootQ]++; }
        else                         { id[rootQ] = rootP; hg[rootP]++; }
        count--;
    }    

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
