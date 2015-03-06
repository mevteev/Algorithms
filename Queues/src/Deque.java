import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;

    private int count;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // construct an empty deque
    public Deque() {
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = null;
        newNode.next = first;

        if (first != null) {
            first.prev = newNode;
        }

        first = newNode;

        if (last == null) {
            last = newNode;
        }

        count++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = last;
        newNode.next = null;

        if (last != null) {
            last.next = newNode;
        }

        last = newNode;

        if (first == null) {
            first = newNode;
        }

        count++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node ret = first;

        if (first != last) {
            first = first.next;

            if (first != null) {
                first.prev = null;
            }
        } else {
            first = null;
            last = null;
        }

        count--;

        return ret.item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node ret = last;

        if (first != last) {

            last = last.prev;

            if (last != null) {
                last.next = null;
            }
        } else {
            first = null;
            last = null;
        }

        count--;

        return ret.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<Integer>();

        deque.addLast(1);
        deque.addFirst(0);
        deque.addFirst(4);
        
        
        Iterator it = deque.iterator();
        it.next();
        it.next();
        it.next();
        

        //StdOut.println(deque.removeFirst());
        //StdOut.println(deque.removeLast());
        //StdOut.println(deque.removeLast());

        //StdOut.println(deque.isEmpty());
        //StdOut.println(deque.count);

        for (int i : deque) {
            StdOut.println("----- "+ i);
            for (int j : deque) {
                StdOut.println(j);
            }
        }

    }

}
