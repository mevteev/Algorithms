/*Social network connectivity. Given a social network containing N  members and a log file 
containing M  timestamps at which times pairs of members formed friendships, design an 
algorithm to determine the earliest time at which all members are connected 
(i.e., every member is a friend of a friend of a friend ... of a friend). 
Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. 
The running time of your algorithm should be MlogN  or better and use extra space 
proportional to N .
*/
public class SNConnectivity {

    public static void main(String[] args) {
        In data;
        int N, M;
        int member1, member2, timestamp;
        int res;
        WeightedQuickUnionUF wqf;
        
        if (args.length >= 1) {
            data = new In(args[0]);
            N = data.readInt();
            M = data.readInt();
            
            wqf = new WeightedQuickUnionUF(N);
            
            res = -1;
            
            for (int i = 0; i < M; i++) {
                member1 = data.readInt();
                member2 = data.readInt();
                timestamp = data.readInt();
                
                wqf.union(member1, member2);
                
                if (wqf.count() == 1) {
                    res = timestamp;
                    break;
                }
            }
            
            data.close();
            
            if (res > 0) {
                StdOut.println(res);
            } else {
                StdOut.println("Not connected");
            }
        }

    }

}
