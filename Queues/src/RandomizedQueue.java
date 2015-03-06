import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] arr;
    private int count;

    private class ListIterator implements Iterator<Item> {
        private int i;
        private int[] index;
        
        public ListIterator() {
            i = 0;
            index = new int[count];
            for (int a = 0; a < count; a++) {
                index[a] = a;
            }
            
            StdRandom.shuffle(index);
        }

        public boolean hasNext() {
            return i < count;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return arr[index[i++]];
        }        
    }
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
        count = 0;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return count == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return count;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (count == arr.length) {
            resize(2 * arr.length);
        }
        arr[count++] = item;
    }
    
    // delete and return a random item
    public Item dequeue() {
        if (count == 0) {
            throw new java.util.NoSuchElementException();
        }
        Item item;
        int pos = StdRandom.uniform(count);
        item = arr[pos];
        
        arr[pos] = arr[count - 1];
        arr[count - 1] = null;
        
        count--;
        
        if (count > 0 && count == arr.length / 4) {
            resize(arr.length / 2);
        }
        
        return item;
    }
    
    public Item sample() {
        if (count == 0) {
            throw new java.util.NoSuchElementException();
        }

        return arr[StdRandom.uniform(count)];
    }
    
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        
        rq.enqueue(StdRandom.uniform(100));
        rq.enqueue(StdRandom.uniform(100));
        rq.enqueue(StdRandom.uniform(100));
        rq.enqueue(StdRandom.uniform(100));
        rq.enqueue(StdRandom.uniform(100));
        
        for (int i : rq) {
            StdOut.println("----- "+ i);
            for (int j : rq) {
                StdOut.println(j);
            }
        }
        
        //StdOut.println(rq.count);
    }

}
