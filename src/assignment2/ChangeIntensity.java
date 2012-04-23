package assignment2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tao.image.ColorMode;
import tao.image.IPUtil;

public class ChangeIntensity {
  
  int G = IPUtil.G;
  
  public int[][] changeIntensityByFactor(int[][] imgMatrix, float f){
    int h = imgMatrix.length;
    int w = imgMatrix[0].length;

    int[][] tImgMatrix = new int[h][w];

    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        tImgMatrix[i][j] = (int) Math.min(255, imgMatrix[i][j] * f);
      }
    }    

    return tImgMatrix;
  }
  
  
  public int[][] changeIntensityByValue(int[][] imgMatrix, int value){
    int h = imgMatrix.length;
    int w = imgMatrix[0].length;

    int[][] tImgMatrix = new int[h][w];

    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        tImgMatrix[i][j] = Math.min(255, imgMatrix[i][j] + value);
      }
    }    

    return tImgMatrix;
  }


  /**
   * @param args
   * @throws IOException 
   */
  public static void execute(String name) throws IOException {
    
    ChangeIntensity ci = new ChangeIntensity();
    
    String[] arg = name.split("\\.");
    String filename = arg[0];
    String extension = arg[1];    

    String path = filename +"."+ extension;
    
    System.out.println(path);
    BufferedImage bimg      = ImageIO.read(new File(path));
    
    float factor = 1.25f;
    int value  = 20;

    IPUtil.setMode(ColorMode.RED);
    int[][] rM = IPUtil.readImageAsMatrix(bimg);
    int[][] rE = ci.changeIntensityByFactor(rM, factor);    

    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = IPUtil.readImageAsMatrix(bimg);
    int[][] gE = ci.changeIntensityByFactor(gM, factor);

    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = IPUtil.readImageAsMatrix(bimg);
    int[][] bE = ci.changeIntensityByFactor(bM, factor);

    Color[][] res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "byFactor-"+factor+"-"+filename, extension);

    
    IPUtil.setMode(ColorMode.RED);
    rM = IPUtil.readImageAsMatrix(bimg);
    rE = ci.changeIntensityByValue(rM, value);    

    IPUtil.setMode(ColorMode.GREEN);
    gM = IPUtil.readImageAsMatrix(bimg);
    gE = ci.changeIntensityByValue(gM, value);

    IPUtil.setMode(ColorMode.BLUE);
    bM = IPUtil.readImageAsMatrix(bimg);
    bE = ci.changeIntensityByValue(bM, value);

    res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "byValue-"+value+"-"+filename, extension);

  }
  
  public static void main(String args[]) throws IOException{
    ChangeIntensity.execute("peppers_color.tif");
  }

}
