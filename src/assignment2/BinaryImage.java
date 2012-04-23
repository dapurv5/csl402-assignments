package assignment2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import tao.image.ColorMode;
import tao.image.IPUtil;

public class BinaryImage {
  
  int threshold;
  
  public BinaryImage(){    
  }
  
  public BinaryImage(int threshold){
    this.threshold = threshold;
  }
  
  public void setThreshold(int threshold){
    this.threshold = threshold;
  }
  
  public int[][] convertToBinary(int[][] imgMatrix){
    int h = imgMatrix.length;
    int w = imgMatrix[0].length;

    int[][] tImgMatrix = new int[h][w];
    
    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        tImgMatrix[i][j] = (imgMatrix[i][j] > threshold)?255:0;
      }
    }
    
    return tImgMatrix;
  }

  /**
   * @param args
   * @throws IOException 
   */
  public static void execute(String name) throws IOException {
    
    String[] arg = name.split("\\.");
    String filename = arg[0];
    String extension = arg[1];       
    
    BinaryImage biut = new BinaryImage();
    
    String path = filename +"."+ extension;
    BufferedImage bimg      = ImageIO.read(new File(path));
    
    
    IPUtil.setMode(ColorMode.RED);
    int[][] rM = IPUtil.readImageAsMatrix(bimg);
    
    int threshold = IPUtil.weightedMean(rM);
    biut.setThreshold(threshold);
    
    int[][] rE = biut.convertToBinary(rM);
    Color[][] res = IPUtil.combineColors(rE, rE, rE);
    IPUtil.drawImage(res, "binary-"+filename, extension);

  }
  
  public static void main(String args[]) throws IOException{
    BinaryImage.execute("peppers_color.tif");
  }
}
