import java.util.Arrays;


public class Fast {

    private static boolean permutate(Point origin, Point[] arr, int start, int end) {
        for (int i = start; i < end; i++) {
            if (arr[i].compareTo(origin) < 0) {
                return true;
            }
        }
        
        return false;
    }
    
    private static void printSegment(Point origin, Point[] arr, int start, int stop) {
        
        StdOut.print(origin);
        
        Arrays.sort(arr, start, stop);
        for (int i = start; i < stop; i++) {
            StdOut.print(" -> " + arr[i]);
        }
        
        origin.drawTo(arr[stop - 1]);
        
        StdOut.println();
        
    }
    
    public static void main(String[] args) {
        Point[] point, sortedPoint;
        In input;
        int count;
        int pointX, pointY;
        int equalSlopes;
        double slope = 0, nextSlope;

        
        if (args.length >= 1)
        {
            input = new In(args[0]);
            // read count
            count = input.readInt();
            
            if (count < 4) {
                return;
            }
            
            point = new Point[count];
            sortedPoint = new Point[count];

            for (int i = 0; i < count; i++) {
                pointX = input.readInt();
                pointY = input.readInt();
                
                point[i] = new Point(pointX, pointY);
                
            }
            input.close();
            
            Arrays.sort(point);
            for (int i = 0; i < count; i++) {
                sortedPoint[i] = point[i];
            }
            
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            
            
            for (int i = 0; i < count; i++) {
                
                point[i].draw();

                Arrays.sort(sortedPoint, point[i].SLOPE_ORDER);
                
                slope = point[i].slopeTo(sortedPoint[1]);
                equalSlopes = 1;
                
                for (int j = 2; j < count; j++) {
                    
                    nextSlope = point[i].slopeTo(sortedPoint[j]);
                    if (slope != nextSlope) {

                        if (equalSlopes >= 3) {
                            
                            if (!permutate(point[i], sortedPoint, j - equalSlopes, j)) {
                                
                                printSegment(point[i], sortedPoint, j - equalSlopes, j);
                            
                            }
                        }
                        equalSlopes = 1;
                        slope = nextSlope;                         
                    } else {
                        equalSlopes++;
                    }
                }

                if (equalSlopes >= 3) {
                    
                    if (!permutate(point[i], sortedPoint, count - equalSlopes, count)) {

                        printSegment(point[i], sortedPoint, count - equalSlopes, count);
                        
                    }
                    equalSlopes = 0;
                }
            }
        }
    }
}
                

            
            
            
            
            
//            for (int i = 0; i < count; i++) {
//                Arrays.sort(sortedPoint, point[i].SLOPE_ORDER);
//                
//                int j = 1;
//                
//                if (sortedPoint[j].compareTo(point[i]) >= 0) {
//                    slope = point[i].slopeTo(sortedPoint[j]);
//                    equalSlopes = 1;
//                    
//                    j++;
//                    
//                    while (j < count) {
//                        nextSlope = point[i].slopeTo(sortedPoint[j]);
//                        if (slope != nextSlope || j == count - 1) {
//                            if (equalSlopes >= 3) {
//                                StdOut.print(point[i]);
//                                for (int k = j - equalSlopes; k < j ; k++) {
//                                    
//                                    StdOut.print(" -> " + sortedPoint[k]);
//                                }
//                                StdOut.println(" = " + slope + " = " + point[i].slopeTo(sortedPoint[j - 1]));
//                            }
//                            equalSlopes = 1;
//                            slope = nextSlope;                            
//                        } else {
//                            if (sortedPoint[j].compareTo(point[j - 1]) < 0) {
//                                break;
//                            }
//                            equalSlopes++;
//                        }
//                        j++;
//                    }
//                    
//                }
//                    
                    
                    
                
//                slope = point[i].slopeTo(sortedPoint[1]);
//                equalSlopes = 1;
//                
//                
//                while (j < count) {
//                    if (sortedPoint[j].compareTo(point[i]) >= 0) {
//                        nextSlope = point[i].slopeTo(sortedPoint[j]);
//                        if (slope != nextSlope || j == count - 1) {
//                            if (equalSlopes >= 3) {
//                                StdOut.print(point[i]);
//                                for (int k = j - 1; k >= j - equalSlopes; k--) {
//                                    StdOut.print(" -> " + sortedPoint[k]);
//                                }
//                                StdOut.println(" = " + slope + " = " + point[i].slopeTo(sortedPoint[j - 1]));
//                            } 
//                            equalSlopes = 1;
//                            slope = nextSlope;
//                        } else {
//                            equalSlopes++;
//                        }
//                    }
//
//                    j++;
//                }
                

