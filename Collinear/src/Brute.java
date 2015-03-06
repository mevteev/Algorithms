import java.util.Arrays;


public class Brute {

    public static void main(String[] args) {
        Point[] point;
        In input;
        int count;
        int pointX, pointY;
        double pq, pr, ps;
        
        if (args.length >= 1)
        {
            input = new In(args[0]);
            // read count
            count = input.readInt();
            point = new Point[count];
            for (int i = 0; i < count; i++) {
                pointX = input.readInt();
                pointY = input.readInt();
                
                point[i] = new Point(pointX, pointY);
            }
            input.close();
            
            Arrays.sort(point);
            
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            
            for (int p = 0; p < count; p++) {
                point[p].draw();
                for (int q = p + 1; q < count; q++) {
                    for (int r = q + 1; r < count; r++) {
                        for (int s = r + 1; s < count; s++) {
                            pq = point[p].slopeTo(point[q]);
                            pr = point[p].slopeTo(point[r]);
                            ps = point[p].slopeTo(point[s]);
                            if (pq == pr && pr == ps) {
                                StdOut.println(point[p] + " -> " + point[q] + " -> " + point[r] + " -> " + point[s]);

                                
                                point[p].drawTo(point[s]);
                                
                            }
                        }
                    }
                }
            }
            
            
        }
    }

}
