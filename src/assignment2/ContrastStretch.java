package assignment2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tao.image.ColorMode;
import tao.image.IPUtil;

public class ContrastStretch {

  int G = IPUtil.G;

  public int[][] contrastStretch(int[][] imgMatrix){

    float h = imgMatrix.length;
    float w = imgMatrix[0].length;

    int tImgMatrix[][] = new int[(int)h][(int)w];
    int[] H  = IPUtil.getHistogram(imgMatrix);

    float d = IPUtil.maxPixelValue(H);
    float c = IPUtil.minPixelValue(H);
    
    int[] T = new int[G];
    float a = 0;
    float b = G-1;
    float k   = (b - a)/(d - c);
    
    for(int p = 0; p < G; p++){
      T[p] = (int) ((p - c )*k + a);
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
    
    name = "cameraman.tif";
    
    String[] arg = name.split("\\.");
    String filename = arg[0];
    String extension = arg[1];    

    String path = filename + "."+extension;
    ContrastStretch sc = new ContrastStretch();

    BufferedImage bimg      = ImageIO.read(new File(path));

    IPUtil.setMode(ColorMode.RED);
    int[][] rM = IPUtil.readImageAsMatrix(bimg);
    int[][] rE = sc.contrastStretch(rM);    

    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = IPUtil.readImageAsMatrix(bimg);
    int[][] gE = sc.contrastStretch(gM);

    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = IPUtil.readImageAsMatrix(bimg);
    int[][] bE = sc.contrastStretch(bM);

    Color[][] res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "stretched-"+filename, extension);

  }
  
  public static void main(String args[]) throws IOException{
    execute("");
  }

}
