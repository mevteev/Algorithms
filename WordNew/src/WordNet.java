import java.util.HashMap;
import java.util.Iterator;


public class WordNet {
    
    private HashMap<Integer, String> noun;
    private HashMap<Integer, String> gloss;
    private Digraph digraph;
    private SAP sap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // Read synsets
        In in = new In(synsets);
        String strSynsets[] = in.readAllLines();
        
        noun = new HashMap<Integer, String>();
        gloss = new HashMap<Integer, String>();
        
        for (String s : strSynsets) {
            String parts[] = s.split(",");
            if (parts.length >= 3) {
                noun.put(Integer.valueOf(parts[0]), parts[1]);
                gloss.put(Integer.valueOf(parts[0]), parts[1]);
            }
        }
        in.close();
        
        digraph = new Digraph(strSynsets.length);

        
        //StdOut.println(synset.get(0)[1]);
        
        // Read hypernyms
        in = new In(hypernyms);
        String strHypernyms[] = in.readAllLines();
        
        for (String s : strHypernyms) {
            String parts[] = s.split(",");
            
            for (int i = 1; i < parts.length; i++) {
                digraph.addEdge(Integer.valueOf(parts[i]), Integer.valueOf(parts[0]));
            }
            
        }
        in.close();
        
        //StdOut.println(digraph.toString());
        
        sap = new SAP(digraph);
        
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return noun.values();
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return noun.containsValue(word);
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        
        return -1; //sap.length(, w)
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "";
    }
    
    // do unit testing of this class
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        WordNet wn = new WordNet("test/synsets15.txt", "test/hypernyms15Tree.txt");

    }
    
    
//    private class WordIterator implements Iterator<String> {
//        
//        Digraph digraph; 
//        HashMap<Integer, String[]> synset;
//        Iterator<Integer> digraphIterator;
//        
//        
//        public WordIterator(Digraph digraph, HashMap<Integer, String[]> synset) {
//            this.digraph = digraph;
//            this.synset = synset;
//            digraphIterator = digraph.adj(0).iterator();
//        }
//        
//        @Override
//        public boolean hasNext() {
//            return digraphIterator.hasNext();
//        }
//        
//        @Override
//        public String next() {
//            return synset.get(digraphIterator.next())[0];
//        }
//        
//        @Override
//        public void remove() {
//            throw new UnsupportedOperationException();
//            
//        }
//        
//    }

}
