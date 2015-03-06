/*Union-find with specific canonical element. Add a method find() to the union-find data type 
so that find(i) returns the largest element in the connected component containing i. 
The operations, union(), connected(), and find() should all take logarithmic time or better.
 
For example, if one of the connected components is {1,2,6,9} , then the find() method 
should return 9  for each of the four elements in the connected components.
*/
        
 
public class Canonical extends WeightedQuickUnionUF {
    
    protected int[] max; 
    
    public Canonical(int N) {
        super(N);
        max = new int[N];
        
        for (int i = 0; i < N; i++) {
            max[i] = i;
        }        
    }
    
    
    public int findMax(int i) {
        return max[find(i)];
    }
    
    protected void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if   (sz[rootP] < sz[rootQ]) { 
            id[rootP] = rootQ; 
            sz[rootQ] += sz[rootP];
            if (max[rootQ] < max[rootP]) {
                max[rootQ] = max[rootP];
            }
        } else { 
            id[rootQ] = rootP; 
            sz[rootP] += sz[rootQ];
            if (max[rootP] < max[rootQ]) {
                max[rootP] = max[rootQ];
            }
            
        }
        count--;
    }
    
    
    

    public static void main(String[] args) {
        In data;
        int N;
        int member1, member2;
        Canonical wqf;
        
        if (args.length >= 1) {
            data = new In(args[0]);
            N = data.readInt();
           
            wqf = new Canonical(N);
            
            while (data.hasNextChar()) {
                member1 = data.readInt();
                member2 = data.readInt();
                wqf.union(member1, member2);
            }
            
            
            StdOut.println(wqf.findMax(2));
            StdOut.println(wqf.findMax(0));
            data.close();
            
        }

    }

}
