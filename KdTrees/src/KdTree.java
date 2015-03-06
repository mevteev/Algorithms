import java.util.Iterator;
import java.util.TreeSet;


public class KdTree {
    
    private Node root;
    
    private class Node {
        private Node left, right;
        private Point2D point;
        private boolean vertical;
        private int N;
        private Node parent;
        
        private RectHV rect = null;
        
        public Node(Point2D point, boolean vertical, int N, Node parent) {
            this.point = point;
            this.vertical = vertical;
            this.N = N;
            this.parent = parent;
            
            //this.rect = new RectHV(getLeftEdge(), getBottomEdge(), getRightEdge(), getTopEdge());
        }
        
        public void setRect() {
            rect = new RectHV(getLeftEdge(), getBottomEdge(), getRightEdge(), getTopEdge());
        }
        
        public double getTopEdge() {
            Node nParent = this.parent;
            if (nParent != null) {
                    
                if (nParent.vertical) {
                    return nParent.getTopEdge();
                }
                if (nParent.left == this) {
                    return nParent.point.y();
                } else if (nParent.parent != null) {
                    return nParent.parent.getTopEdge();
                }
            }
            return 1;
        }
        
        public double getBottomEdge() {
            Node nParent = this.parent;
            if (nParent != null) {
                
                if (nParent.vertical) {
                    return nParent.getBottomEdge();
                }
                if (nParent.right == this) {
                    return nParent.point.y();
                } else if (nParent.parent != null) {
                    return nParent.parent.getBottomEdge();
                }
            }
            return 0;
        }    
        
        public double getRightEdge() {
            Node nParent = this.parent;
            if (nParent != null) {
                    
                if (!nParent.vertical) {
                    return nParent.getRightEdge();
                }
                if (nParent.left == this) {
                    return nParent.point.x();
                } else if (nParent.parent != null) {
                    return nParent.parent.getRightEdge();
                }
            }
            return 1;
        }        
        
        public double getLeftEdge() {
            Node nParent = this.parent;
            if (nParent != null) {
                    
                if (!nParent.vertical) {
                    return nParent.getLeftEdge();
                }
                if (nParent.right == this) {
                    return nParent.point.x();
                } else if (nParent.parent != null) {
                    return nParent.parent.getLeftEdge();
                }
            }
            return 0;
        }
        
        public RectHV getRect() {
            if (rect == null) {
                setRect();
            }
            //return new RectHV(getLeftEdge(), getBottomEdge(), getRightEdge(), getTopEdge());
            return rect;
        }
        
    }
    
    // construct an empty set of points 
    public KdTree() { }
    
    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // number of points in the set 
    public int size() { 
        return size(root);
    }
    
    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) { throw new java.lang.NullPointerException(); }
        put(p);
        
    }
    
    private void put(Point2D p) {
        Node check = get(root, p);
        if (check != null) {
            return;
        }
        root = put(root, p, true, null);
    }
    
    private Node put(Node x, Point2D p, boolean direction, Node parent) {
        if (p == null) { throw new java.lang.NullPointerException(); }
        
        int cmp;
        if (x == null) {
            return new Node(p, direction, 1, parent);
        }
        if (x.vertical) {
            cmp = compareX(x.point, p);
        } else {
            cmp = compareY(x.point, p);
        }
        
        if (!x.point.equals(p)) {
            if (cmp > 0) {
                x.left = put(x.left, p, !direction, x);
            }
            else if (cmp <= 0) {
                x.right = put(x.right, p, !direction, x);
            }
            else {
                x.point = p;
            }
        }
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    
    private int compareX(Point2D p, Point2D q) {
        if (p.x() < q.x()) return -1;
        if (p.x() > q.x()) return +1;
        return 0;
    }
    
    private int compareY(Point2D p, Point2D q) {
        if (p.y() < q.y()) return -1;
        if (p.y() > q.y()) return +1;
        return 0;
    }    
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) { throw new java.lang.NullPointerException(); }
        return get(root, p) != null;
    }
    
    private Node get(Node x, Point2D key) {
        int cmp;
        if (x == null) return null;
        
        if (x.point.compareTo(key) == 0) {
            return x;
        }
        
        if (x.vertical) {
            cmp = compareX(x.point, key);
        } else {
            cmp = compareY(x.point, key);
        }        

        if      (cmp > 0) return get(x.left, key);
        else if (cmp <= 0) return get(x.right, key);
        else              return null;
    }
    
    

    // draw all points to standard draw 
    public void draw() {
        draw(root);
    }
    
    
    private void draw(Node nRoot) {
        if (nRoot == null) {
            return;
        }
        StdDraw.setPenColor();
        StdDraw.filledCircle(nRoot.point.x(), nRoot.point.y(), 0.005);
        
        if (nRoot.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(nRoot.point.x(), nRoot.getTopEdge(), nRoot.point.x(), nRoot.getBottomEdge());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(nRoot.getLeftEdge(), nRoot.point.y(), nRoot.getRightEdge(), nRoot.point.y());
            
        }
        
        draw(nRoot.left);
        draw(nRoot.right);        
        
        
    }
    
    
    
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(final RectHV rect) { 
        if (rect == null) { throw new java.lang.NullPointerException(); }
        
        return new Iterable<Point2D>() {
            
            @Override
            public Iterator<Point2D> iterator() {
                return new RangeIterator(rect);
            }
            
        };        
    }
    
    
    private class RangeIterator implements Iterator<Point2D> {

        private TreeSet<Point2D> rangePoints;
        private RectHV rect;
        private Iterator<Point2D> iterator;
        
        public RangeIterator(RectHV rect) {
            rangePoints = new TreeSet<Point2D>();
            this.rect = rect;
            
            if (root != null) {
                buildRange(root);
            }
            
            iterator = rangePoints.iterator();
            
        }
        
//        private void buildRange1(Node nRoot) {
//            if (rect.contains(nRoot.point)) {
//                rangePoints.add(nRoot.point);
//            }
//            if (nRoot.left != null) {
//                if (rect.intersects(nRoot.left.getRect())) {
//                    buildRange(nRoot.left);
//                }
//            }
//            
//            if (nRoot.right != null) {
//                if (rect.intersects(nRoot.right.getRect())) {
//                    buildRange(nRoot.right);
//                }
//            }
//        }
        
        private void buildRange(Node nRoot) {
            if (nRoot == null) return;
            
            if (rect.contains(nRoot.point)) {
                rangePoints.add(nRoot.point);
            }
            
            if (!rect.intersects(nRoot.getRect())) {
                return;
            }
            
            double x, y;
            x = nRoot.point.x();
            y = nRoot.point.y();
            
            if (nRoot.vertical && rect.xmin() <= x && x <= rect.xmax() 
                    || !nRoot.vertical && rect.ymin() <= y && y <= rect.ymax()) {
                buildRange(nRoot.left);
                buildRange(nRoot.right);
            }
            else {
                if (nRoot.vertical) {
                    if (rect.xmax() < x) {
                        buildRange(nRoot.left);
                    } else {
                        buildRange(nRoot.right);
                    }
                } else {
                    if (rect.ymax() < y) {
                        buildRange(nRoot.left);
                    } else {
                        buildRange(nRoot.right);
                    }
                }
            }
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
        if (isEmpty()) { return null; }
        Point2D nearP = near(root, p);

        return nearP;
    }
    
    private Point2D near(Node nRoot, Point2D p) {
        double minDistance = nRoot.point.distanceSquaredTo(p);
        Point2D leftPoint = null, rightPoint = null;
        
        //StdOut.println(nRoot.point + " " + minDistance);
        
        Node firstSubTree;
        Node secondSubTree;
        
        double leftDist = Double.MAX_VALUE;
        double rightDist = Double.MAX_VALUE;
        

        if (nRoot.left != null && nRoot.left.getRect().contains(p)) {
            firstSubTree = nRoot.left;
            secondSubTree = nRoot.right;
        } else {
            firstSubTree = nRoot.right;
            secondSubTree = nRoot.left;
        }
        
        
        if (firstSubTree != null && firstSubTree.getRect().distanceSquaredTo(p) < minDistance) {
            leftPoint = near(firstSubTree, p);
            if (leftPoint != null) {
                leftDist = leftPoint.distanceSquaredTo(p);
                if (leftDist < minDistance) {
                    minDistance = leftDist;
                }
            }
        }
        
        

        if (secondSubTree != null && secondSubTree.getRect().distanceSquaredTo(p) < minDistance) {
            rightPoint = near(secondSubTree, p);
            if (rightPoint != null) {
                rightDist = rightPoint.distanceSquaredTo(p);
            }
        }
        
        if (minDistance < leftDist && minDistance < rightDist) {
            return nRoot.point;
        }
        
        if (leftDist < rightDist && leftDist <= minDistance) {
            return leftPoint;
        }
        
        return rightPoint;
                
    }
    
    // level order traversal
    private Iterable<Point2D> levelOrder() {
        Queue<Node> queue = new Queue<Node>();
        Queue<Point2D> keys = new Queue<Point2D>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.point);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }    
    
    

    public static void main(String[] args) {
        KdTree ps = new KdTree();
        
        ps.insert(new Point2D(0.206107, 0.095492));
        ps.insert(new Point2D(0.975528, 0.654508));
        ps.insert(new Point2D(0.024472, 0.345492));
        ps.insert(new Point2D(0.793893, 0.095492));
        ps.insert(new Point2D(0.793893, 0.904508));
        ps.insert(new Point2D(0.975528, 0.345492));
        ps.insert(new Point2D(0.206107, 0.904508));
        ps.insert(new Point2D(0.500000, 0.000000));
        ps.insert(new Point2D(0.024472, 0.654508));
        ps.insert(new Point2D(0.500000, 1.000000));
        ps.insert(new Point2D(0.975528, 0.345492));
        
        StdOut.println(ps.nearest(new Point2D(0.81, 0.30)));
        
        StdOut.println(ps.size());
        
        //StdOut.println(ps.contains(new Point2D(0.1, 0.1)));
        
 //       for (Point2D n : ps.levelOrder()) {
 //           StdOut.println(n);
 //       }
        
        //Node n = ps.root.left.left.right.right;
        
//        Node n = ps.root.left;
//        
//        StdOut.println(n.point);
//        StdOut.println(n.getLeftEdge());
//        StdOut.println(n.getRightEdge());
//        
//        StdOut.println(n.getTopEdge());
//        StdOut.println(n.getBottomEdge());
//        
//        StdOut.println(n.getLeftEdge());
//        StdOut.println(n.getRightEdge());    
        
        ps.draw();
        
//        StdOut.println(ps.root.right.right.left.point);
//        StdOut.println(ps.root.right.right.left.right.point);

        
    }

}
