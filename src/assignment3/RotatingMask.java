package assignment3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tao.image.ColorMode;
import tao.image.IPUtil;

public class RotatingMask {

  public static final int N = 3;

  public static float calcDispersion(int[][] imgMatrix, int Ii, int If, int Ji, int Jf ){
    float disp    = -1;
    int height    = imgMatrix.length;
    int width     = imgMatrix[0].length;
    float sigma   = 0;
    float sigmaSq = 0;    

    for(int i = Ii; i <= If; i++){
      for(int j = Ji; j <= Jf; j++){
        if(i < 0 || i >= height || j < 0 || j >= width){return Float.MAX_VALUE;}
        sigma   += imgMatrix[i][j];
        sigmaSq += (imgMatrix[i][j])^2;
      }
    }

    int n = (If-Ii+1)*(Jf-Ji+1);    
    disp  = (sigmaSq - (sigma*sigma)/n)/n; 

    return disp;
  }
  
  
  
  public static int calcMean(int[][] imgMatrix, int Ii, int If, int Ji, int Jf ){
    int mean      = -1;
    int height    = imgMatrix.length;
    int width     = imgMatrix[0].length;
    float sigma   = 0;    

    for(int i = Ii; i <= If; i++){
      for(int j = Ji; j <= Jf; j++){
        if(i < 0 || i >= height || j < 0 || j >= width){return -1;}
        sigma   += imgMatrix[i][j];
      }
    }

    int n = (If-Ii+1)*(Jf-Ji+1);
//    System.out.println("sigma is "+sigma);
    mean  = (int)sigma/n; 

    return mean;
  }



  /**
   * Returns the mean corresponding to minimum dispersion mask.
   * @param I the position of the pixel along the height.
   * @param J the position of the pixel along the width.
   */
  public static int calcMinMean(int[][] imgMatrix, int I, int J){

    float minDisp = Float.MAX_VALUE;
    int minAvg    = Integer.MAX_VALUE;
    
    for(int i = I-N+1; i <= I; i++){
      for(int j = J-N+1; j<= J; j++){        
        float disp = calcDispersion(imgMatrix, i, i+2, j, j+2);
        if(disp < minDisp){
          minDisp = disp;
          minAvg  = calcMean(imgMatrix, i, i+2, j, j+2);
        }
      }
    }
    
    return minAvg;
  }
  
  
  public static int[][] rotateMask(int[][] imgMatrix){
    int h = imgMatrix.length;
    int w = imgMatrix[0].length;        
    int[][] tImgMatrix = new int[h][w];
    
    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        tImgMatrix[i][j] = calcMinMean(imgMatrix, i, j);        
// System.out.println("Replacing ("+i+","+j+","+imgMatrix[i][j]+") with"+tImgMatrix[i][j]);
      }
    }
    return tImgMatrix;

  }

  public static void execute(String name) throws IOException{    
    String[] arg = name.split("\\.");
    String filename = arg[0];
    String extension = arg[1];
    
    String path = filename + "."+extension;
    
    BufferedImage bimg = ImageIO.read(new File(path));

    
    IPUtil.setMode(ColorMode.RED);
    int[][] rM = IPUtil.readImageAsMatrix(bimg);
    int[][] rE = RotatingMask.rotateMask(rM);
    
    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = IPUtil.readImageAsMatrix(bimg);
    int[][] gE = RotatingMask.rotateMask(gM);
    
    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = IPUtil.readImageAsMatrix(bimg);
    int[][] bE = RotatingMask.rotateMask(bM);
    
    Color[][] res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "rotateMasked-"+path, extension);
            
  }

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
//    String name = "noise_3.tif";
//    String name = "lena_color_256.tif";
//    String name = "guassian_noise.gif";
    String name = "x.png";
    execute(name);
  }

}
