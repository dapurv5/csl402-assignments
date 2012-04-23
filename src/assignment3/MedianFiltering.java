package assignment3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import tao.image.ColorMode;
import tao.image.IPUtil;

public class MedianFiltering {

  public static final int N     = 3;
  private static int m          = -1;
  private static int n_m        = 0;
  private static int t          = (N*N)/2;

  private static int[] H        = null;

  public static int[][] medianFilter(int[][] imgMatrix){
    int h = imgMatrix.length;
    int w = imgMatrix[0].length;        
    int[][] tImgMatrix = new int[h][w];

    int l = (N-1)/2;
    for(int i = l; i < h-l; i++){
      for(int j = l; j < w-l; j++){
        tImgMatrix[i][j] = calcMedian(imgMatrix, i, j);
      }
    }
    return tImgMatrix;
  }



  private static int calcMedian(int[][] imgMatrix, int I, int J) {

    int k = 0;
    int l   = (N-1)/2;
    int A[] = new int[N*N];
    

    //////////////////////////////
    int z =0;
    int debug[] = new int[N*N];
    for(int a = I-l; a <= I+l; a++){
      for(int b = J-l; b <= J+l; b++){
        debug[z] = imgMatrix[a][b];
        z++;
      }
    }
    Arrays.sort(debug);
    /////////////////////////////


    if(J == l){
      n_m    = 0;      
      H      = new int[256];

      for(int i = I-l; i <= I+l; i++){
        for(int j = J-l; j <= J+l; j++){
          A[k]         = imgMatrix[i][j];
          H[A[k]]++;
          k++;
        }
      }
      Arrays.sort(A);
      m = A[t];

      for(int i = 0; (i <= m) && (i < 256); i++){
        n_m += H[i];
      }
      
//      System.out.println("\n\nBeginning 0f row ====================");
//      System.out.println("sorted A is "+Arrays.toString(A));
//      System.out.println("median is "+m);
//      System.out.println("n_m is "+n_m);
    }
    else{

      for(int i = I-l; i <= I+l; i++){
        int pixelDead = imgMatrix[i][J-1];
        int pixelBorn = imgMatrix[i][J+1];
//        System.out.println("\nremoving "+pixelDead+" Adding "+pixelBorn);/////////
        H[pixelDead]--;
        H[pixelBorn]++;
        n_m = (pixelDead <= m)?n_m-1:n_m;
        n_m = (pixelBorn <= m)?n_m+1:n_m;
      }

      if(n_m > t){
        while(n_m > t  && m>0){
          n_m = n_m - H[m];
          m   = m-1;
        }
      }
      else if(n_m < t){
        while(n_m < t && m<256){
          m = m + 1;
          n_m = n_m + H[m];
        }
      }

//      System.out.println("2) median is "+m);
//      System.out.println("2) n_m is "+n_m);
//      System.out.println("median should be "+debug[t]);

    }
    return debug[t];//m;
//    return m;//////////////////
  }



  public static void execute(String name) throws IOException{    
    String[] arg = name.split("\\.");
    String filename = arg[0];
    String extension = arg[1];

    String path = filename + "."+extension;

    BufferedImage bimg = ImageIO.read(new File(path));


    IPUtil.setMode(ColorMode.RED);
    int[][] rM = IPUtil.readImageAsMatrix(bimg);
    int[][] rE = MedianFiltering.medianFilter(rM);

    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = IPUtil.readImageAsMatrix(bimg);
    int[][] gE = MedianFiltering.medianFilter(gM);

    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = IPUtil.readImageAsMatrix(bimg);
    int[][] bE = MedianFiltering.medianFilter(bM);

    Color[][] res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "medianFiltered-"+path, extension);        
  }


  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    String name = "balloons_noisy.png";
//    String name = "x.png";
    //  String name = "lena_color_256.tif";
    execute(name);
  }

}
