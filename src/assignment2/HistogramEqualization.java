package assignment2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tao.image.ColorMode;
import tao.image.IPUtil;


public class HistogramEqualization {
  
  int G = IPUtil.G;

  public int[] cumulate(int[] H){
    int[] Hc = new int[H.length];
    Hc[0]    = 0;
    
    for(int i = 1; i < H.length; i++){
      Hc[i] = Hc[i-1] + H[i];
    }
    return Hc;
  }
  
  
  public int[][] equaliseHistogram(int[][] imgMatrix){
    float h = imgMatrix.length;
    float w = imgMatrix[0].length;
    
    int[] H  = IPUtil.getHistogram(imgMatrix);
    int[] Hc = cumulate(H);
    
    int[][] tImgMatrix = new int[(int)h][(int)w]; 
    
    int[] T = new int[G];
    float k   = (G-1)/(h*w);
    for(int p = 0; p < 256; p++){
      T[p] = (int)(k * Hc[p]);
    }
    
    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        tImgMatrix[i][j] = T[imgMatrix[i][j]];
      }
    }
    
    return tImgMatrix;
  }


  /**
   * @param args
   * @throws IOException 
   */
  public static void execute(String name) throws IOException {
    HistogramEqualization he = new HistogramEqualization();
    
    String[] arg = name.split("\\.");
    String filename = arg[0];
    String extension = arg[1];
    
    String path = filename + "."+extension;
    
    BufferedImage bimg      = ImageIO.read(new File(path));

    
    IPUtil.setMode(ColorMode.RED);
    int[][] rM = IPUtil.readImageAsMatrix(bimg);
    int[][] rE = he.equaliseHistogram(rM);
    
    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = IPUtil.readImageAsMatrix(bimg);
    int[][] gE = he.equaliseHistogram(gM);
    
    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = IPUtil.readImageAsMatrix(bimg);
    int[][] bE = he.equaliseHistogram(bM);
    
    Color[][] res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "equalized-"+filename, extension);
  }
  
  public static void main(String args[]) throws IOException{
    HistogramEqualization.execute("peppers_color.tif");
  }

  

}
