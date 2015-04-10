import java.util.ArrayList;
import java.util.Arrays;


public class SeamCarver {
    private Picture picture;
    private double[][] energyMatrix;
    
    private class Vertice {
        public Vertice(int x, int y) {
            this.x = x;
            this.y = y;
            this.energyTo = Double.MAX_VALUE; 
        }
        
        int x;
        int y;
        double energyTo;
        Vertice edgeTo;
    }
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
        energyMatrix = new double[width()][height()];
        fillEnergy();
        
        
    }
    
    // current picture
    public Picture picture() {
        return this.picture;
    }
    
    // width of current picture
    public int width() {
        return this.picture.width();
    }
    
    // height of current picture
    public int height() {
        return this.picture.height();
    }
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        return energyMatrix[x][y];
    }
    
    private double calcEnergy(int x, int y) {
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return  3*255*255;
        } else {
            double mRx = picture.get(x - 1, y).getRed() - picture.get(x + 1,  y).getRed();
            double mGx = picture.get(x - 1, y).getGreen() - picture.get(x + 1,  y).getGreen();
            double mBx = picture.get(x - 1, y).getBlue() - picture.get(x + 1,  y).getBlue();
            
            double mRy = picture.get(x, y - 1).getRed() - picture.get(x,  y + 1).getRed();
            double mGy = picture.get(x, y - 1).getGreen() - picture.get(x,  y + 1).getGreen();
            double mBy = picture.get(x, y - 1).getBlue() - picture.get(x,  y + 1).getBlue();
            
            double mDx = mRx * mRx + mGx * mGx + mBx * mBx;
            double mDy = mRy * mRy + mGy * mGy + mBy * mBy;
            
            return mDx + mDy;
        }

    }
    
    private void fillEnergy() {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energyMatrix[x][y] = calcEnergy(x ,y);
            }
        }
    }
    
   
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] seam = new int[picture.height()];
        
        return seam;
        
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[picture.width()];
        
        findVerticalSeam(0);
        
        return seam;
        
    }
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        
        
    }
    
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        
    }
    
    private double findVerticalSeam(int x) {
        double res = 0;
        
        int tempWidth;
        if (width() < height() * 2) {
            tempWidth = width();
        } else {
            tempWidth = height() * 2;
        }
        
        double temp[][] = new double[tempWidth][height()];
        int edgeTo[][] = new int[tempWidth][height()];
        
        //Create array of vertices
        for (int iY = 0; iY < height() - 1; iY++ )
        {
            for (int iX = x - iY; iX <= x + iY; iX++) {
                if (iY == 0) {
                    temp[iX][iY] = energy(iX, iY);
                }
                if (iX >= 0 && iX < width()) {
                    //refresh 3 vertices below
                    for (int w = iX - 1; w <= iX + 1; w++ ) {
                        if (w >= 0 && w < width()) {
                            double en = temp[iX][iY] + energy(w, iY + 1);
                            if (temp[w][iY + 1] == 0 || en < temp[w][iY + 1]) {
                                temp[w][iY + 1] = en;
                                edgeTo[w][iY + 1] = iX;
                            }
                        }
                    }
                }
            }
        }
        //get minimum
        int pos = -1;
        double min = Double.MAX_VALUE;
        for (int i = x - height(); i <= x + height(); i++) {
            if (i >= 0 && i < width()) {
                if (temp[i][height() - 1] != 0 && temp[i][height() - 1] < min) {
                    min = temp[i][height() - 1];
                    pos = i;
                }
            }
        }
        
        //build path
        int[] seam = new int[height()];
        seam[height() - 1] = pos;
        for (int i = height() - 2; i >=0; i--) {
            seam[i] = edgeTo[seam[i + 1]][i + 1];
        }
        
        StdOut.print(min);
        StdArrayIO.print(seam);
        
        
        return res;
    }
    

    
    
    private int getIndex(int x, int y) {
        return y * width() + x;
    }
    
    private int getX(int index) {
        return index % width();
    }
    
    private int getY(int index) {
        return index / width();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
