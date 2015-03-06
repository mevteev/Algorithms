import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {
    
    private TreeSet<Point2D> points;
    
    // construct an empty set of points 
    public PointSET() {
        points = new TreeSet<Point2D>();
        
    }
    
    // is the set empty?   
    public boolean isEmpty() { 
        return points.isEmpty();
    }
    
    // number of points in the set 
    public int size() {
        return points.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) { throw new java.lang.NullPointerException(); }
        points.add(p);
        
    }
    
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) { throw new java.lang.NullPointerException(); }
        return points.contains(p);
    }
    
    // draw all points to standard draw 
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }
    
    // all points that are inside the rectangle  
    public Iterable<Point2D> range(final RectHV rect) { 
        if (rect == null) { throw new java.lang.NullPointerException(); }
        return new Iterable<Point2D>() {
            
            @Override
            public Iterator<Point2D> iterator() {
                // TODO Auto-generated method stub
                return new PointIterator(rect);
            }
            
        };
    }
    
    
    private class PointIterator implements Iterator<Point2D> {
        
        private TreeSet<Point2D> rangePoints;
        private Iterator<Point2D> iterator;
        
        public PointIterator(RectHV rect) {
            rangePoints = new TreeSet<Point2D>();
            
            for (Point2D p : points) {
                if (rect.contains(p)) {
                    rangePoints.add(p);
                }
            }
            iterator = rangePoints.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Point2D next() {
            return (Point2D) iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
            
        }
        
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) { throw new java.lang.NullPointerException(); }
        double maxDist = -1;
        Point2D nearestPoint = null;
        for (Point2D curr : points) {
            double dist = p.distanceTo(curr);
            if (dist < maxDist || maxDist == -1) {
                maxDist = dist;
                nearestPoint = curr;
            }
        }
        return nearestPoint;
    } 

    public static void main(String[] args) {
        PointSET ps = new PointSET();
        
        ps.insert(new Point2D(0.206107, 0.095492));
        ps.insert(new Point2D(0.975528, 0.654508));
        ps.insert(new Point2D(0.024472, 0.345492));
        ps.insert(new Point2D(0.793893, 0.095492));
        ps.insert(new Point2D(0.793893, 0.904508));
        ps.insert(new Point2D(0.975528, 0.345492));
        ps.insert(new Point2D(0.206107, 0.904508));
        ps.insert(new Point2D(0.206107, 0.904508));
        ps.insert(new Point2D(0.500000, 0.000000));
        ps.insert(new Point2D(0.024472, 0.654508));
        ps.insert(new Point2D(0.500000, 1.000000));
        
        StdOut.println(ps.nearest(new Point2D(1, 1)));
        
        for (Point2D p : ps.range(new RectHV(0, 0, 1, 1))) {
            StdOut.println(p);
        }

    }
    
    

}
