
public class Successor {

    public static void main(String[] args) {
        In data;
        int N;
        
        int x;
        
        Canonical wqf;
        
        if (args.length >= 1) {
            data = new In(args[0]);
            N = data.readInt();
           
            wqf = new Canonical(N);
            
            while (data.hasNextChar()) {
                x = data.readInt();
                if (x < N - 1) {
                    wqf.union(x, x + 1);
                }
            }
            
            
            StdOut.println(wqf.findMax(3));
            data.close();
            
        }
    }

}
