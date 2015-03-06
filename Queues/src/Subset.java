
public class Subset {

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int pos = 0;
        int k = Integer.parseInt(args[0]);
        if (args.length >= 1 && k > 0) {
            while (!StdIn.isEmpty()) {
                String item = StdIn.readString();

                pos++;
                if (pos <= k) {
                    rq.enqueue(item);
                } else {
                    if (StdRandom.uniform(pos) < k) {
                        rq.dequeue();
                        rq.enqueue(item);
                    }
                }
            } 
            for (String str : rq) {
                StdOut.println(str);
            }
        } else {
            //StdOut.println("Please specify k");
        }

    }

}
