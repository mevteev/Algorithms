import java.io.BufferedReader;
import java.io.FileReader;

public class Invertions {
    
    public static long inversions = 0;
    

    public static void main(String[] args) throws Exception {
        int[] arr = new int[100000];
        int i = 0;
        if (args.length == 0 ) {
            throw new java.lang.Exception("Specify file name");
        }
        
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line = "";
        try {
            line = br.readLine();
            while(line != null) {
                arr[i++] = Integer.parseInt(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println(line);
            e.printStackTrace();
        } finally {
            br.close();
        }
        
        int[] res = mergeSort(arr);
        
        /*
        for (i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
        */
        System.out.println(inversions);
    }
    
    
    public static int[] merge(int[] a, int[] b) {
        int n = a.length + b.length;
        int[] res = new int[n];
        
        
        
        for (int k = 0, i = 0, j = 0; k < n; k++) {
            if (i < a.length && (j >= b.length || a[i] < b[j])) {
                res[k] = a[i++];
            } else {
                res[k] = b[j++];
                
                inversions += (a.length - i);
            }
        }
        
        return res;
    }
    
    public static int[] mergeSort(int[] a) {
        
        if (a.length == 1) {
            return a;
        }
        
        int[] arr1 = new int[a.length / 2];
        int[] arr2 = new int[a.length - a.length / 2];
                
        System.arraycopy(a, 0, arr1, 0, a.length / 2);
        System.arraycopy(a, a.length / 2, arr2, 0, a.length - a.length / 2);
        
        return merge(mergeSort(arr1), mergeSort(arr2));
    }
    

}
