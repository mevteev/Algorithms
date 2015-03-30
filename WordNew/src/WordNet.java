import java.util.ArrayList;
import java.util.HashMap;


public class WordNet {
    
    private ArrayList<Integer> nounIdx;
    private ArrayList<String>  nounStr;
    
    private ArrayList<Integer> glossIdx;
    private ArrayList<String>  glossStr;    
    
    private HashMap<String, ArrayList<Integer>> nounsList;
    
    private Digraph digraph;
    private SAP sap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // Read synsets
        In in = new In(synsets);
        String[] strSynsets = in.readAllLines();
        
        nounIdx = new ArrayList<Integer>();
        nounStr = new ArrayList<String>();
        
        glossIdx = new ArrayList<Integer>();
        glossStr = new ArrayList<String>();
        
        nounsList = new HashMap<String, ArrayList<Integer>>();
        
        
        for (String s : strSynsets) {
            String[] parts = s.split(",");
            if (parts.length >= 3) {
                
                String[] nouns = parts[1].split(" ");
                
                nounIdx.add(Integer.valueOf(parts[0]));
                nounStr.add(parts[1]);
                
                
                for (int i = 0; i < nouns.length; i++) {
                    
                    ArrayList<Integer> arrIdx = nounsList.get(nouns[i]);
                    if (arrIdx == null) {
                        arrIdx = new ArrayList<Integer>();
                    }
                        
                    arrIdx.add(Integer.valueOf(parts[0]));
                    nounsList.put(nouns[i], arrIdx);
                }
                
                glossIdx.add(Integer.valueOf(parts[0]));
                glossStr.add(parts[2]);
                
            }
        }
        in.close();
        
        digraph = new Digraph(strSynsets.length);
        
        //StdOut.println(synset.get(0)[1]);
        
        // Read hypernyms
        in = new In(hypernyms);
        String[] strHypernyms = in.readAllLines();
        
        for (String s : strHypernyms) {
            String[] parts = s.split(",");
            
            for (int i = 1; i < parts.length; i++) {
                digraph.addEdge(Integer.valueOf(parts[0]), Integer.valueOf(parts[i]));
            }
            
        }
        in.close();
        
        // Checking for cycle
        DirectedCycle dCycle = new DirectedCycle(digraph);
        if (dCycle.hasCycle()) {
            throw new java.lang.IllegalArgumentException("Cycle"); 
        }
        
        // Checking for two roots
        int root = 0;
        for (int i = 0; i < digraph.V(); i++) {
            Iterable<Integer> adj = digraph.adj(i);
            if (!adj.iterator().hasNext()) {
                root++;
            }
        }
        if (root > 1) {
            throw new java.lang.IllegalArgumentException("Two roots"); 
        }
        
        
        //StdOut.println(digraph.toString());
        
        sap = new SAP(digraph);
        
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        //return noun.values();
        return nounsList.keySet();
    }
    
    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        
        if (word == null) {
            throw new java.lang.NullPointerException();
        }
        //return noun.containsValue(word);
        //return nounStr.contains(word);
        return nounsList.containsKey(word);
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        
        if (nounA == null || nounB == null) {
            throw new java.lang.NullPointerException();
        }

        // int indexA = nounStr.indexOf(nounA);
        // int indexB = nounStr.indexOf(nounB);
        
        ArrayList<Integer> indexA = nounsList.get(nounA);
        ArrayList<Integer> indexB = nounsList.get(nounB);
        
        
        if (indexA == null || indexB == null) {
            throw new java.lang.IllegalArgumentException("Noun not found");
        }
        
        
        return sap.length(indexA, indexB);
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        
        if (nounA == null || nounB == null) {
            throw new java.lang.NullPointerException();
        }
        
        ArrayList<Integer> indexA = nounsList.get(nounA);
        ArrayList<Integer> indexB = nounsList.get(nounB);
        
        if (indexA == null || indexB == null) {
            throw new java.lang.IllegalArgumentException("Noun not found");
        }
        
        
        int index = sap.ancestor(indexA, indexB);
        
        return nounStr.get(index);
    }
    
    // do unit testing of this class
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        WordNet wn = new WordNet(args[0], args[1]);
        
        
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            int length   = wn.distance(v, w);
            String ancestor = wn.sap(v, w);
            StdOut.printf("length = %d, ancestor = %s\n", length, ancestor);
        }
        

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
