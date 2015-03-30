
public class Outcast {
    
    private WordNet wordnet;
    
 // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    
 // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] d = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            d[i] = 0;
            //StdOut.println(nouns[i] + ": ");
            for (int j = 0; j < nouns.length; j++) {
                //StdOut.println(nouns[i] + " ->" + nouns[j] + " = " + wordnet.distance(nouns[i], nouns[j]));
                d[i] = d[i] + wordnet.distance(nouns[i], nouns[j]);
            }
        }
        
        int maxPos = 0;
        for (int i = 1; i < nouns.length; i++) {
            if (d[i] > d[maxPos]) {
                maxPos = i;
            }
        }
        
        return nouns[maxPos];
        
        
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        
        //StdOut.println(wordnet.distance("Sub-Saharan_Africa", "Paterson"));
        //StdOut.println(wordnet.sap("Sub-Saharan_Africa", "Paterson"));
        
        
        
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }

}
